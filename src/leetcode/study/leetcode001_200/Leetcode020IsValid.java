package leetcode.study.leetcode001_200;

import java.util.Stack;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/4/14 19:14
 * https://leetcode.cn/problems/valid-parentheses/description/
 */
public class Leetcode020IsValid {
    public static Stack<Character> stack = new Stack<>();
    public static boolean isValid(String str) {
        char[] s = str.toCharArray();
        int n = s.length;
        if (n == 1) {
            return false;
        }
        stack.clear();
        for (int i = 0; i < n; i++) {
            if (s[i] == '(' || s[i] == '[' || s[i] == '{') {
                stack.push(s[i]);
            } else if (s[i] == ')') {
                if (stack.isEmpty() || stack.pop() != '(') {
                    return false;
                }
            } else if (s[i] == ']') {
                if (stack.isEmpty() || stack.pop() != '[') {
                    return false;
                }
            } else {
                if (stack.isEmpty() || stack.pop() != '{') {
                    return false;
                }
            }
        }
        return stack.isEmpty();
    }

    public static void main(String[] args) {
        String s = "([])";
        System.out.println(isValid(s));
    }
}
