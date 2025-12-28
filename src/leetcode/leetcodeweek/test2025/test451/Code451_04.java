package leetcode.leetcodeweek.test2025.test451;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/5/25 8:32
 * https://leetcode.cn/contest/weekly-contest-451/problems/lexicographically-smallest-string-after-adjacent-removals/
 */
public class Code451_04 {
    public static String lexicographicallySmallestString(String str) {
        char[] s = str.toCharArray();
        int n = s.length;
        Deque<Character> deque = new ArrayDeque<>();
        List<String> list = new ArrayList<>();
        list.add(str);
        for (int i = 0; i < n; i++) {
            // zab
            // bcd
            // azy
            // fcddccec
            if (!deque.isEmpty() && check(deque.getLast(), s[i])) {
                int index = i;
                while (index + 1 < n && s[index] == s[index + 1]) {
                    deque.addLast(s[index]);
                    index++;
                }
                if (index + 1 < n && check(s[index], s[index + 1])) {
                    i = index;
                } else {
                    for (int j = i; j < index; j++) {
                        deque.pollLast();
                    }
                }
                if (i + 1 < n && check(s[i], s[i + 1])) {
                    deque.addLast(s[i]);
                } else {
                    deque.pollLast();
                    StringBuilder curStr = new StringBuilder();
                    for (Character character : deque) {
                        curStr.append(character);
                    }
                    curStr.append(str.substring(i + 1));
                    list.add(curStr.toString());

                }
            } else {
                deque.addLast(s[i]);
            }
        }
        list.sort((a, b) -> a.compareTo(b));
//        StringBuilder ans = new StringBuilder();
//        while (!deque.isEmpty()) {
//            ans.append(deque.pollFirst());
//        }
        return list.get(0);
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
//        String s = "abc";
//        String s = "bcda";
//        String s = "fcddccec";
        String s = "hglkji";
        System.out.println(lexicographicallySmallestString(s));
    }
}
