package leetcode.leetcodeweek.test2026.test503;

import java.util.HashSet;
import java.util.Set;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2026/5/24 8:55
 * https://leetcode.cn/contest/weekly-contest-503/problems/password-strength/description/
 */
public class Code503_02 {
    public static int passwordStrength(String password) {
        char[] s = password.toCharArray();
        int n = s.length;
        int ans = 0;
        boolean[] vis = new boolean[128];
        for (int i = 0; i < n; i++) {
            if (vis[s[i]]) {
                continue;
            }
            vis[s[i]] = true;
            if (Character.isLowerCase(s[i])) {
                ans += 1;
            } else if (Character.isUpperCase(s[i])) {
                ans += 2;
            } else if (Character.isDigit(s[i])) {
                ans += 3;
            } else {
                ans += 5;
            }
        }
        return ans;
    }

    public static int passwordStrength1(String password) {
        char[] s = password.toCharArray();
        int n = s.length;
        int ans = 0;
        Set<Character> hasLowerCase = new HashSet<>();
        Set<Character> hasUpperCase = new HashSet<>();
        Set<Character> hasDigit = new HashSet<>();
        Set<Character> hasSymbol = new HashSet<>();
        for (int i = 0; i < n; i++) {
            if (!hasLowerCase.contains(s[i]) && Character.isLowerCase(s[i])) {
                ans += 1;
                hasLowerCase.add(s[i]);
            } else if (!hasUpperCase.contains(s[i]) && Character.isUpperCase(s[i])) {
                ans += 2;
                hasUpperCase.add(s[i]);
            } else if (!hasDigit.contains(s[i]) && Character.isDigit(s[i])) {
                ans += 3;
                hasDigit.add(s[i]);
            } else if (!hasSymbol.contains(s[i]) && "!@#$".indexOf(s[i]) != -1) {
                ans += 5;
                hasSymbol.add(s[i]);
            }

        }
        return ans;
    }

    public static void main(String[] args) {
        String s = "bbB11#";
        System.out.println(passwordStrength(s));
    }
}
