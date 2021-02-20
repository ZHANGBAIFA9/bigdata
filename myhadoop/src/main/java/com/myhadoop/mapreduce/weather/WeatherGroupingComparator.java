package com.myhadoop.mapreduce.weather;

import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;

/**
 * @Version 1.0
 * @Author ZHANGBAIFA
 * @Date 2021/2/20 22:06
 * @Description:
 */
public class WeatherGroupingComparator extends WritableComparator {
    //空参构造，让父类创建key的对象，否则在运行时会出现空指针异常
    public WeatherGroupingComparator() {
        super(Weather.class,true);
    }

    @Override
    public int compare(WritableComparable a, WritableComparable b) {
        Weather wa = (Weather)a;
        Weather wb = (Weather)b;
        //同年同月分一组
        int result = wa.getYear().compareTo(wb.getYear());
        if(result==0){
            //年份相同，再比较月份
            result = wa.getMonth().compareTo(wb.getMonth());
        }
        return result;
        //假如key类（Weather）中的compareTo方法的逻辑适合分组比较器
        //return wa.compareTo(wb);
    }
}
