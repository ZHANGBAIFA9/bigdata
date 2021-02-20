package com.myhadoop.mapreduce.weather;

import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;

/**
 * @Version 1.0
 * @Author ZHANGBAIFA
 * @Date 2021/2/20 22:07
 * @Description:
 */
public class WeatherSortComparator extends WritableComparator {
    //空参构造让父类创建一个指定类型的对象
    public WeatherSortComparator() {
        super(Weather.class,true);
    }

    @Override
    public int compare(WritableComparable a, WritableComparable b) {
        Weather wa = (Weather)a;
        Weather wb = (Weather)b;
//        //如果Weather类的copareTo()方法不适用于排序比较器中，需要自定义开发。
//        //先比较年
//        int result = wa.getYear().compareTo(wb.getYear());
//        if(result == 0){//年相同
//            result = wa.getMonth().compareTo(wb.getMonth());
//            if(result==0){//月相同，再比较温度
//                result = wb.getTemperature().compareTo(wa.getTemperature());
//            }
//        }
//        return result;
        //Weather类已经实现了copareTo()方法，并且排序的规则适用于排序比较器中
        return wa.compareTo(wb);
    }
}
