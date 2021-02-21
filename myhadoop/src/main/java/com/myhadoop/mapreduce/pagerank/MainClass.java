package com.myhadoop.mapreduce.pagerank;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.KeyValueTextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;


/**
 * @Version 1.0
 * @Author ZHANGBAIFA
 * @Date 2021/2/21 14:15
 * @Description:
 */
public class MainClass {
    enum MyCounter {
        MY_COUNTER
    }
    public static void main(String[] args) throws Exception {
        Configuration configuration = new Configuration(true);
        configuration.set("mapreduce.framework.name", "local");
        //用于存储中间数据，mapreduce运行完要删掉
        configuration.set("mapreduce.cluster.local.dir", "E:\\usr\\local\\mapreduce.cluster.local.dir");
        int index = 0;
        double myvalue = 0.0001;
        while (true) {
            configuration.setInt("runCount", index);
            // configuration的设置一定设置完之后再实例化Job对象
            Job job = Job.getInstance(configuration);
            // job中获取不到这里设置的参数
            // configuration.setInt("runCount", index);

            //将当前是第几次计算放到计数器中
            System.err.println(configuration.getInt("runCount", 0));
            job.setJobName("pageRank"+index);
            job.setJarByClass(MainClass.class);// A     B    C
            // 该inputformat使用\t对行进行分割，\t前面是key，后面是value
            // 如果没有\t，则整行是key，value是空的
            // 如果有多个\t，则第一个\t之前的是key，后面的是value
            job.setInputFormatClass(KeyValueTextInputFormat.class);
            if (index != 0) {
                // 如果不是第一次计算，则以上次计算结果作为本次输入
                FileInputFormat.addInputPath(job, new Path("/data/pr/output" + (index - 1)));
            } else {
                // 如果是第一次计算，则直接读取原始文件
                FileInputFormat.addInputPath(job, new Path("/data/pagerank/input/pagerank.txt"));
            }
            // 输出设置
            FileOutputFormat.setOutputPath(job, new Path("/data/pr/output" + index));

            job.setMapperClass(PRMapper.class);
            job.setReducerClass(PRReducer.class);

            job.setMapOutputKeyClass(Text.class);
            job.setMapOutputValueClass(Text.class);

            job.waitForCompletion(true);

            long sum = job.getCounters().findCounter(MyCounter.MY_COUNTER).getValue();
            System.err.println("sum = " + sum);
            if (sum * 1.0 / (10000 * 4) < myvalue) {
                break;
            }
            index++;
        }
    }
}
