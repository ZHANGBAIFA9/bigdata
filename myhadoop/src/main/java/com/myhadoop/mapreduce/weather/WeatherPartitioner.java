package com.myhadoop.mapreduce.weather;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Partitioner;

/**
 * @Version 1.0
 * @Author ZHANGBAIFA
 * @Date 2021/2/20 22:07
 * @Description:
 */
public class WeatherPartitioner extends Partitioner<Weather,Text> {
    @Override
    public int getPartition(Weather weather, Text text, int numPartitions) {
        //按照月份分区（原则：不能破坏原语，考虑防止出现数据倾斜）
        return weather.getMonth()%numPartitions;
    }
}
