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
}
