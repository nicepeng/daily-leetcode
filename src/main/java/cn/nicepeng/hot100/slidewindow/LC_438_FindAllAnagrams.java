package cn.nicepeng.hot100.slidewindow;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * LC_438_FindAllAnagrams   438. 找到字符串中所有字母异位词
 * 给定两个字符串 s 和 p，找到 s 中所有 p 的 异位词 的子串，返回这些子串的起始索引。不考虑答案输出的顺序。
 * PS：两个字符串互为字母异位词 ⟺ 它们包含的字符种类和每种字符的数量完全相同。顺序无关，只看频率。
 * 输入: s = "cbaebabacd", p = "abc"
 * 输出: [0,6]
 * 解释:
 * 起始索引等于 0 的子串是 "cba", 它是 "abc" 的异位词。
 * 起始索引等于 6 的子串是 "bac", 它是 "abc" 的异位词。
 *
 * @author nicepeng
 * @since 2026/6/19 17:29
 */
public class LC_438_FindAllAnagrams {


    /**
     * 思路一：定长滑窗
     * 定长窗口的移动规则极其简单：右指针走一步，左指针必走一步，窗口大小恒定，无需 while 循环收缩。
     * 对于本题，每次窗口移动后都完整比较两个长度为 26 的频率数组，如果窗口数组跟目标数组的值完全一样，说明存在异位词，找到一个解
     * @param s 源串
     * @param p 目标串
     * @return  起始索引集合
     */
    public static List<Integer> findAnagrams(String s, String p) {
        List<Integer> result = new ArrayList<>();
        int sLen = s.length();
        int pLen = p.length();

        // 🔑 边界检查：s 比 p 短，不可能有异位词
        if (sLen < pLen) {
            return result;
        }

        // ========== 1. 初始化两个频率数组 ==========
        // target: p 的字符频率（整个过程中不变）；window: s 中当前窗口的字符频率（随滑动动态更新）
        int[] target = new int[26];
        int[] window = new int[26];
        // 同时构建目标频率表和第一个窗口 [0, pLen-1] 的频率表
        for (int i = 0; i < pLen; i++) {
            target[p.charAt(i) - 'a']++;
            window[s.charAt(i) - 'a']++;
        }
        // ========== 2. 检查第一个窗口 ==========
        // ⚠️ 定长滑窗的经典陷阱：循环从 pLen 开始，第一个窗口 [0, pLen-1] 必须在循环外单独检查
        if (Arrays.equals(window, target)) {
            result.add(0);
        }

        // ========== 3. 定长窗口滑动 ==========
        // right 指向即将进入窗口的字符
        // left = right - pLen 即为即将离开窗口的字符
        // 每步操作：加入 s[right]，移除 s[right-pLen]，窗口大小始终保持 pLen
        for (int right = pLen; right < sLen; right++) {
            // 新字符进入窗口
            window[s.charAt(right) - 'a']++;
            // 旧字符离开窗口
            window[s.charAt(right - pLen) - 'a']--;

            // 逐位比较两个频率数组，当且仅当所有26个位置的值都相等时返回 true
            if (Arrays.equals(window, target)) {
                // 当前窗口左端点 = right - pLen + 1
                result.add(right - pLen + 1);
            }
        }
        return result;
    }

    /**
     * 思路二：优化思路一，定长滑窗 + 差异计数器
     * 维护一个变量 diffCount，记录当前窗口频率表与目标频率表之间有多少个字符的出现频率不匹配。
     * 窗口每移动一步，只有"进入窗口的字符"和"离开窗口的字符"会影响 diffCount，每次更新 O(1)
     * 当 diffCount == 0 时，窗口就是异位词。
     * @param s
     * @param p
     * @return
     */
    public static List<Integer> findAnagrams_plus(String s, String p) {
        List<Integer> result = new ArrayList<>();
        int sLen = s.length();
        int pLen = p.length();
        // 🔑 边界检查：s 比 p 短，不可能有异位词
        if (sLen < pLen) {
            return result;
        }
        // ========== 1. 构建目标频率表 & 初始窗口频率表 ==========
        int[] target = new int[26];     // p 的字符频率
        int[] window = new int[26];     // 当前窗口的字符频率

        // 初始化：构建目标频率 + 第一个窗口
        for (int i = 0; i < pLen; i++) {
            target[p.charAt(i) - 'a']++;
            window[s.charAt(i) - 'a']++;
        }

        // ========== 2. 计算初始 diffCount ==========
        // diffCount = 频率表中不匹配的字符种类数
        // 注意：是"种类数"不是"总差值"！
        // 例如 target[a]=2, window[a]=0 → diffCount += 1（不是+=2）
        int diffCount = 0;
        for (int i = 0; i < 26; i++) {
            if (target[i] != window[i]) {
                diffCount += 1;
            }
        }
        // 初始窗口 [0, pLen-1] 已经就位，先检查
        if (diffCount == 0) {
            result.add(0);
        }

        // ========== 3. 定长窗口滑动 ==========
        // right 从 pLen 开始，每次右移一位，left = right - pLen 自动跟着移
        for (int right = pLen; right < sLen; right++) {
            char inCh = s.charAt(right);    // 进入窗口的字符
            char outCh = s.charAt(right - pLen);    // 离开窗口的字符

            /*
                inCh == outCh 时不能跳过结果记录：窗口内容虽然没变，但如果上一轮 diffCount==0 已经记录过了，这一轮也会再次满足。
                不过由于窗口左端点不同，即使内容相同也是不同的起始索引，所以 inCh == outCh 时仍需判断并可能添加新索引。
            */
            if (inCh != outCh) {
                // --- 处理进入窗口的字符 inCh ---
                int inIdx = inCh - 'a';
                if (window[inIdx] == target[inIdx]) {
                    diffCount++;    // 本来匹配，字符inCh加入window后破坏了匹配，diffCount+1
                }
                window[inIdx]++;
                if (window[inIdx] == target[inIdx]) {
                    diffCount--;    // 加入window后刚好匹配，则 diffCount-1
                }

                // --- 处理离开窗口的字符 outChar ---
                int outIdx = outCh - 'a';
                if (window[outIdx] == target[outIdx]) {
                    diffCount++; // 原来匹配，移除字符outCh后不匹配
                }
                window[outIdx]--;
                if (window[outIdx] == target[outIdx]) {
                    diffCount--; // 移除后刚好匹配了
                }
            }

            // 🔑 diffCount == 0 表示所有26个字符频率都匹配
            if (diffCount == 0) {
                result.add(right - pLen + 1); // 窗口左端点 = right - pLen + 1
            }
        }
        return result;
    }

    public static void main(String[] args) {
        System.out.println(findAnagrams_plus("cbaebabacd", "abc"));
        System.out.println(findAnagrams_plus("abab", "ab"));
    }
}
