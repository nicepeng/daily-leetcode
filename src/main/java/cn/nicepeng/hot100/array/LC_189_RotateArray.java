package cn.nicepeng.hot100.array;

import java.util.Arrays;

/**
 * LC_189_RotateArray       189. 轮转数组
 *  给定一个整数数组 nums，将数组中的元素向右轮转 k 个位置，其中 k 是非负数。
 *  输入: nums = [1,2,3,4,5,6,7], k = 3
 *  输出: [5,6,7,1,2,3,4]
 * @author nicepeng
 * @since 2026/6/27 1:02
 */
public class LC_189_RotateArray {

    public static void rotate(int[] nums, int k) {
        k %= nums.length;

        swap(nums, 0, nums.length - 1);
        swap(nums, 0, k - 1);
        swap(nums, k, nums.length - 1);
    }

    public static void swap(int[] nums, int begin, int end) {
        while (begin < end) {
            int temp = nums[begin];
            nums[begin] = nums[end];
            nums[end] = temp;
            begin++;
            end--;
        }
    }

    public static void main(String[] args) {
        int[] nums = {1,2,3,4,5,6,7};
        rotate(nums, 3);
        System.out.println(Arrays.toString(nums));
    }
}
