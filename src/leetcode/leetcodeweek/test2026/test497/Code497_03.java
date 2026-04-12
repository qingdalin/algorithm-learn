package leetcode.leetcodeweek.test2026.test497;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2026/4/12 9:46
 * https://leetcode.cn/contest/weekly-contest-497/problems/longest-balanced-substring-after-one-swap/
 */
public class Code497_03 {
    // 100111110
    public static int longestBalanced(String str) {
        char[] s = str.toCharArray();
        int n = s.length;
        int total0 = 0;
        for (char c : s) {
            if (c == '0') {
                total0++;
            }
        }
        int total1 = n - total0;
        Map<Integer, List<Integer>> pos = new HashMap<>();
        pos.computeIfAbsent(0, k -> new ArrayList<>()).add(-1);
        int ans = 0;
        int sum = 0;
        for (int i = 0; i < n; i++) {
            sum += (s[i] - '0') * 2 - 1;
            List<Integer> p = pos.computeIfAbsent(sum, k -> new ArrayList<>());
            p.add(i);
            ans = Math.max(ans, i - p.get(0));
            p = pos.get(sum - 2);
            if (p != null) {
                if ((i - p.get(0) - 2) / 2 < total0) {
                    ans = Math.max(ans, i - p.get(0));
                } else if (p.size() > 1) {
                    ans = Math.max(ans, i - p.get(1));
                }
            }
            p = pos.get(sum + 2);
            if (p != null) {
                if ((i - p.get(0) - 2) / 2 < total1) {
                    ans = Math.max(ans, i - p.get(0));
                } else if (p.size() > 1) {
                    ans = Math.max(ans, i - p.get(1));
                }
            }
        }
        return ans;
    }

    public static int longestBalanced2(String str) {
        char[] s = str.toCharArray();
        int n = s.length;
        int cntMaxOne = 0, cntMaxZero = 0;
        for (int i = 0; i < n; i++) {
            if (s[i] == '0') {
                cntMaxZero++;
            } else {
                cntMaxOne++;
            }
        }
        if (cntMaxOne == 0 || cntMaxZero == 0) {
            return 0;
        }
        int ans = 0;
        int cntOne = 0, cntZero = 0;
        for (int l = 0, r = -1; r < n && l < n; l++) {
            while (r + 1 < n && Math.abs(cntOne - cntZero) <= 2) {
                if (s[r + 1] == '0') {
                    cntZero++;
                } else {
                    cntOne++;
                }
                r++;
            }
            ans = Math.max(ans, cntOne == cntZero ? cntOne * 2 : r - l);
            if (s[l] == '0') {
                cntZero--;
            } else {
                cntOne--;
            }
        }
        return ans;
    }

    public static void main(String[] args) {
//        String s = "10001010000010010";
//        String s = "01";
//        String s = "100001";
        String s = "0010";
        System.out.println(longestBalanced(s));
    }

    public static int longestBalanced1(String s) {
        int cntOne = 0, cntZero = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '0') {
                cntZero++;
            } else {
                cntOne++;
            }
        }
        int ans = Math.min(cntZero, cntOne) * 2;
        return ans;
    }
}
