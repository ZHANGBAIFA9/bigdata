package com.myhadoop.mapreduce.pagerank;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * @Version 1.0
 * @Author ZHANGBAIFA
 * @Date 2021/2/21 14:15
 * @Description:
 */
public class PRMapper extends Mapper<Text, Text, Text, Text> {
    @Override
    protected void map(Text key, Text value, Context context) throws IOException, InterruptedException {
        // 指示第几次运行
        int index = context.getConfiguration().getInt("runCount", 0);
        System.err.println("index = " + index);
        //KeyValueTextInputFormat使用\t对行进行分割，第一个\t前面是key，后面是value
        //第一次运行：key:A	 value:B\tD
        //非次运行：key:A	 value:0.4625\tB\tD
        String line = value.toString();
        String[] pages = line.split("\t");
        //被key投票的页面数量，后续被用于均分key的PR值
        int pageNum = 0;
        //用于确定pages[]数组遍历从0（第一次运行）开始还是从1（非第一次运行）开始
        int i = 0;
        //当前Key的PR值，第一次默认为1.0
        double prValue = 1.0;
        //
        String newValue = line;//B\tD   0.4625\tB\tD
        if (index == 0) {
            // {"B","D"}
            pageNum = pages.length;
            // 为了防止第一次计算的时候，没有pr值，添加默认pr值
            newValue = "1\t" + newValue;
        } else {
            //  pages: {"0.4625","B","D"}
            prValue = Double.parseDouble(pages[0]);
            pageNum = pages.length - 1;
            i = 1;//后续遍历时排除掉0.465
        }
        //  B   D
        for (;i < pages.length; i++) {  //0.4625\tB\tD
            // <B, 1/2>
            // <D, 1/2>
            context.write(new Text(pages[i]), new Text(prValue / pageNum + ""));
        }
        // <A, pr   B   D>
        context.write(key, new Text(newValue));
        System.err.println("Mapper输出键值对：" + key.toString() + "::" + newValue);
    }
}
