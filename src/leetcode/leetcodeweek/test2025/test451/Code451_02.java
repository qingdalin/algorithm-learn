package leetcode.leetcodeweek.test2025.test451;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/5/25 8:32
 * https://leetcode.cn/contest/weekly-contest-451/problems/resulting-string-after-adjacent-removals/
 */
public class Code451_02 {
    public static String resultingString(String str) {
        char[] s = str.toCharArray();
        int n = s.length;
        Deque<Character> deque = new ArrayDeque<>();
        for (int i = 0; i < n; i++) {
            if (!deque.isEmpty() && check(deque.getLast(), s[i])) {
                deque.pollLast();
            } else {
                deque.addLast(s[i]);
            }
        }
        StringBuilder ans = new StringBuilder();
        while (!deque.isEmpty()) {
            ans.append(deque.pollFirst());
        }
        return ans.toString();
    }

    private static boolean check(char pre, char cur) {
        if (cur == 'a') {
            return pre == 'z' || pre == 'b';
        }
        if (cur == 'z') {
            return pre == 'a' || pre == 'y';
        }
        return Math.abs(cur - pre) == 1;
    }

    public static void main(String[] args) {
        String s = "abc";
        System.out.println(resultingString(s));
    }
}
