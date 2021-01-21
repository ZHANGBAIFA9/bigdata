package com.myhive.customFunction.udf;

import org.apache.hadoop.hive.ql.exec.UDF;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * @Version 1.0
 * @Author ZHANGBAIFA
 * @Date 2021/1/21 15:07
 * @Description:
 */
public class MyJSONUDF extends UDF {
    //必须重写evaluate函数
    public  String evaluate (){
        return "test";
    }


    public static List<String> evaluate (String json) throws JSONException {
        JSONObject basejson = new JSONObject(json);
        List<String> list = new ArrayList<>();
        String evt_id_list = basejson.getString("evt_id_list");
        String speed_per = basejson.getString("speed_per");
        String overspeed_dur = basejson.getString("overspeed_dur");
        String waybill_id = basejson.getString("waybill_id");
        String vhc_id = basejson.getString("vhc_id");
        list.add(evt_id_list);
        list.add(speed_per);
        list.add(overspeed_dur);
        list.add(waybill_id);
        list.add(vhc_id);
        return list ;
    }
    public static void main(String[] args) throws JSONException {
        String line = "{\"evt_id_list\":\"hello world\",\"speed_per\":\"12121212\",\"overspeed_dur\":\"sssss\",\"waybill_id\":\"biubiubiu\",\"vhc_id\":\"2222\"}" ;
        List<String> evaluates = evaluate(line);
        for(String evaluate : evaluates){
            System.out.println(evaluate);
        }
    }
}
