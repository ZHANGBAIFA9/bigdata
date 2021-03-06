package com.myhadoop.mapreduce.wc;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

/**
 * @Version 1.0
 * @Author ZHANGBAIFA
 * @Date 2021/2/18 19:08
 * @Description:
 *  注意：本地运行测试，运行时给定参数 /wc/input /wc/output2，同时指定本地模式
 *
 *  打包上传到集群运行，project structure --> artifacts --> + jar --> empty --> 给jar起名wc
 *  --> 将编译后的类拉到左边，应用保存 --> 点击Build --> Build Artifacts --> wc 进行打包
 *
 * 注： 提交集群运行要添加 job.setJarByClass(MainClass.class); 指定当前class为入口
 *
 *  集群运行测试：yarn jar wc.jar com.myhadoop.mapreduce.wc.MainClass /wc/input /wc/output3
 */
//注： 如不需要当前目录下的配置文件，可将resources设置为 test resources root 然后重新Build即可
// 或将resource 下配置文件先移除在打包，本地调试时重新添加
public class MainClass {
    //初始化本地配置
    private static Configuration conf = new Configuration(true);
    public static void main(String[] args) throws Exception {
        if(args == null || args.length != 2){
            System.out.println("Usage : yarn jar wc.jar com.myhadoop.mapreduce.wc.MainClass <input path> <output path>");
            // 结束运行
            System.exit(1);
        }
        //设置本地模式运行
        conf.set("mapreduce.framework.name","local");
        // 调节切片大小设置 Math.max(minSize, Math.min(maxSize, blockSize));
        //调节切片大小比块（128M）大,调节minSize大小大于128M即可
        //conf.set(FileInputFormat.SPLIT_MINSIZE,"");
        // 调节切片大小比块（128M）小，调节maxSize小于128M 即可
        conf.set(FileInputFormat.SPLIT_MAXSIZE,"") ;

        Job job = Job.getInstance(conf);
        // 设置参数
        //设置map类相关
        job.setMapperClass(WCMapper.class);
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(LongWritable.class);
        //设置reduce类相关
        job.setReducerClass(WCReducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(LongWritable.class);
        //设置reducetask数量
        job.setNumReduceTasks(1);
        job.setPartitionerClass(WCPartitioner.class);
        //设置作业名称
        job.setJobName("wc");
        job.setJarByClass(MainClass.class);
        //设置作业输入输出路径
        FileInputFormat.addInputPath(job,new Path(args[0]));
        //确保输出路径不存在，以免报错
        Path outputPath = new Path(args[1]);
        if(outputPath.getFileSystem(conf).isDirectory(outputPath)){
            //如果存在则删除
            outputPath.getFileSystem(conf).delete(outputPath,true) ;
        }
        FileOutputFormat.setOutputPath(job,outputPath);
        //提交作业,true 打印每秒计算信息
        job.waitForCompletion(true) ;
    }
}
