package com.myhive.customFunction.udf;

import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.io.Text;
import org.json.JSONException;

/**
 * @Version 1.0
 * @Author ZHANGBAIFA
 * @Date 2021/2/22 16:39
 * @Description:
 *         自定义建议版手机号脱敏 UDF函数
 *         函数打包与hdfs打包一致
 */
public class TuoMin extends UDF {

    public Text evaluate(final Text key){
        if(key == null ){
            return null ;
        }
        String phone = key.toString() ;
        String str = phone.substring(0,3) +"****"+ phone.substring(7) ;
        return new Text(str);
    }

    /**
     * 自测函数，上述方法需static修饰
     */
//    public static void main(String[] args) {
//        String str = "13508629990" ;
//        System.out.println(evaluate(new Text(str)));
//    }
}
