package cn.nicepeng.hot100.graph;

import java.util.LinkedList;
import java.util.Queue;

/**
 * NumIslands   200. 岛屿数量
 * 给你一个由 '1'（陆地）和 '0'（水）组成的的二维网格，请你计算网格中岛屿的数量。
 * 岛屿总是被水包围，并且每座岛屿只能由水平方向和/或竖直方向上相邻的陆地连接形成。
 * 此外，你可以假设该网格的四条边均被水包围。
 *
 * @author nicepeng
 * @since 2026/6/19 8:51
 */
public class LC_200_NumIslands {

    /**
     *  思路一：BFS
     *  核心思想：遇到 '1' 时，用队列向外层层扩展，将所有相连的 '1' 标记为已访问（直接改为 '0'），防止重复计数。
     *  ⏱ 时间 O(M×N)
     *  💾 空间 O(min(M,N))（BFS队列最坏情况）
     * @param grid  岛屿数组
     * @return  岛屿数量
     */
    public static int numIslands(char[][] grid) {
        // 边界条件
        if (grid == null || grid.length == 0)   return 0;
        int ans = 0;

        for (int r = 0; r < grid.length; r++) {
            for (int c = 0; c < grid[0].length; c++) {
                // 🔑 发现新的陆地 → 这是一个新岛屿的起点
                if (grid[r][c] == '1') {
                    // 用 BFS 将整个岛屿"淹没"（标记为已访问）
                    dfs(grid, r, c);
                    ans++;
                }
            }
        }
        return ans;
    }

    /**
     * 思路：广度优先遍历 BFS
     * 借用一个队列 queue，判断队列首部节点 (i, j) 是否未越界且为 1：
     *      若是则置零（删除岛屿节点），并将此节点上下左右节点 (i+1,j),(i-1,j),(i,j+1),(i,j-1) 加入队列；
     *      若不是则跳过此节点；
     *  循环 pop 队列首节点，直到整个队列为空，此时已经遍历完此岛屿。
     *
     * @param grid  网格数组
     * @param r 行下标
     * @param c 列下标
     */
    private static void bfs(char[][] grid, int r, int c) {
        Queue<int[]> queue = new LinkedList<>();
        // 入队
        queue.offer(new int[]{r, c});

        while (!queue.isEmpty()) {
            // 出队
            int[] cur = queue.poll();
            r = cur[0];
            c = cur[1];
            // 边界检查 + 是否为未访问的陆地
            if (r >= 0 && r < grid.length && c >= 0 && c < grid[0].length && grid[r][c] == '1') {
                // 将可达的1置为 .
                grid[r][c] = '.';
                // 上下左右元素入队
                queue.offer(new int[]{r - 1, c});   // 上
                queue.offer(new int[]{r + 1, c});   // 下
                queue.offer(new int[]{r, c - 1});   // 左
                queue.offer(new int[]{r, c + 1});   // 右
            }

        }
    }

    /**
     * 思路：深度优先遍历 DFS
     * 设目前指针指向一个岛屿中的某一点 (i, j)，寻找包括此点的岛屿边界
     *      从 (i, j) 向此点的上下左右 (i+1,j),(i-1,j),(i,j+1),(i,j-1) 做深度搜索。
     *      终止条件：
     *          (i, j) 越过矩阵边界;
     *          grid[i][j] == 0，代表此分支已越过岛屿边界。
     *      搜索岛屿的同时，执行 grid[i][j] = '0'，即将岛屿所有节点删除，以免之后重复搜索相同岛屿。
     *
     * @param grid  网格数组
     * @param r 行下标
     * @param c 列下标
     */
    private static void dfs(char[][] grid, int r, int c) {
        // 递归出口，返回上层
        // 这五个条件缺一不可。漏掉任何一个都会导致数组越界或无限递归
        if (r < 0 || c < 0 || r >= grid.length || c >= grid[0].length || grid[r][c] != '1') {
            return;
        }
        // 将可达的1置为 .
        grid[r][c] = '.';
        // 上下左右位置继续递归
        dfs(grid, r - 1, c);
        dfs(grid, r + 1, c);
        dfs(grid, r, c - 1);
        dfs(grid, r , c + 1);
    }

    public static void main(String[] args) {
        char[][] grid = {
                {'1','1','0','0','0'},
                {'1','1','0','0','0'},
                {'0','0','1','0','0'},
                {'0','0','0','1','1'}
        };
        System.out.println(numIslands(grid));
    }
}
