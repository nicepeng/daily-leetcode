package cn.nicepeng.hot100.twopoints;

/**
 * LC_042_TrapRainWater 42. 接雨水
 * 给定 n 个非负整数表示每个宽度为 1 的柱子的高度图，计算按此排列的柱子，下雨之后能接多少雨水。
 * 思考过程：每个位置能存多少水？
 * 水位高度由"左右边界的短板"决定，对于任意位置 i，它能存的雨水量取决于左右两侧最高柱子中较矮的那个与当前柱子高度的差值：
 * water[i]=max(0, min(leftMax[i], rightMax[i]) − height[i])
 *
 * @author nicepeng
 * @since 2026/6/18 22:04
 */
public class LC_042_TrapRainWater {

    /**
     * 思路一：暴力法
     * 核心思想：对每个位置 i，分别向左、向右扫描找到最高柱子
     * 水位高度由"左右边界的短板"决定，对于任意位置 i，它能存的雨水量取决于左右两侧最高柱子中较矮的那个与当前柱子高度的差值：
     * water[i]=max(0, min(leftMax[i], rightMax[i]) − height[i])
     * ⏱ 时间复杂度：O(N^2)
     * 💾 空间复杂度：O(1)
     *
     * @param height 数组
     * @return 雨水量
     */
    public static int trapRainWater_01(int[] height) {
        // 边界条件
        if (height == null || height.length < 3) {
            return 0;
        }
        int totalWater = 0;
        // 遍历每个位置（首尾不可能积水，可从 1到 n-2）
        for (int i = 1; i < height.length - 1; i++) {
            int leftMax = 0;
            // 向左扫描找 [0..i] 的最大值
            for (int j = 0; j <= i; j++) {
                leftMax = Math.max(leftMax, height[j]);
            }
            // 向右扫描找 [i..n-1] 的最大值
            int rightMax = 0;
            for (int j = i; j < height.length; j++) {
                rightMax = Math.max(rightMax, height[j]);
            }

            // 当前位置积水量 = min(左最大, 右最大) - 当前高度
            // leftMax/rightMax 至少为 height[i]，所以结果非负
            totalWater += Math.min(leftMax, rightMax) - height[i];
        }
        return totalWater;
    }

    /**
     * 思路二：空间换时间，用两数组提前记录左右最大值 （✅面试首选保底）
     * 核心思想：用两个数组提前记录每个位置的左侧最大值和右侧最大值，将内层扫描消除。
     * @param height 数组
     * @return 雨水量
     * ⏱ 时间复杂度：O(N)
     * 💾 空间复杂度：O(N)
     */
    public static int trapRainWater_02(int[] height) {
        // 边界条件
        if (height == null || height.length < 3) {
            return 0;
        }
        // ========== 第一步：预处理左侧最大值数组 ==========
        // leftMax[i] = max(height[0], height[1], ..., height[i])
        int[] leftMax = new int[height.length];
        leftMax[0] = height[0];
        for (int i = 1; i < height.length; i++) {
            // 递推关系：当前位置的左侧最大值 = max(前一个位置的左侧最大值, 当前高度)
            leftMax[i] = Math.max(leftMax[i - 1], height[i]);
        }

        // ========== 第二步：预处理右侧最大值数组 ==========
        // rightMax[i] = max(height[i], height[i+1], ..., height[n-1])
        int[] rightMax = new int[height.length];
        rightMax[height.length - 1] = height[height.length - 1];
        for (int i = height.length - 2; i >= 0; i--) {
            // 从右往左递推：当前位置的右侧最大值 = max(前一个位置的右侧最大值, 当前高度)
            rightMax[i] = Math.max(rightMax[i + 1], height[i]);
        }

        // ========== 第三步：逐位置计算积水量 ==========
        int totalWater = 0;
        for (int i = 1; i < height.length; i++) {
            // 直接查数组获取左右最大值，O(1) 完成计算
            totalWater += Math.min(leftMax[i], rightMax[i]) - height[i];
        }

        return totalWater;
    }

    /**
     * 思路三：对撞双指针（最优解）
     * 核心思想：利用"较小 max 侧可独立确定水位"的性质，用两个变量代替两个数组，实现 O(1) 空间。
     * @param height    数组
     * @return  雨水量
     */
    public static int trapRainWater_03(int[] height) {
        // 边界条件
        if (height == null || height.length < 3) {
            return 0;
        }
        int left = 0, right = height.length - 1;
        int leftMax = 0, rightMax = 0;
        int totalWater = 0;

        while (left < right) {
            // 🔑 第一步：先更新两侧的已知最大值 （必须在计算水量之前更新，否则新最大值会导致负数）
            leftMax = Math.max(leftMax, height[left]);
            rightMax = Math.max(rightMax, height[right]);

            // 🔑 第二步：处理 max 较小的一侧
            // 若 leftMax <= rightMax，则对于位置 left：
            // 真实leftMax[left] == leftMax（左边已全部扫描），真实rightMax[left] >= rightMax >= leftMax
            // ⇒ min(真实左max, 真实右max) == leftMax    即水位完全由 leftMax 决定，无需知道右边精确值
            if (leftMax <= rightMax) {
                totalWater += leftMax - height[left];
                left++;
            } else {
                // 对称情况：右侧 max 更小，right 位置水位由 rightMax 决定
                totalWater += rightMax - height[right];
                right--;
            }
        }
        return totalWater;
    }

    public static void main(String[] args) {
        int[] height = {0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1};
        System.out.println(trapRainWater_03(height));
        int[] nums = {4, 2, 0, 3, 2, 5};
        System.out.println(trapRainWater_03(nums));
    }
}
