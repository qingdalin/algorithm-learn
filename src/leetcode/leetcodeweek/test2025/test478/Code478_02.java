package leetcode.leetcodeweek.test2025.test478;

import java.util.HashSet;
import java.util.Set;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/11/30 9:46
 * https://leetcode.cn/contest/weekly-contest-478/problems/maximum-substrings-with-distinct-start/
 */
public class Code478_02 {
    public static int maxDistinct(String str) {
        Set<Character> vis = new HashSet<>();
        char[] s = str.toCharArray();
        int cnt = 0;
        for (char c : s) {
            if (vis.add(c)) {
                cnt++;
            }
        }
        return cnt;
    }

    public static void main(String[] args) {
        String s = "abab";
        System.out.println(maxDistinct(s));
    }
}
