package com.myhadoop.mapreduce.weather;

import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * @Version 1.0
 * @Author ZHANGBAIFA
 * @Date 2021/2/20 22:06
 * @Description:
 */
public class Weather implements WritableComparable<Weather> {
    private Integer year;
    private Integer month;
    private Integer day;
    //温度
    private Double temperature;

    @Override
    public int compareTo(Weather o) {
        //比较器比较方法的默认实现，后续可能在排序比较或分组比较器中被调用
        int result = this.getYear().compareTo(o.getYear());
        //表示年相同
        if(result==0){
            result = this.month.compareTo(o.getMonth());
            //月相同，按照温度的降序排列
            if(result==0){
                result = o.getTemperature().compareTo(this.temperature);
            }
        }
        return result;
    }
    //一般开发中使用自定义排序比较器，然后通过job.setOutputKeyComparatorClass(..)显示指定排序比较器
    //如果没有通过job.setOutputKeyComparatorClass(..)显示指定排序比较器，则使用key自带的比较
    public static class Comparator extends WritableComparator {
        public Comparator(){
            super(Weather.class);
        }
        @Override
        public int compare(WritableComparable a, WritableComparable b) {
            Weather wa = (Weather) a;
            Weather wb = (Weather) b;
            return wa.compareTo(wb);
        }
    }
    static {
        // register this comparator
        WritableComparator.define(Weather.class, new Weather.Comparator());
    }

    @Override
    public void write(DataOutput out) throws IOException {
        out.writeInt(year);
        out.writeInt(month);
        out.writeInt(day);
        out.writeDouble(temperature);
    }

    @Override
    public void readFields(DataInput in) throws IOException {
        this.year = in.readInt();
        this.month = in.readInt();
        this.day = in.readInt();
        this.temperature = in.readDouble();
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public Double getTemperature() {
        return temperature;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    @Override
    public String toString() {
        return "Weather{" +
                "year=" + year +
                ", month=" + month +
                ", day=" + day +
                ", temperature=" + temperature +
                '}';
    }
}
