package com.myjava.algorithm.leetcode.first100;

import java.util.HashMap;
import java.util.Map;

/**
 * @Version 1.0
 * @Author ZHANGBAIFA
 * @Date 2021/1/11 19:23
 * @Description:
 *      给定一个整数数组 nums 和一个目标值 target，
 *      请你在该数组中找出和为目标值的那两个整数，并返回他们的数组下标。
 *
 *      你可以假设每种输入只会对应一个答案。但是，数组中同一个元素不能使用两遍。
 *      示例:
 *          给定 nums = [2, 7, 11, 15], target = 9
 *          因为 nums[0] + nums[1] = 2 + 7 = 9
 *          所以返回 [0, 1]
 *
 */
public class TwoNumSum {
    public static void main(String[] args) {
       int[] nums = {2, 7, 11, 15};
       int target = 9 ;

        int[] answers = answer2(nums, target);
        for(int answer : answers){
            System.out.println(answer);
       }
        
    }

//    public static int[] answer(int[] nums , int target){
//        int[] res = new int[2];
//        Map<Integer,Integer> map = new HashMap<Integer,Integer>();
//        int length = nums.length;
//        for(int i = 0 ; i< length ; i++){
//            if(map.containsKey(target - nums[i]) == true){
//                res[0] = map.get(target - nums[i]);
//                res[1] = i;
//                break;
//            }else{
//                map.put(nums[i],i);
//            }
//        }
//        return res ;
//    }

    /**
     *  思路：遍历给定数组，判断map中已存放key是否存在 (target - nums[i]) 的值，如存在即跳出循环，返回位置下标，
     *      否则将数组元素存放在map数组中，以便后续判断使用
     * @param nums
     * @param target
     * @return
     */
    public static int[] answer2(int[] nums , int target) {
        int[] dest = new int[2] ;
        Map<Integer, Integer> map = new HashMap<Integer, Integer>();
        int length = nums.length ;
        for(int i = 0 ; i < length ; i++){
            if(map.containsKey(target - nums[i]) == true){
                dest[0] = map.get(target - nums[i]) ;
                dest[1] = i ;
                break ;
            }else{
                map.put(nums[i] , i);
            }
        }
        return dest ;
    }


}
