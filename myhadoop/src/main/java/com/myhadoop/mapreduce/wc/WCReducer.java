package com.myhadoop.mapreduce.wc;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * @Version 1.0
 * @Author ZHANGBAIFA
 * @Date 2021/2/18 19:08
 * @Description:
 */
public class WCReducer extends Reducer<Text,LongWritable,Text,LongWritable> {
    @Override
    protected void reduce(Text key, Iterable<LongWritable> values, Context context) throws IOException, InterruptedException {
        //定义long型变量：表示当前单词出现的总次数
        long sum = 0;
        for(LongWritable count:values){
            sum+=count.get();
        }
        //输出
        context.write(key,new LongWritable(sum));
    }
}
