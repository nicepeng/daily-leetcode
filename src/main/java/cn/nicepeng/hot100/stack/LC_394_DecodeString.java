package cn.nicepeng.hot100.stack;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * LC_394_DecodeString  394. 字符串解码
 * 给定一个经过编码的字符串，返回它解码后的字符串。
 *
 * @author nicepeng
 * @since 2026/6/24 20:56
 */
public class LC_394_DecodeString {

    public static String decodeString(String s) {
        Deque<Integer> numStack = new ArrayDeque<>();   // 存储重复次数 k
        Deque<String> strStack = new ArrayDeque<>();    // 存储 '[' 前的字符串前缀

        int curNum = 0;         // 当前正在解析的数字
        StringBuilder curStr = new StringBuilder(); // 当前层级正在构建的字符串

        for (char c : s.toCharArray()) {
            if (Character.isDigit(c)) {
                curNum = curNum * 10 + (c - '0');   // 处理多位数
            } else if (c == '[') {
                // 【左括号】进入新的嵌套层级，将当前层级的数字和字符串压栈保存（相当于保存现场）
                numStack.push(curNum);
                strStack.push(curStr.toString());
                // 重置当前状态，准备处理内层
                curNum = 0;
                curStr = new StringBuilder();
            } else if (c == ']') {
                // 【右括号】当前层级解码完成，与外层合并
                int repeatTimes = numStack.pop();   // 弹出外层的重复次数
                String prevStr = strStack.pop();    // 弹出外层的字符串前缀
                // 合并：外层前缀 + 当前层字符串 × 重复次数
                StringBuilder merged = new StringBuilder(prevStr);
                for (int i = 0; i < repeatTimes; i++) {
                    merged.append(curStr);
                }
                curStr = merged;
            } else {
                // 【字母】直接追加到当前层级的字符串中
                curStr.append(c);
            }
        }

        return curStr.toString();
    }

    public static void main(String[] args) {
        String s1 = "abc3[cd]xyz";
        String s2 = "2[abc]3[cd]ef";
        String s3 = "3[a2[c]]";
        System.out.println(decodeString(s2));
    }

}
