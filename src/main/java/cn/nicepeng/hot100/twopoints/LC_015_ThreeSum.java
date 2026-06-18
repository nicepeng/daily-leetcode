package cn.nicepeng.hot100.twopoints;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * LC_015_ThreeSum  15. 三数之和
 * 给你一个整数数组 nums ，判断是否存在三元组 [nums[i], nums[j], nums[k]] 满足 i != j、i != k 且 j != k ，
 * 同时还满足 nums[i] + nums[j] + nums[k] == 0 。请你返回所有和为 0 且不重复的三元组。
 * 注意：答案中不可以包含重复的三元组。
 *
 * @author nicepeng
 * @since 2026/6/18 20:34
 */
public class LC_015_ThreeSum {

    /**
     * 思路：排序 + 双指针逼近
     * 先排序，再固定第一个数，然后循环双指针逼近，获得解
     *
     * @param nums 数组
     * @return 三元组集合
     */
    public static List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();

        // 边界检查：不足3个数直接返回空列表
        if (nums == null || nums.length < 3) {
            return result;
        }

        // ========== 第一步：排序（双指针的前提）==========
        Arrays.sort(nums);

        // ========== 第二步：枚举第一个数 ==========
        for (int i = 0; i < nums.length - 2; i++) {
            // 🔑 剪枝1：第一个数 > 0，后面全为正数，不可能凑出0
            if (nums[i] > 0) {
                break;
            }
            // 🔑 去重1：跳过相同的第一个数，避免重复三元组。注意：是跟"前一个"比，不是跟"后一个"比，i > 0 防止越界
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }
            // 🔑 剪枝2：当前i + 最小两数 > 0 → 后续无解
            if ((long) nums[i] + nums[i + 1] + nums[i + 2] > 0) {
                break;
            }
            // 🔑 剪枝3：当前i + 最大两数 < 0 → 当前i太小，跳过
            if ((long) nums[i] + nums[nums.length - 1] + nums[nums.length - 2] < 0) {
                continue;
            }

            // ========== 第三步：对撞双指针寻找另外两个数 ==========
            int left = i + 1, right = nums.length - 1;
            while (left < right) {
                int sum = nums[i] + nums[left] + nums[right];
                if (sum == 0) {
                    // ✅ 找到一组合法三元组
                    result.add(Arrays.asList(nums[i], nums[left], nums[right]));
                    // 🔑 去重2：跳过相同的第二个数
                    while (left < right && nums[left] == nums[left + 1]) {
                        left++;
                    }
                    // 🔑 去重3：跳过相同的第三个数
                    while (left < right && nums[right] == nums[right - 1]) {
                        right--;
                    }
                    // 移动到下一组不同的数
                    left++;
                    right--;
                } else if (sum < 0) {
                    // 和太小，左指针右移增大
                    left++;
                } else {
                    // 和太大，右指针左移减小
                    right--;
                }
            }
        }
        return result;
    }

    public static void main(String[] args) {
        int[] nums = {-1, 0, 1, 2, -1, -4};
        System.out.println(threeSum(nums));
    }
}
