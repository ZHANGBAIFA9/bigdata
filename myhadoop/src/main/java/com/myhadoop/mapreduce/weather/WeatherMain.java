package com.myhadoop.mapreduce.weather;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;
/**
 * @Version 1.0
 * @Author ZHANGBAIFA
 * @Date 2021/2/20 22:06
 * @Description:
 *  job执行类
 */
public class WeatherMain {
    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration(true);
        conf.set("mapreduce.framework.name","local");
        Job job = Job.getInstance(conf);
        job.setJobName("温度统计");
        job.setJarByClass(WeatherMain.class);

        job.setMapperClass(WeatherMapper.class);
        job.setMapOutputKeyClass(Weather.class);
        job.setMapOutputValueClass(Text.class);

        job.setReducerClass(WeatherReducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(NullWritable.class);

        FileInputFormat.addInputPath(job,new Path("/data/weather/input"));
        Path outputPath = new Path("/data/weather/output");
        if(outputPath.getFileSystem(conf).exists(outputPath)){
            outputPath.getFileSystem(conf).delete(outputPath,true);
        }
        FileOutputFormat.setOutputPath(job,outputPath);
        //设置排序比较器
        job.setSortComparatorClass(WeatherSortComparator.class);
        //设置reduceTask的数量
        job.setNumReduceTasks(4);
        //设置分区
        job.setPartitionerClass(WeatherPartitioner.class);

        //设置CombinerClass,在Map端先进行合并。通常使用reducer类
        //job.setCombinerClass(WeatherReducer.class);
        //设置分组比较器
        job.setGroupingComparatorClass(WeatherGroupingComparator.class);
        job.waitForCompletion(true);
    }
}
