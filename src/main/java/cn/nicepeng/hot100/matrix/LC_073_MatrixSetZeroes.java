package cn.nicepeng.hot100.matrix;

import java.util.Arrays;

/**
 * MatrixSetZeroes  73. 矩阵置零
 * 给定一个 m x n 的矩阵，如果一个元素为 0 ，则将其所在行和列的所有元素都设为 0 。请使用 原地 算法。
 *
 * @author nicepeng
 * @since 2026/6/23 20:18
 */
public class LC_073_MatrixSetZeroes {

    /**
     * 思路一：额外标记数组
     * 最直观的做法：用两个布尔数组分别记录哪些行、哪些列需要置零，然后再遍历矩阵执行置零。
     * rowZero[i] = true → 第 i 行需要全部置零
     * colZero[j] = true → 第 j 列需要全部置零
     * ⏱ O(mn) | 💾 O(m+n)
     * @param matrix    原矩阵
     */
    public static void setZeroes(int[][] matrix) {
        int m = matrix.length, n = matrix[0].length;
        // 两个标记数组，分别记录需要置零的行和列
        boolean[] rowZero = new boolean[m];
        boolean[] colZero = new boolean[n];
        // 第一遍扫描：标记所有包含 0 的行和列
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (matrix[i][j] == 0) {
                    rowZero[i] = true;
                    colZero[j] = true;
                }
            }
        }
        // 第二遍扫描：根据标记数组将对应位置置零
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (rowZero[i] || colZero[j]) {
                    matrix[i][j] = 0;
                }
            }
        }
    }

    /**
     * 思路二：矩阵首行首列做标记    ✅ 最优解
     * 思路一用了两个数组来标记，细想一下，能否把这两个数组给优化掉？欸，还真可以，既然不能用额外数组，借用矩阵自身的第一行和第一列作为标记数组就行。
     * 🔑 核心技巧：复用输入矩阵的首行首列作为标记存储，仅用 2 个布尔变量补偿被覆盖的信息。
     * ⏱ O(mn) | 💾 O(1)
     * @param matrix
     */
    public static void setZeroes_plus(int[][] matrix) {
        int m = matrix.length, n = matrix[0].length;
        // 提前记录首行、首列本身是否包含零，因为后续会把首行首列当作标记数组使用，原始信息会被覆盖
        boolean firstRowHasZero = false;
        boolean firstColHasZero = false;

        //【步骤1】提前记录首行、首列本身是否包含零
        // 检查首行是否有零
        for (int i = 0; i < n; i++) {
            if (matrix[0][i] == 0) {
                firstRowHasZero = true;
                break;
            }
        }

        // 检查首列是否有零
        for (int i = 0; i < m; i++) {
            if (matrix[i][0] == 0) {
                firstColHasZero = true;
                break;
            }
        }

        // 【步骤2】用首行首列作为标记数组 只遍历内部区域 [1..m-1][1..n-1]，发现 matrix[i][j]==0 时，在首行首列对应位置打标记
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                if (matrix[i][j] == 0) {
                    matrix[i][0] = 0;
                    matrix[0][j] = 0;
                }
            }
        }

        // 【步骤3】根据首行首列的标记，对内部区域执行置零。注意：必须从 [1][1] 开始，不能破坏标记本身
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                if (matrix[i][0] == 0 || matrix[0][j] == 0) {
                    matrix[i][j] = 0;
                }
            }
        }

        // 【步骤4】最后处理首行和首列，必须在步骤3之后执行，否则标记会被提前清除
        if (firstRowHasZero) {
            for (int j = 0; j < n; j++) {
                matrix[0][j] = 0;
            }
        }

        if (firstColHasZero) {
            for (int i = 0; i < m; i++) {
                matrix[i][0] = 0;
            }
        }

    }


    public static void main(String[] args) {
        int[][] matrix = {{0,1,2,0}, {3,4,5,2}, {1,3,1,5}};
        setZeroes_plus(matrix);
        System.out.println(Arrays.deepToString(matrix));
    }
}
