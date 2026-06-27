package cn.nicepeng.hot100.array;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * LC_056_MergeIntervals    56. 合并区间
 * 以数组 intervals 表示若干个区间的集合，其中单个区间为 intervals[i] = [starti, endi] 。请你合并所有重叠的区间，并返回 一个不重叠的区间数组，该数组需恰好覆盖输入中的所有区间 。
 *
 * @author nicepeng
 * @since 2026/6/26 0:06
 */
public class LC_056_MergeIntervals {

    /**
     * 思路：排序+贪心
     * @param intervals
     * @return
     */
    public static int[][] merge(int[][] intervals) {
        if (intervals == null || intervals.length <= 1) {
            return intervals;
        }
        // 第一步：按区间的左端点（start）从小到大排序
        // 使用Integer.compare避免数值溢出，比直接相减更稳妥
        Arrays.sort(intervals, (o1, o2) -> Integer.compare(o1[0], o2[0]));

        // 用List存储合并后的结果，方便动态添加
        List<int[]> res = new ArrayList<>();
        // 先把第一个区间加入结果集，作为初始合并基准
        res.add(intervals[0]);

        // 第二步：从第二个区间开始遍历，逐个判断合并
        for (int i = 1; i < intervals.length; i++) {
            int[] curr = intervals[i];
            // 结果集中最后一个区间（当前已合并的最右区间）
            int[] last = res.get(res.size() - 1);

            // 判断是否有重叠：当前区间的左端点 <= 最后一个区间的右端点
            if (curr[0] <= last[1]) {
                // 重叠：合并，更新最后一个区间的右端点为两者的最大值    [1,3]和[2,6] -> [1,6]
                // 注意：必须取max，防止当前区间完全被包含的情况（如[1,10]和[2,3]）
                last[1] = Math.max(last[1], curr[1]);
            } else {
                // 不重叠：直接加入结果集，成为新的合并基准
                res.add(curr);
            }
        }

        // 第三步：将List转为int[][]二维数组返回， new int[0][] 是Java中toArray的标准写法，会自动适配长度
        return res.toArray(new int[0][]);

    }

    public static void main(String[] args) {
        int[][] intervals = {{1,3}, {2,6}, {8,10}, {15,18}};
        System.out.println(Arrays.deepToString(merge(intervals)));
    }
}
