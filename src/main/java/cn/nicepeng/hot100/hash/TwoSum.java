package cn.nicepeng.hot100.hash;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 1. 两数之和
 * 给定一个整数数组 nums 和一个整数目标值 target，请你在该数组中找出 和为目标值 target  的那 两个 整数，并返回它们的数组下标。
 * 你可以假设每种输入只会对应一个答案，并且你不能使用两次相同的元素。
 * 你可以按任意顺序返回答案。
 * 示例
 * 输入：nums = [2,7,11,15], target = 9
 * 输出：[0,1]
 * 解释：因为 nums[0] + nums[1] == 9，返回 [0, 1]。
 *
 * @author nicepeng
 * @since 2026/6/16 20:07
 */
public class TwoSum {

    public static void main(String[] args) {
        int[] nums = new int[]{2, 7, 11, 15};
        System.out.println(Arrays.toString(twoSum(nums, 9)));
    }

    /**
     * 思路：
     * 遍历数组时，对于当前元素 nums[i]，先计算补数 complement = target - nums[i]，然后去哈希表中查找。
     *      如果找到，说明之前已经遍历过的某个数与当前数之和为 target，直接返回两个索引。
     *      如果没找到，就把当前的 (nums[i], i) 存入哈希表，继续向后遍历。
     复杂度
        时间复杂度：O(n)，只遍历一次
        空间复杂度：O(n)，最坏存储所有元素
     * @param nums 数组
     * @param target 目标值
     * @return 索引数组
     */
    public static int[] twoSum(int[] nums, int target) {
        // 以空间换时间
        Map<Integer, Integer> valIdxMap = new HashMap<>();
        // 遍历数组
        for (int i = 0; i < nums.length; i++) {
            // 如果补数已经存在于 map 中，说明之前某个元素与当前元素配对成功
            if (valIdxMap.containsKey(target - nums[i])) {
                // 返回之前存储的索引（补数的索引）和当前索引 i
                return new int[]{valIdxMap.get(target - nums[i]), i};
            }
            // 将当前元素及其索引存入哈希表，供后续元素查找
            valIdxMap.put(nums[i], i);
        }
        return new int[]{};
    }
}
