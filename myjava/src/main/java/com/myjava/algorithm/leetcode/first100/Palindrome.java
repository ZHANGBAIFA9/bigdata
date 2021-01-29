package com.myjava.algorithm.leetcode.first100;

/**
 * @Version 1.0
 * @Author ZHANGBAIFA
 * @Date 2021/1/28 15:26
 * @Description:
 *                 判断一个整数是否是回文数。回文数是指正序（从左向右）和倒序（从右向左）读都是一样的整数。
 *                 示例 1:
 *                          输入: 121
 *                          输出: true
 *                 示例 2:
 *                         输入: -121
 *                         输出: false
 *                 解释: 从左向右读, 为 -121 。 从右向左读, 为 121- 。因此它不是一个回文数。
 *                 示例 3:
 *                         输入: 10
 *                         输出: false
 *                解释: 从右向左读, 为 01 。因此它不是一个回文数。
 *                进阶:
 *                      你能不将整数转为字符串来解决这个问题吗？
 */
public class Palindrome {
    public static void main(String[] args) {
        int x = 211131112 ;
        boolean b = isPalindrome(x);
//        System.out.println("" + b);
        System.out.println(isPalindrome1(x));
    }

    /**
     *  想法：
     *          1、传入参数转字符串，转字符数组，定义返回数据
     *          2、通过判断排除负数，循环判断对应值是否相等，不相等则返回false,跳出循环
     * @param x
     * @return
     */
    public static boolean isPalindrome(int x) {
        boolean ans = true ;
       String src = String.valueOf(x);
        char[] src_c = src.toCharArray();
        if(!String.valueOf(src_c[0]).equals("-")){
            for(int i = 0 ; i< src_c.length ; i++){
                if(src_c[i] == src_c[src_c.length -1 - i]){
                    ans = true ;
                }else{
                    ans = false ;
                    break;
                }
            }
            return ans ;
        }else{
            return false ;
        }
    }

    /**
     *  简单粗暴解决办法
     * @param x
     * @return
     */
    public static boolean isPalindrome1(int x) {
        String reversedStr = (new StringBuilder(x + "")).reverse().toString();
        return (x + "").equals(reversedStr);
    }

    /**
     *
     * @param x
     * @return
     */
    public static boolean isPalindrome2(int x) {
        //边界判断
        if (x < 0) {
            return false;
        }
        int div = 1;
        while (x / div >= 10){
            div *= 10;
        }
        while (x > 0) {
            int left = x / div;
            int right = x % 10;
            if (left != right){
                return false;
            }
            x = (x % div) / 10;
            div /= 100;
        }
        return true;
    }
}
