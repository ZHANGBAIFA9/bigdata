package com.myjava.algorithm.leetcode.first100;

/**
 * @Version 1.0
 * @Author ZHANGBAIFA
 * @Date 2021/1/13 13:21
 * @Description:
 *      给你两个 非空 的链表，表示两个非负的整数。它们每位数
 *      字都是按照 逆序 的方式存储的，并且每个节点只能存储 一位 数字。
 *
 *      请你将两个数相加，并以相同形式返回一个表示和的链表。
 *      你可以假设除了数字 0 之外，这两个数都不会以 0 开头。
 *          示例1：
 *          输入：l1 = [2,4,3], l2 = [5,6,4]
 *          输出：[7,0,8]
 *          解释：342 + 465 = 807.
 *
 *          示例2：
 *          输入：l1 = [0], l2 = [0]
 *          输出：[0]
 *
 *          示例3：
 *          输入：l1 = [9,9,9,9,9,9,9], l2 = [9,9,9,9]
 *          输出：[8,9,9,9,0,0,0,1]
 *
 */
public class TwoNumSumLian {
    public static void main(String[] args) {
        int[] l1 = {2,4,3} ;
        int[] l2 = {5,6,4} ;
        // 我得处理办法
        int[] answers = answer(l1, l2);
        for(int answer : answers){
            System.out.print(answer + "\t");
        }

        System.out.println("=======================");
        //给的标准答案
        ListNode l3 = new ListNode(234) ;
        ListNode l4 = new ListNode(564) ;
        ListNode answers2 = answers2(l3 , l4) ;
        System.out.println(answers2.val);
    }

    /**
     *      标准答案：1、???
     * @param l1
     * @param l2
     * @return
     */
    private static ListNode answers2(ListNode l1, ListNode l2) {
        ListNode pre = new ListNode(0);
        ListNode cur = pre;
        int carry = 0;
        while(l1 != null || l2 != null) {
            int x = l1 == null ? 0 : l1.val;
            int y = l2 == null ? 0 : l2.val;
            int sum = x + y + carry;

            carry = sum / 10;
            sum = sum % 10;
            cur.next = new ListNode(sum);

            cur = cur.next;
            if(l1 != null)
                l1 = l1.next;
            if(l2 != null)
                l2 = l2.next;
        }
        if(carry == 1) {
            cur.next = new ListNode(carry);
        }
        return pre.next;
    }

    /**
     *  首先是我的做法： 简单粗暴，不考虑进位退位问题
     *                  1、根据示例1可知数组需要被转换顺序计算，求和后需转换顺序在返回
     *                  2、第一步，将传进来的参数进行转换，倒序赋值给sb，然后转成Integer,进行计算
     *                  3、第二步，将计算结果转成字符数组，然后进行调转顺序，赋值给需返回的int类型数组
     * @param src1
     * @param src2
     * @return
     */
    public static int[] answer(int[] src1 , int[] src2){
        int s1 = 0 ;
        int s2 = 0 ;
        StringBuilder sb1 = new StringBuilder();
        for(int i = src1.length - 1; i >= 0 ; i--){
            sb1.append(src1[i]) ;
        }
        StringBuilder sb2 = new StringBuilder();
        for(int i = src2.length - 1 ; i >= 0 ; i--) {
            sb2.append(src2[i]) ;
        }
        s1 = Integer.parseInt(sb1.toString());
        s2 = Integer.parseInt(sb2.toString()) ;
        //
        String dest = String.valueOf(s1 + s2);
        char[] chars = dest.toCharArray();
        int[] res  = new int[chars.length];
        int count = 0 ;
        for ( int i = chars.length -1 ; i >= 0  ;i--){
            res[count] = Integer.parseInt(String.valueOf(chars[i])) ;
            count++ ;
        }
        return res ;
    }
    
}
// 标准答案，自定义一个类，类属性包含 值： val  ， 下个节点值 ListNode 带参构造函数
class ListNode {
    int val;
    ListNode next;
    ListNode(int x) { val = x; }
 }