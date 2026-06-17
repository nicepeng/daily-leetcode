package cn.nicepeng.hot100.hash;

import java.util.*;

/**
 * 128. 最长连续序列
 * 给定一个未排序的整数数组 nums ，找出数字连续的最长序列（不要求序列元素在原数组中连续）的长度。
 * <p>
 * 请你设计并实现时间复杂度为 O(n) 的算法解决此问题。
 * <p>
 * 示例 1：
 * <p>
 * 输入：nums = [100,4,200,1,3,2]
 * 输出：4
 * 解释：最长数字连续序列是 [1, 2, 3, 4]。它的长度为 4。
 *
 * @author nicepeng
 * @since 2026/6/17 20:07
 */
public class LC_128_LongestConsecutive {

    public static void main(String[] args) {
        int[] nums = new int[]{100, 4, 200, 1, 3, 2};
        System.out.println(longestConsecutive(nums));
    }


    /**
     * 思路：哈希set
     * 只有当 num-1 不在 Set 中时，num 才是一个连续序列的起点。我们只对起点向右扩展。
     * 时间复杂度：O(N)，创建 Set O(n) + 每个元素最多被扩展访问1次 O(n) = O(n)
     * 空间复杂度：O(N)，HashSet 存储所有不重复元素
     *
     * @param nums  原数组
     * @return  最长连续序列的长度
     */
    public static int longestConsecutive(int[] nums) {
        // 边界情况：空数组直接返回0
        if (nums == null || nums.length == 0) {
            return 0;
        }

        // ========== 第一步：将所有数字存入 HashSet ==========
        // 目的：实现 O(1) 的元素存在性查询
        // 同时自动去重，避免重复元素干扰计数
        Set<Integer> set = new HashSet<>();
        for (int num : nums) {
            set.add(num);
        }

        // 记录全局最长连续序列长度
        int maxLength = 0;

        // ========== 第二步：遍历每个数字，仅对"序列起点"进行扩展 ==========
        for (int num : set) {
            //  核心判断：如果 num-1 存在于集合中，说明 num 不是某个连续序列的起点，直接跳过，这保证了每个序列只会被它的"最小值"触发一次扩展
            if (set.contains((num - 1))) {
                continue;
            }
            // 走到这里，说明 num 是一个连续序列的起点，从 num 开始，不断向右查找 num+1, num+2, ...
            // 当前正在检查的数字
            int currentNum = num;
            // 当前连续序列的长度（至少包含起点自身）
            int currentLength = 1;

            // 向右扩展：只要下一个连续数字存在，就继续
            while (set.contains(currentNum + 1)) {
                // 移动到下一个连续数字
                currentNum += 1;
                // 序列长度+1
                currentLength += 1;
            }
            // 用当前序列长度更新全局最大值
            maxLength = Math.max(maxLength, currentLength);
        }

        return maxLength;
    }
}
