package cn.nicepeng.hot100.slidewindow;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * LC_003_LengthOfLongestSubstring
 * 给定一个字符串 s ，请你找出其中不含有重复字符的 最长 子串 的长度。
 * 输入: s = "abcabcbb"
 * 输出: 3
 * 解释: 因为无重复字符的最长子串是 "abc"，所以其长度为 3。注意 "bca" 和 "cab" 也是正确答案。
 *
 * @author nicepeng
 * @since 2026/6/19 15:06
 */
public class LC_003_LengthOfLongestSubstring {

    /**
     * 思路一：滑动窗口 + 哈希表
     * 维护一个窗口 [left, right]，保证窗口内始终无重复，right 每次走一步，
     * 当 right 指向的字符 c 在窗口内已存在时，left 应该跳到 "窗口内 c 的上一次出现下标的下一位"。用哈希表记录每个字符的最新索引，即可 O(1) 完成跳转。
     *  时间：O(n)，right 遍历 n 次，left 总共最多移动 n 次，均摊 O(1)/步
     *  空间：O(min(n, m))，m 为字符集大小，ASCII 最多 128
     * @param s 字符串
     * @return 无重复字符最长子串的长度
     */
    public static int lengthOfLongestSubstring(String s) {
        // key=字符, value=该字符最近一次出现的索引。作用：当发现重复时，O(1) 定位 left 应该跳到的位置
        Map<Character, Integer> charIndexMap = new HashMap<>();
        // left：窗口左边界（包含）
        int left = 0, maxLen = 0;

        // right 作为窗口右边界，逐步向右扩展
        for (int right = 0; right < s.length(); right++) {
            char c = s.charAt(right);
            // 当前遍历字符 c 已经在窗口内[left, right]出现过，则将 left 跳到上次出现位置的下一位
            if (charIndexMap.containsKey(c)) {
                // 字符 c 上一次出现的下标位置
                int prevIndex = charIndexMap.get(c);
                // ⚠️ 必须取 max！防止 left 回退
                // 例如 "abba"：right指向第二个 'a' 时，prevIndex=0，但此时 left 已经因为第二个 'b' 重复而跳到了下标 2，如果不取 max，left 会错误地回退到 1
                left = Math.max(left, prevIndex + 1);
            }
            // 更新字符 c 的最新索引
            charIndexMap.put(c, right);
            // 当前窗口 [left, right] 一定无重复，更新全局最大值
            maxLen = Math.max(maxLen, right - left + 1);
        }
        return maxLen;
    }


    public static void main(String[] args) {
        String s1 = "abcabcbb";
        System.out.println(lengthOfLongestSubstring(s1));
    }
}
