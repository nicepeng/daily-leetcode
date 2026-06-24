package cn.nicepeng.hot100.matrix;

import java.util.ArrayList;
import java.util.List;

/**
 * LC_054_SpiralMatrix  54. 螺旋矩阵     矩阵模拟题
 *
 * @author nicepeng
 * @since 2026/6/23 21:12
 */
public class LC_054_SpiralMatrix {

    /**
     * 思路一：四边界收缩法（面试首选，推荐 ✅）
     * 定义四个边界变量：top, bottom, left, right，按照 右→下→左→上 的顺序逐层遍历，每完成一个方向就收缩对应边界。当边界交叉时停止。
     *  🔑 关键细节：第3、4步必须加边界检查！因为第1、2步执行后边界可能已经交叉，不加判断会导致重复添加元素。
     *  ⏱ O(mn) | 💾 O(1)（不计输出列表）
     * @param matrix
     * @return
     */
    public static List<Integer> spiralOrder(int[][] matrix) {
        List<Integer> result = new ArrayList<>();

        if (matrix == null || matrix.length == 0)   return result;

        int m = matrix.length, n = matrix[0].length;

        int top = 0, bottom = m - 1, left = 0, right = n - 1;

        while (top <= bottom && left <= right) {
            // 【第1步】从左到右遍历顶行
            for (int j = left; j <= right; j++) {
                result.add(matrix[top][j]);
            }
            top++;
            // 【第2步】从上到下遍历右列
            for (int i = top; i <= bottom; i++) {
                result.add(matrix[i][right]);
            }
            right--;
            // 【第3步】从右到左遍历底行
            if (top <= bottom) {
                for (int j = right; j >= left; j--) {
                    result.add(matrix[bottom][j]);
                }
                bottom--;
            }

            if (left < right) {
                // 【第4步】从下到上遍历左列
                for (int i = bottom; i >= top; i--) {
                    result.add(matrix[i][left]);
                }
                left++;
            }

        }
        return result;
    }

    /**
     * 思路一四边界收缩法换一种方式实现
     *
     * @param matrix
     * @return
     */
    public static List<Integer> spiralOrder_02(int[][] matrix) {
        List<Integer> result = new ArrayList<>();

        if (matrix == null || matrix.length == 0)   return result;

        int m = matrix.length, n = matrix[0].length;

        int top = 0, bottom = m - 1, left = 0, right = n - 1;
        while (true) {
            // 【第1步】从左到右遍历
            for (int j = left; j <= right; j++) {
                result.add(matrix[top][j]);
            }
            top++;
            if (top > bottom)   break;

            // 【第2步】从上到下遍历
            for (int i = top; i <= bottom; i++) {
                result.add(matrix[i][right]);
            }
            right--;
            if (right < left)   break;

            // 【第3步】从右到左遍历
            for (int j = right; j >= left; j--) {
                result.add(matrix[bottom][j]);
            }
            bottom--;
            if (bottom < top)   break;

            // 【第4步】从下到上遍历
            for (int i = bottom; i >= top; i--) {
                result.add(matrix[i][left]);
            }
            left++;
            if (left > right)   break;

        }
        return result;
    }


    public static void main(String[] args) {
        int[][] matrix = {{1,2,3},
                          {4,5,6},
                          {7,8,9}};
        System.out.println(spiralOrder_02(matrix));
        System.out.println(spiralOrder_02(new int[][]{
                {1,2,3,4},
                {5,6,7,8},
                {9,10,11,12}}));

    }
}
