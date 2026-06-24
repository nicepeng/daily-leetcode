package cn.nicepeng.hot100.stack;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

/**
 * LC_020_ValidParenthesis     20. 有效的括号
 * 给定一个只包括 '('，')'，'{'，'}'，'['，']' 的字符串 s ，判断字符串是否有效。
 *
 * @author nicepeng
 * @since 2026/6/24 20:24
 */
public class LC_020_ValidParenthesis {


    /**
     * 思路一：显式栈 + HashMap（推荐 ✅ 最清晰）
     * 遇到左括号 → 压入栈中，等待后续匹配
     * 遇到右括号 → 检查栈顶是否是对应的左括号
     *      匹配成功 → 弹出栈顶，继续
     *      匹配失败或栈为空 → 直接返回 false
     * 遍历结束后，栈必须为空才有效（防止多余左括号）
     * ⏱ O(n) | 💾 O(n)
     * 🔑 为什么用 ArrayDeque 而不是 Stack？
     * Java 的 Stack 类继承自 Vector，方法是同步的，性能差；ArrayDeque 是官方推荐的栈实现，更快且无线程安全开销。
     * @param s
     * @return
     */
    public static boolean isValid(String s) {
        // 快速剪枝
        if (s.length() % 2 != 0) return false;

        Map<Character, Character> map = new HashMap<>();
        // key：右括号    value：左括号
        map.put(')', '(');
        map.put(']', '[');
        map.put('}', '{');

        Deque<Character> stack = new ArrayDeque<>();

        for (char c : s.toCharArray()) {
            // 【情况1】当前字符是右括号
            if (map.containsKey(c)) {
                char top = stack.isEmpty() ? '#' : stack.pop();
                // 栈顶元素必须与当前右括号对应的左括号一致，不一致说明括号不匹配，直接返回 false
                if (top != map.get(c)) {
                    return false;
                }
            } else {
                // 【情况2】当前字符是左括号，直接压栈等待匹配
                stack.push(c);
            }
        }
        // 栈为空说明所有左括号都被正确匹配，栈非空说明有多余的左括号未被闭合
        return stack.isEmpty();
    }

    public static void main(String[] args) {
        System.out.println(isValid("()[]{}"));
        System.out.println(isValid("([])"));
        System.out.println(isValid("([)]"));
    }
}
