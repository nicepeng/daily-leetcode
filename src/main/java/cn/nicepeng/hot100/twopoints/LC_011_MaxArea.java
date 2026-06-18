package cn.nicepeng.hot100.twopoints;

/**
 * LC_011_MaxArea  11. 盛最多水的容器
 * 给定一个长度为 n 的整数数组 height 。有 n 条垂线，第 i 条线的两个端点是 (i, 0) 和 (i, height[i]) 。
 * <p>
 * 找出其中的两条线，使得它们与 x 轴共同构成的容器可以容纳最多的水。
 * <p>
 * 返回容器可以储存的最大水量。
 *
 * @author nicepeng
 * @since 2026/6/18 19:34
 */
public class LC_011_MaxArea {

    /**
     * 思路：双指针
     * 题干翻译一下：给定一个整数数组 height，每个元素代表一条垂直线的高度，要求找出两条线使得它们与 x 轴构成的面积最大
     * 面积 = 宽度 × 高度（短板），宽度 (j - i)：随着指针靠拢单调递减，高度 min(h[i], h[j])：取决于较短的那条板
     * 这意味着：当我们移动指针时，宽度一定变小，要想面积可能变大，唯一的机会是让"短板"变长。
     * <p>
     *  时间复杂度：O(n)，每次循环至少一个指针移动一步，总共移动 n-1 次
     *  空间复杂度：O(1)，仅使用三个整型变量
     *
     * @param height    数组
     * @return  最大面积
     */
    public static int maxArea(int[] height) {
        // 边界检查
        if (height == null || height.length < 2) {
            return 0;
        }
        // 初始化对撞双指针：分别指向数组首尾
        int left = 0, right = height.length - 1;
        // 记录历史最大盛水量
        int maxWater = 0;
        // ========== 核心：双指针向中间逼近 ==========
        while (left < right) {
            // 当前容器的盛水量 = 短板高度 × 宽度     Math.min 取短板，(right - left) 是宽度
            int curWater = (right - left) * Math.min(height[left], height[right]);
            // 更新全局最大值
            maxWater = Math.max(maxWater, curWater);
            // 🔑 核心决策：移动较短的那一侧指针
            // 原因：只有让短板变高，才可能在宽度缩小的情况下获得更大面积， 移动长板只会让短板不变或更短，面积一定变小
            if (height[left] < height[right]) {
                // 左边是短板，左指针右移，尝试寻找更高的左边界
                left++;
            } else {
                // 右边是短板（或等高），右指针左移，尝试寻找更高的右边界
                // ⚠️ 注意：左右等高时移动哪边都可以，这里统一归入 else
                right--;
            }
        }
        return maxWater;
    }

    public static void main(String[] args) {
        int[] nums = {1, 8, 6, 2, 5, 4, 8, 3, 7};
        System.out.println(maxArea(nums));
    }
}
