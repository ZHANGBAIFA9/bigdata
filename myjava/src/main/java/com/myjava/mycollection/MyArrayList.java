package com.myjava.mycollection;

import java.util.Random;

/**
 * @Version 1.0
 * @Author ZHANGBAIFA
 * @Date 2021/1/4 18:26
 * @Description:
 *  创建一个长度为6的int型数组，要求数组元素值在1~30之间，且是随机赋值。同时要求元素值各不相同。
 */
public class MyArrayList {
    public static void main(String[] args) {
        int[] ints = randNum();
        for(int i = 0 ; i< ints.length ; i++){
            System.out.print(ints[i]+"\t");
        }
        System.out.println();
        int[] ints1 = randNum2();
        for(int i = 0 ; i< ints1.length ; i++){
            System.out.print(ints1[i]+"\t");
        }

    }
    //第一种解决办法
    public static int[] randNum(){
        //声明一个数组
        int[] distinctArray = new int[6];
        //标识新产生的数字是否已存在在数组中
        boolean isFlag = false;
        Random random = new Random();
        //控制随机值范围
        int max = 30,min = 1;
        //主的循环体，用来控制的生成数据的个数
        for(int i = 0;i < distinctArray.length;i++) {
            //产生一个[1,30]之内的随机数
            int curNum = random.nextInt(max) % (max-min+1) + min;
            //用来循环判断新产生的数值是否在数组中存在，直至新产生的数值不在数组中存在结束循环
            while(true) {
                //遍历数组，查找是否有重复值
                for(int j = 0;j < distinctArray.length;j++) {
                    if(curNum == distinctArray[j]) {
                        isFlag = true; //存在重复值，isFlag置为true
                        break;
                    }
                }
                //isFlag如果为true，产生一个新的值。否则，不存在重复值，结束循环。
                if(isFlag) {
                    curNum = random.nextInt(max) % (max-min+1) + min;
                }else {
                    break;
                }
                //重置isFlag
                isFlag = false;
            }
            //将符合要求的值存到数组中去
            distinctArray[i] = curNum;
        }
        return distinctArray ;
    }
    //第二中解决办法
    public static int[] randNum2(){
        int[] distinctArray = new int[6];
        for(int i= 0;i < distinctArray.length;i++) {
            distinctArray[i] = (int) (Math.random() * 30) + 1;
            for(int j = 0;j < i;j++) {
                if(distinctArray[j] == distinctArray[i]) {
                    i--;
                    break;
                }
            }
        }

        return distinctArray ;
    }
}
