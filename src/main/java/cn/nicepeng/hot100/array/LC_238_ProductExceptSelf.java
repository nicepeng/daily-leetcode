package cn.nicepeng.hot100.array;

import java.util.Arrays;

/**
 * LC_238_ProductExceptSelf     238. 除了自身以外数组的乘积
 * 给你一个整数数组 nums，返回 数组 answer ，其中 answer[i] 等于 nums 中除了 nums[i] 之外其余各元素的乘积 。
 * 题目数据 保证 数组 nums之中任意元素的全部前缀元素和后缀的乘积都在  32 位 整数范围内。
 * 请 不要使用除法，且在 O(n) 时间复杂度内完成此题。
 * @author nicepeng
 * @since 2026/6/27 1:25
 */
public class LC_238_ProductExceptSelf {


    /**
     * 思路一：左右乘积数组版（直观好理解，推荐入门）
     * 提前计算出两个辅助数组：
     *      left[i]：下标 i 左侧所有元素的乘积（不包含 nums[i]）
     *      right[i]：下标 i 右侧所有元素的乘积（不包含 nums[i]）
     *  最终 answer[i] = left[i] * right[i]。
     *  时间复杂度：O (n)，总共三次线性遍历，总次数为常数倍 n
     *  空间复杂度：O (n)，使用了两个辅助数组
     * @param nums
     * @return
     */
    public static int[] productExceptSelf_V1(int[] nums) {
        int n = nums.length;
        // 左乘积数组：left[i] 表示i左侧所有元素的乘积（不含nums[i]）
        int[] left = new int[n];
        // 右乘积数组：right[i] 表示i右侧所有元素的乘积（不含nums[i]）
        int[] right = new int[n];
        // 结果数组
        int[] ans = new int[n];

        // 第一步：计算左乘积数组
        // 第0个元素左边没有元素，乘积为乘法单位元1
        left[0] = 1;
        for (int i = 1; i < n; i++) {
            // i左侧乘积 = i-1左侧乘积 × nums[i-1]
            left[i] = left[i - 1] * nums[i - 1];
        }

        // 第二步：计算右乘积数组
        // 最后一个元素右边没有元素，乘积为1
        right[n - 1] = 1;
        for (int i = n - 2; i >= 0; i--) {
            // i右侧乘积 = i+1右侧乘积 × nums[i+1]
            right[i] = right[i + 1] * nums[i + 1];
        }

        // 第三步：左右乘积相乘，得到最终结果
        for (int i = 0; i < n; i++) {
            ans[i] = left[i] * right[i];
        }

        return ans;
    }


    /**
     * 思路二：思路一空间优化（面试标准写法，推荐）
     * 左乘积数组的计算逻辑很简单，可以直接在结果数组上计算（结果数组不算额外空间）
     * 右乘积不需要存整个数组，只用一个变量在从右往左遍历时动态维护即可
     * 时间复杂度：O (n)，两次线性遍历
     * 空间复杂度：O (1)，仅使用一个额外变量，输出数组不计入额外空间
     * @param nums
     * @return
     */
    public static int[] productExceptSelf_V2(int[] nums) {
        int n = nums.length;
        int[] ans = new int[n];
        // 第一步：从左到右，把 ans 当作左乘积数组使用
        // ans[i] 表示i左侧所有元素的乘积（不含nums[i]）
        ans[0] = 1;
        for (int i = 1; i < n; i++) {
            ans[i] = ans[i - 1] * nums[i - 1];
        }

        // 第二步：从右到左，用变量维护右侧乘积，同步计算最终结果
        // right 记录当前位置右侧所有元素的乘积
        int right = 1;
        for (int i = n - 1; i >= 0; i--) {
            // 左乘积（已存在ans[i]） × 右乘积 = 最终结果
            ans[i] = ans[i] * right;
            // 更新right：把当前元素加入右侧乘积，供左边的位置使用
            right = right * nums[i];
        }

        return ans;
    }

    /**
     * 思路二：双指针
     * @param nums
     * @return
     */
    public static int[] productExceptSelf(int[] nums) {
        int[] ans = new int[nums.length];
        Arrays.fill(ans, 1);
        int preProduct = 1, postProduct = 1;
        for (int left = 0, right = nums.length - 1; left < nums.length; left++, right--) {
            ans[left] *= preProduct;
            ans[right] *= postProduct;
            preProduct *= nums[left];
            postProduct *= nums[right];
        }
        return ans;
    }

    public static void main(String[] args) {
        int[] nums = {1,2,3,4};
        System.out.println(Arrays.toString(productExceptSelf_V2(nums)));    // [24, 12, 8, 6]
    }
}
