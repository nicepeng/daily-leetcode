package cn.nicepeng.hot100.prefixsum;

import java.util.HashMap;
import java.util.Map;

/**
 * LC_560_SubarraySumIsK    560. 和为 K 的子数组
 * 给你一个整数数组 nums 和一个整数 k ，请你统计并返回 该数组中和为 k 的子数组的个数 。子数组是数组中元素的“连续”非空序列。
 *
 * @author nicepeng
 * @since 2026/6/19 0:12
 */
public class LC_560_SubarraySumIsK {

    /**
     * 思路一：暴力枚举
     * 枚举所有子数组 [i, j]，计算其元素之和，判断是否等于 k。
     * 固定起点 i，向右扩展终点 j 时累加当前元素，避免重复求和 → O(n²)
     * ⏱ 时间复杂度：O(n²) | 💾 空间复杂度：O(1)
     *
     * @param nums
     * @param k
     * @return
     */
    public static int subarraySum_01(int[] nums, int k) {
        int count = 0;
        int len = nums.length;

        // 枚举子数组的左端点 i
        for (int i = 0; i < len; i++) {
            int sum = 0;
            // 从左端点 i 开始，逐步扩展右端点 j，每次只新增一个元素，避免重新遍历求和
            for (int j = i; j < len; j++) {
                // 累加 nums[j]，sum 即为 nums[i..j] 的和
                sum += nums[j];
                if (sum == k) {
                    count += 1;
                }
            }
        }
        return count;
    }


    /**
     * 思路二：前缀和 + 双重循环
     * 引入前缀和数组 preSum，其中 preSum[i] = nums[0] + nums[1] + ... + nums[i-1]（即前 i 个元素的和）
     * 那么子数组 nums[i..j] 的和 = preSum[j+1] - preSum[i]
     * ⏱ 时间复杂度：O(n²) | 💾 空间复杂度：O(n)
     *
     * @param nums
     * @param k
     * @return
     */
    public static int subarraySum_02(int[] nums, int k) {

        int len = nums.length;
        // 构建前缀和数组，长度为 n+1
        // preSum[0] = 0, preSum[i] = nums[0]+...+nums[i-1]
        int[] preSumArr = new int[len + 1];
        for (int i = 0; i < len; i++) {
            preSumArr[i + 1] = preSumArr[i] + nums[i];
        }
        int count = 0;
        // 枚举所有子数组 [i, j] ，子数组 nums[i..j] 的和 = preSum[j+1] - preSum[i]
        for (int i = 0; i < len + 1; i++) {
            for (int j = i; j < len + 1; j++) {
                if (preSumArr[j] - preSumArr[i] == k) {
                    count += 1;
                }
            }
        }
        return count;
    }


    /**
     * 思路三：前缀和 + 哈希表
     * 用哈希表记录每个前缀和出现的次数，一次遍历即可完成统计
     * 那么子数组 nums[i..j] 的和 = preSum[j+1] - preSum[i]
     * ⏱ 时间复杂度：O(n) | 💾 空间复杂度：O(n)
     *
     * @param nums
     * @param k
     * @return
     */
    public static int subarraySum_03(int[] nums, int k) {
        int count = 0;
        int preSum = 0;  // 当前前缀和，无需数组，滚动变量即可

        // key: 某个前缀和的值，value: 该前缀和在遍历过程中出现的次数
        Map<Integer, Integer> prefixCount = new HashMap<>();
        // 为什么？因为当某个前缀和本身就等于 k 时，preSum - k = 0，表示从数组开头到 j 的子数组和恰好为 k，0 出现 1 次代表"空前缀"的存在。
        prefixCount.put(0, 1);

        for (int num : nums) {
            preSum += num;

            // 查找之前有多少个前缀和等于 (preSum - k) ，每一个这样的前缀和对应一个和为 k 的子数组
            int target = preSum - k;
            if (prefixCount.containsKey(target)) {
                count += prefixCount.get(target);
            }
            // 将当前前缀和存入哈希表，增加对应次数
            prefixCount.put(preSum, prefixCount.getOrDefault(preSum, 0) + 1);
        }
        return count;
    }

    public static void main(String[] args) {
        System.out.println(subarraySum_03(new int[]{1, 1, 1}, 2));
        System.out.println(subarraySum_03(new int[]{1, 2, 3}, 3));
    }
}
