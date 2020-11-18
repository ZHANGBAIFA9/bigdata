package com.myjava.algorithm;

/**
 * Create By BF On 2020/6/16
 * 冒泡排序及优化
 * 思想：
 *      首先将第1个和第2个记录的关键字比较大小，如果是逆序的，就将这两个记录进行交换，在对第二个和第三个记录的关键字
 *     进行比较，依次类推，重复上述计算，直至完成第(n-1) 和 第n个记录的关键字之间的比较，此后，在按照上述过程进行第2次、第3次
 *     排序，直至整个序列有序为止。注意：排序过程中，当相邻的两个元素大小一致，这一步就不需要交换位置，
 *     因此，冒泡是一种严格的排序算法，不改变序列中相同元素之间的相对位置关系。
 * 时间复杂度：O(N^2)
 * 空间复杂度：O(1)
 */
public class BubbleSort {
    public static void main(String[] args){
        //排序前数组
        int[] arr = {2,3,8,65,4,6,4163,41,3,4,3,1,45,3} ;
        bubbleSort(arr) ;
        //打印排序后数组
        for(int i = 0 ; i< arr.length ; i++){
            System.out.print(arr[i]+"\t");
        }
    }
    /**
     *通过标记位，进行调优
     */
    public static void bubbleSort(int[] arr){
        int length = arr.length ;
        //使用标记，对冒泡排序进行优化
        boolean flag ;
        for (int i = 0 ; i < length - 1 ;i++){
            flag = false ;
            for (int j = 0 ; j< length - i - 1 ; j++){
                //进行升序操作
                if(arr[j] > arr[j + 1]){
                    int temp = arr[j] ;
                    arr[j] = arr[j+1] ;
                    arr[j+1] = temp ;
                    flag = true ;
                }
                System.out.println(arr[j]+"\r\n");
            }
            //对冒泡排序进行优化
            if(!flag){
                break;
            }
        }
    }
}
