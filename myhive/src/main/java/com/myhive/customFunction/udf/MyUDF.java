package com.myhive.customFunction.udf;


import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.hive.ql.exec.Description;

import java.util.ArrayList;
import java.util.List;

/**
 * @Version 1.0
 * @Author ZHANGBAIFA
 * @Date 2019/12/3 16:00
 * @Description:
 */
@Description(name = "test",
        value = "this is a test function",
        extended = "select test() ===> test\nselect test('tom') ===> test: tom"
)
public class MyUDF extends UDF {
    //必须重写evaluate函数
    public String evaluate (){
        return "test";
    }


    public List<String> evaluate (String name){
        ArrayList<String> list = new ArrayList<String>();
        String[] arr = name.split(" ");
        for (String s : arr) {
            list.add(s);
        }
        return list;
    }
}
