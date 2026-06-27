package cn.nicepeng.hot100.array;

/**
 * LC_53_MaxSubArraySum     53. 最大子数组和
 * 给你一个整数数组 nums ，请你找出一个具有最大和的连续子数组（子数组最少包含一个元素），返回其最大和。
 * 子数组是数组中的一个连续部分。
 * 例 1：nums = [-2,1,-3,4,-1,2,1,-5,4] → 输出：6
 * 解释：连续子数组 [4,-1,2,1] 的和最大，为 6
 *
 * @author nicepeng
 * @since 2026/6/26 0:29
 */
public class LC_53_MaxSubArraySum {


    public static int maxSubArray(int[] nums) {
        // 边界处理：数组为空直接返回0（题目保证数组非空，此处为鲁棒性处理）
        if (nums == null || nums.length == 0) {
            return 0;
        }
        // dp[i]：以nums[i]结尾的最大子数组和
        int[] dp = new int[nums.length];
        // 初始状态：第一个元素只能自己组成子数组
        dp[0] = nums[0];
        // 全局最大值，初始化为第一个元素
        int maxSum = dp[0];
        // 从第二个元素开始遍历
        for (int i = 1; i < nums.length; i++) {
            // 状态转移：要么接在前面后面，要么自己重新开始
            dp[i] = Math.max(dp[i - 1] + nums[i], nums[i]);
            // 更新全局最大值
            maxSum = Math.max(dp[i], maxSum);
        }
        return maxSum;
    }

    public static int maxSubArrayPlus(int[] nums) {
        // 边界处理：数组为空直接返回0（题目保证数组非空，此处为鲁棒性处理）
        if (nums == null || nums.length == 0) {
            return 0;
        }
        // preSum：记录以"前一个元素结尾"的最大子数组和，对应dp[i-1]
        int preSum = nums[0];
        // maxSum：全局最大和，初始化为第一个元素
        int maxSum = nums[0];

        // 从第二个元素开始遍历
        for (int i = 1; i < nums.length; i++) {
            // 计算以当前元素结尾的最大子数组和
            // 两种选择：接在前面的后面 / 自己重新开始
            preSum = Math.max(preSum + nums[i], nums[i]);
            // 更新全局最大值
            maxSum = Math.max(preSum, maxSum);
        }
        return maxSum;
    }

    public static void main(String[] args) {
        System.out.println("测试案例一：" + maxSubArrayPlus(new int[]{-2, 1, -3, 4, -1, 2, 1, -5, 4}));   // 6
        System.out.println("测试案例二：" + maxSubArrayPlus(new int[]{-3, -1, -2}));    // -1
        System.out.println("测试案例三：" + maxSubArrayPlus(new int[]{1, 2, 3, 4}));    // 10
        System.out.println("测试案例四：" + maxSubArrayPlus(new int[]{-2, 0, -1}));    // 0
    }
}
