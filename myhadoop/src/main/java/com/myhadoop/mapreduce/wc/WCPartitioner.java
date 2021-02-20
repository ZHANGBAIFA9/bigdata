package com.myhadoop.mapreduce.wc;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Partitioner;

/**
 * @Version 1.0
 * @Author ZHANGBAIFA
 * @Date 2021/2/20 15:17
 * @Description: 自定义分区 ,数据进行分区后 进入圆形缓冲区 MapOutPutBuffer
 *              圆形缓冲区达到默认值80%时溢出到磁盘
 * 默认执行hashPartitioner
 * return (key.hashCode() & Integer.MAX_VALUE) % numReduceTasks;
 */
public class WCPartitioner extends Partitioner<Text,LongWritable> {
    @Override
    public int getPartition(Text text, LongWritable longWritable, int numPartitions) {
        return 0;
    }
}
