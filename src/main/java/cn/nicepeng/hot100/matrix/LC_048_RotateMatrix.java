package cn.nicepeng.hot100.matrix;

import java.util.Arrays;

/**
 * LC_048_RotateMatrix  48. 旋转图像
 * 给定一个 n × n 的二维矩阵 matrix 表示一个图像。请你将图像顺时针旋转 90 度。
 * 你必须在 原地 旋转图像，这意味着你需要直接修改输入的二维矩阵。请不要 使用另一个矩阵来旋转图像。
 *
 * @author nicepeng
 * @since 2026/6/23 22:50
 */
public class LC_048_RotateMatrix {

    /**
     * 思路一：转置 + 翻转（推荐 ✅ 最优解）
     * 顺时针旋转 90° = 沿主对角线转置 + 水平镜像翻转
     * ⏱ O(n²) | 💾 O(1)
     * 🔑 记忆口诀："先转置，再翻转 = 顺时针90°"
     * 扩展：
     *      逆时针 90°   转置 + 垂直翻转
     *      180°        水平翻转 + 垂直翻转
     * @param matrix
     */
    public static void rotate(int[][] matrix) {
        if (matrix == null || matrix.length == 0)   return;
        int m = matrix.length, n = matrix[0].length;
        // 【第1步】沿主对角线转置（左上→右下对角线）  只遍历上三角区域 (j > i)，避免交换两次恢复原状
        // 转置后：matrix[i][j] ↔ matrix[j][i]
        for (int i = 0; i < m; i++) {
            for (int j = i + 1; j < n; j++) {
                int temp = matrix[i][j];
                matrix[i][j] = matrix[j][i];
                matrix[j][i] = temp;
            }
        }

        // 【第2步】水平翻转（每行左右镜像）    每行只需遍历前半部分，与对称位置交换
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n / 2; j++) {
                int temp = matrix[i][j];
                matrix[i][j] = matrix[i][n - 1 - j];
                matrix[i][n - 1 - j] = temp;
            }
        }
    }


    public static void main(String[] args) {
        int[][] matrix = {{1,2,3}, {4,5,6}, {7,8,9}};
        rotate(matrix);
        System.out.println(Arrays.deepToString(matrix));
    }
}
