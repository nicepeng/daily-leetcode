package cn.nicepeng.hot100.twopoints;

import java.util.Arrays;

/**
 * 283. 移动零
 *  给定一个数组 nums，编写一个函数将所有 0 移动到数组的末尾，同时保持非零元素的相对顺序。
 *  请注意 ，必须在不复制数组的情况下原地对数组进行操作。
 * 示例 1:
 * <p>
 * 输入: nums = [0,1,0,3,12]
 * 输出: [1,3,12,0,0]
 *
 * @author nicepeng
 * @since 2026/6/17 20:43
 */
public class LC_283_MoveZeros {

    public static void main(String[] args) {
        int[] nums = {1, 5, 0, 2, 0, 3, 12};
        moveZeroes(nums);
        System.out.println(Arrays.toString(nums));
    }

    /**
     * 思路：  快慢双指针
     * 题干要求原地对数组操作，即不能申请额外的数组空间。
     * 定义两个指针，
     *   慢指针 slow：指向"下一个非零元素应该放置的位置"。[0, slow) 区间内全是已处理好的非零元素（保持原序）。
     *   快指针 fast：负责扫描整个数组，寻找非零元素。
     * 时间复杂度：O(N)，快指针遍历一次数组，每个元素最多被交换1次
     * 空间复杂度：O(1)，仅使用两个指针变量，完全原地操作
     * @param nums  原数组
     */
    public static void moveZeroes(int[] nums) {
        // 边界情况：空数组或单元素无需处理
        if (nums == null || nums.length <= 1) {
            return;
        }

        // slow: 下一个非零元素应该放置的位置
        // fast: 当前正在扫描的元素位置
        int slow = 0;

        // ========== 核心：快指针遍历整个数组 ==========
        for (int fast = 0; fast < nums.length; fast++) {
            // 只有当快指针指向非零元素时，才需要处理
            if (nums[fast] != 0) {
                // 交换 slow 和 fast 下标的元素，把 slow 位置的零"换"到 fast 位置
                swap(nums, slow, fast);
                // 交换后，slow 右移，指向下一个待填充位置
                slow++;
            }
        }
        // 循环结束后：[0, slow) 全是非零元素（保持原序），[slow, n) 全是零
    }

    private static void swap(int[] nums, int slow, int fast) {
        // 相同位置无需交换，避免无意义操作
        if (slow == fast)   return;

        int temp = nums[slow];
        nums[slow] = nums[fast];
        nums[fast] = temp;
    }
}
