package com.myjava.algorithm.leetcode.first100;

/**
 * @Version 1.0
 * @Author ZHANGBAIFA
 * @Date 2021/1/26 9:46
 * @Description:
 *                  给你一个 32 位的有符号整数 x ，返回 x 中每位上的数字反转后的结果。
 *                  如果反转后整数超过 32 位的有符号整数的范围 [−231, 231 − 1] ，就返回 0。
 *                  假设环境不允许存储 64 位整数（有符号或无符号）。
 *                  示例 1：
 *                      输入：x = 123
 *                      输出：321
 *                 示例 2：
 *                      输入：x = -123
 *                      输出：-321
 *                 示例 3：
 *                      输入：x = 120
 *                      输出：21
 *                 示例 4：
 *                      输入：x = 0
 *                      输出：0
 *                 提示：-231 <= x <= 231 - 1
 *
 */
public class IntegerInversion {

    public static void main(String[] args) {
        int x = 0 ;
        System.out.println(reverse(x));
    }

    /**
     *  想法： 1、接收参数转成string字符串处理，字符串转成字符数组
     *         2、对参数首位字符进行判断，是否带有负号，若有则从第二位字符进行处理，若无直接处理
     *         3、对字符数组进行处理乘10的指数次幂，处理后返回
     * @param x
     * @return
     */
    public static int reverse(int x) {
        String src = String.valueOf(x);
        char[] dest_c = src.toCharArray();
        int dest_i = 0 ;
        if(!String.valueOf(dest_c[0]).equals("-")){
            for(int i = 0 ; i < dest_c.length ; i ++){
                dest_i = (int) (dest_i + Integer.parseInt(String.valueOf(dest_c[i])) * Math.pow(10,i));
            }
        }else{
            for(int i = 1 ; i < dest_c.length ; i ++){
                dest_i = (int) (dest_i + Integer.parseInt(String.valueOf(dest_c[i])) * Math.pow(10,i-1));
            }
            dest_i = -dest_i ;
        }
        return dest_i ;
    }

    /**
     *
     * @param x
     * @return
     */
    public static int reverse1(int x) {
        int ans = 0;
        while (x != 0) {
            int pop = x % 10;
            if (ans > Integer.MAX_VALUE / 10 || (ans == Integer.MAX_VALUE / 10 && pop > 7))
                return 0;
            if (ans < Integer.MIN_VALUE / 10 || (ans == Integer.MIN_VALUE / 10 && pop < -8))
                return 0;
            ans = ans * 10 + pop;
            x /= 10;
        }
        return ans;
    }
}
