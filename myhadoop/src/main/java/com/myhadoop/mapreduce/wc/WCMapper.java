package com.myhadoop.mapreduce.wc;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * @Version 1.0
 * @Author ZHANGBAIFA
 * @Date 2021/2/18 19:07
 * @Description:
 *      样例：单词统计
 *      job的映射类--Mapper类
 *          LongWritable,Text,Text,LongWritable
 *          第一个参数类型：输入的key的类型，LongWritable文件的偏移量
 *          第二个参数类型：输入的value的类型，对应行内容（String）的类型Text
 *          第三个参数：输出的key的类型
 *          第四个参数：输出的value的类型
 */
public class WCMapper extends Mapper<LongWritable,Text,Text,LongWritable> {
    private Text keyout = new Text();
    private LongWritable valueout = new LongWritable(1);
    //被处理文件每一行都会调用一次map方法
    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        //方便查看源码调试，进入map后线程休眠
//        Thread.sleep(Long.MAX_VALUE);
        //获取当前行文本的内容
        String line = value.toString();
        //按空格进行切分
        String[] words = line.trim().split(" ");
        for(String word:words){
            keyout.set(word);
            context.write(keyout,valueout);
        }
    }
}
