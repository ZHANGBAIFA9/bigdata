package com.myhadoop.mapreduce.weather;

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.util.Iterator;

/**
 * @Version 1.0
 * @Author ZHANGBAIFA
 * @Date 2021/2/20 22:07
 * @Description:
 */
public class WeatherReducer extends Reducer<Weather,Text,Text,NullWritable> {
    @Override
    protected void reduce(Weather key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
        /**   key:                     value:
         *      1949    10  1  38      1949-10-01 19:21:02	38c
         *      1949    10  1  37      1949-10-01 14:21:02  37c
         *      1949    10  2  36     1949-10-02 14:01:02	36c
         */
        int day = -1;
        Iterator<Text> iter = values.iterator();
        while(iter.hasNext()){
            Text val = iter.next();
            if(day == -1){
                day = key.getDay();
                context.write(val,NullWritable.get());
            }else{
                if(day!= key.getDay()){
                    context.write(val,NullWritable.get());
                    break;
                }
            }
        }
    }
}
