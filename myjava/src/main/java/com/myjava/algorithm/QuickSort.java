package com.myjava.algorithm;

/**
 * Create By BF On 2020/6/16
 * 快速排序：
 *      选择一个基准元素，通常选择第一个元素或者最后一个元素，通过一趟扫描，将待排序列分成两部分，一部分比基准元素小，
 *      一部分大于等于基准元素，此时基准元素在其排好序的正确位置，然后在同样的方法递归的排序划分的两部分。
 *      （一般情况左边部分小，右边部分大）
 *      不稳定排序
 *  时间复杂度：平均 O(nlog2n)
 *  空间复杂度：O(nlog2n)
 */
public class QuickSort {
    public static void main(String[] args){
        int[] arr = new int[]{ 3 , 2 , 6 , 8 , 9 ,10 , 1 } ;
        quick(arr , 0 , arr.length - 1);
        for (int a : arr){
            System.out.print(a+"\t");
        }
    }
    /**
     * 方法调用：
     * 核心：两次递归调用
     * 原理：逐步排序，将比当前小的元素放在左边，比当前元素大的放在右边
     */
    private static void quick(int[] arr, int left, int right) {
        if(arr == null || arr.length == 0){
            return;
        }
        if(left > right){
            return;
        }
        int key = arr[left] ;
        int l = left ;
        int r = right ;
        while(l != r){
            while (arr[r] >= key && l < r){
                r-- ;
            }
            while (arr[l] <= key && l < r){
                l++;
            }
            if(l < r){
                int tmp = arr[l] ;
                arr[l] = arr[r] ;
                arr[r] = tmp ;
            }
        }
        arr[left] = arr[l] ;
        arr[l] = key ;
        quick(arr , left , l - 1 );
        quick(arr , l + 1 , right);
    }
}
