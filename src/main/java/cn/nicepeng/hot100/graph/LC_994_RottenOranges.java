package cn.nicepeng.hot100.graph;

import java.util.LinkedList;
import java.util.Queue;

/**
 * LC_994_RottenOranges     994. 腐烂的橘子
 * 在给定的 m x n 网格 grid 中，每个单元格可以有以下三个值之一：
 * <p>
 * 值 0 代表空单元格；
 * 值 1 代表新鲜橘子；
 * 值 2 代表腐烂的橘子。
 * 每分钟，腐烂的橘子 周围 4 个方向上相邻 的新鲜橘子都会腐烂。
 * <p>
 * 返回 直到单元格中没有新鲜橘子为止所必须经过的最小分钟数。如果不可能，返回 -1 。
 *
 * @author nicepeng
 * @since 2026/6/25 0:42
 */
public class LC_994_RottenOranges {

    public static int orangesRotting(int[][] grid) {
        int m = grid.length, n = grid[0].length;

        // BFS队列，存储腐烂橘子的坐标，每个元素是长度为2的数组 [行号, 列号]
        Queue<int[]> queue = new LinkedList<>();
        int fresh = 0;

        // 1. 初始统计新鲜橘子个数，并将所有腐烂橘子入队
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 2) {
                    // 腐烂橘子：加入队列，作为BFS初始起点（第0分钟）
                    queue.offer(new int[]{i, j});
                } else if (grid[i][j] == 1) {
                    // 新鲜橘子：计数+1
                    fresh++;
                }
            }
        }

        // 特殊情况：一开始就没有新鲜橘子，直接返回0
        if (fresh == 0) return 0;

        int minutes = 0;
        // 方向数组：分别对应 上、下、左、右 四个方向的坐标偏移
        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

        // 2. BFS层序遍历，模拟每分钟的扩散
        // 循环条件：队列不为空（还有腐烂橘子能扩散） 且 还有新鲜橘子
        while (!queue.isEmpty() && fresh > 0) {
            // 记录当前层的节点数量（当前分钟有多少个腐烂橘子要扩散）
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                int[] rotten = queue.poll();
                int currRow = rotten[0], currCol = rotten[1];
                // 尝试向四个方向感染
                for (int[] dir : directions) {
                    int newRow = currRow + dir[0];
                    int newCol = currCol + dir[1];
                    // 判断新坐标是否合法：在网格范围内，且是新鲜橘子
                    if (newRow >= 0 && newRow < m && newCol >= 0 && newCol < n && grid[newRow][newCol] == 1) {
                        // 感染：把新鲜橘子标记为腐烂
                        grid[newRow][newCol] = 2;
                        // 新鲜橘子数量减1
                        fresh--;
                        // 新腐烂的橘子加入队列，下一层（下一分钟）继续扩散
                        queue.offer(new int[]{newRow, newCol});
                    }
                }
            }
            // 当前层处理完，代表一分钟过去了，时间+1
            minutes++;
        }
        // 3. 如果新鲜橘子全部腐烂，返回分钟数；否则说明有橘子被隔绝，返回-1
        return fresh == 0 ? minutes : -1;
    }

    public static void main(String[] args) {
        int[][] grid1 = {
                {2, 1, 1},
                {1, 1, 0},
                {0, 1, 1}};
        int[][] grid2 = {
                {2, 1, 1},
                {0, 1, 1},
                {1, 0, 1}};
        System.out.println(orangesRotting(grid2));
    }
}
