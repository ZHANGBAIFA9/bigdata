package com.myhadoop.mapreduce.pagerank;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * @Version 1.0
 * @Author ZHANGBAIFA
 * @Date 2021/2/21 14:15
 * @Description:
 */
public class PRReducer extends Reducer<Text, Text, Text, Text>{
    @Override
    protected void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
        //计算key被投票PR值的和
        //C	1
        //C	1/2
        //C	1\tA\tB
        //定义变量，用于记录旧的投票关系，比如：1\tA\tB
        String oldValue = null;
        //计算PR值得和 1+1/2
        double prSum = 0;
        //遍历迭代器
        for (Text value : values) {
            String str = value.toString();
            String[] strs = str.split("\t");
            if (strs.length == 1) {
                // 1
                // 1/2
                prSum += Double.parseDouble(strs[0]);
            } else if (strs.length > 1) {
                // 1\tA\tB
                oldValue = str;
            }
        }
        // 获取旧的PR值:1\tA\tB->1
        double oldPr = Double.parseDouble(oldValue.substring(0, oldValue.indexOf("\t")));
        //newPr=0.15/4 + 0.85 *3/2= 1.3125
        double newPr = 0.15/4 + 0.85 * prSum;

        // 更新pr值之后
        // 1.3125\tA\tB
        String newValue = newPr + oldValue.substring(oldValue.indexOf("\t"));
        // <C,1.3125\tA\tB>
        context.write(key, new Text(newValue));
        // 输出每次计算的pr值差值
        System.err.println(Math.abs(newPr - oldPr) * 10000);

        // 本次PR值-上次PR值  取绝对值，在所有reduce之间累加这个值
        context.getCounter(MainClass.MyCounter.MY_COUNTER).increment((long) (Math.abs(newPr - oldPr) * 10000));
    }
}
