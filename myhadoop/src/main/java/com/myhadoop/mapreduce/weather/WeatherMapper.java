package com.myhadoop.mapreduce.weather;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * @Version 1.0
 * @Author ZHANGBAIFA
 * @Date 2021/2/20 22:07
 * @Description:
 */
public class WeatherMapper extends Mapper<LongWritable,Text,Weather,Text> {
    //创建对象，用于封装数据
    private Weather weather = new Weather();

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        //1949-10-01 14:21:02	34c
        //数据预处理
        String line = value.toString().trim();
        String datas[] = line.split("\t");

        //34c
        weather.setTemperature(Double.parseDouble(datas[1].substring(0,datas[1].length()-1)));
        //定义一个日志格式化的对象
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(sdf.parse(datas[0]));
            weather.setYear(calendar.get(Calendar.YEAR));
            weather.setMonth(calendar.get(Calendar.MONTH)+1);
            weather.setDay(calendar.get(Calendar.DAY_OF_MONTH));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //输出
        context.write(weather,value);
    }
}
