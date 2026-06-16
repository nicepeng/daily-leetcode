package cn.nicepeng.hot100.hash;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * GroupAnagrams
 *  49. 字母异位词分组
 *  给你一个字符串数组，请你将 字母异位词 组合在一起。可以按任意顺序返回结果列表。
 *  字母异位词是通过重新排列不同单词或短语的字母而形成的单词或短语，并使用所有原字母一次。
 * <p>
 * 输入: strs = ["eat", "tea", "tan", "ate", "nat", "bat"]
 * <p>
 * 输出: [["bat"],["nat","tan"],["ate","eat","tea"]]
 * <p>
 * 解释：
 * 在 strs 中没有字符串可以通过重新排列来形成 "bat"。
 * 字符串 "nat" 和 "tan" 是字母异位词，因为它们可以重新排列以形成彼此。
 * 字符串 "ate" ，"eat" 和 "tea" 是字母异位词，因为它们可以重新排列以形成彼此。
 * @author nicepeng
 * @since 2026/6/16 21:27
 */
public class GroupAnagrams {

    public static void main(String[] args) {
        String [] strs = new String[]{"eat", "tea", "tan", "ate", "nat", "bat"};
        System.out.println(groupAnagrams(strs));
    }

    /**
     * 思路一： 排序 + 哈希表
     * 字母异位词有一个共同的特性：将它们按字母排序后得到的字符串是相同的。例如 "eat"、"tea"、"ate" 排序后都是 "aet"。
     * 因此，可以用这个排序后的字符串作为哈希表的键，将所有异位词归到同一个列表中。
     * 时间复杂度：O(nklogk)，其中 n 是 strs 的长度，k 是 strs 中字符串的平均长度。
     * 空间复杂度：O(nk)，其中 n 是 strs 的长度，k 是 strs 中字符串的平均长度，哈希表存储所有字符串。
     * @param strs  字符串数组
     * @return      分组后的字符串列表
     */
    public static List<List<String>> groupAnagramsSort(String[] strs) {
        // 键为排序后的字符串，值为该异位词组的列表
        Map<String, List<String>> strMap = new HashMap<>();

        for (String str : strs) {
            // 将字符串转为字符数组并排序
            char[] chs = str.toCharArray();
            Arrays.sort(chs);
            // 排序后的字符数组作为键
            String mapOfKey = String.valueOf(chs);
            // 键不存在，初始化一个新的列表；键存在，获取对应的列表
            List<String> list = strMap.getOrDefault(mapOfKey, new ArrayList<>());
            list.add(str);
            // 将原字符串加入对应的列表
            strMap.put(mapOfKey, list);
        }
        // 返回所有分组（map的值）
        return new ArrayList<>(strMap.values());
    }

    /**
     * 思路二：字符计数 + 哈希表（最优解）
     * 排序法的瓶颈在于 O(k log k) 的排序时间。如果字符串长度 k 很长，可以用更快的特征表示法：将每个字符串的字母出现次数编码为字符串，作为键。
     * 由于题目说明字符串仅包含小写字母，可以用一个大小为 26 的数组统计每个字符的出现次数，然后按照固定格式（如 "#1#0#2..." 或拼接字符和次数）生成键。
     * 这种计数方式只需要 O(k) 的时间。
     * 时间复杂度：O(n * k)，每个字符串只需计数并拼接键（拼接长度为 26 常数，实际操作可视为 O(k) 统计 + O(26) 生成键，接近线性）。
     * 空间复杂度：O(n * k)，哈希表存储所有字符串。
     * @param strs  字符串数组
     * @return      分组后的字符串列表
     */
    public static List<List<String>> groupAnagrams(String[] strs) {
        // 键为由字符计数构造的唯一标识，值为该组的字符串列表
        Map<String, List<String>> strMap = new HashMap<>();
        for (String str : strs) {
            // 统计当前字符串中 26 个小写字母的出现次数  0-a 1-b 2-c ... 25-z
            int[] counts = new int[26];
            for (char c : str.toCharArray()) {
                counts[c - 'a']++;
            }
            // 将计数数组编码为字符串键，格式如 "a3b1c0...z0"
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < 26; i++) {
                // 拼接字母和对应的次数
                sb.append((char)('a' + i)).append(counts[i]);
            }

            String mapOfKey = sb.toString();
            List<String> list = strMap.getOrDefault(mapOfKey, new ArrayList<>());
            list.add(str);
            strMap.put(mapOfKey, list);
        }
        return new ArrayList<>(strMap.values());
    }
}
