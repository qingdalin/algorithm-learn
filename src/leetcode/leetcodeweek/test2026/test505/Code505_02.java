package leetcode.leetcodeweek.test2026.test505;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2026/6/7 10:23
 * https://leetcode.cn/contest/weekly-contest-505/problems/valid-binary-strings-with-cost-limit/
 */
public class Code505_02 {
    public static List<String> generateValidStrings(int n, int k) {
        List<String> ans = new ArrayList<>();
        f("", 0, 0, n, k, ans);
        return ans;
    }

    private static void f(String s, int i, int cost, int n, int k, List<String> ans) {
        if (cost > k) {
            return;
        }
        if (s.length() >= n) {
            ans.add(s);
            return;
        }
        f(s + "0", i + 1, cost, n, k, ans);
        if (s.equals("") || s.charAt(s.length() - 1) == '0') {
            f(s + "1", i + 1, cost + i, n, k, ans);
        }
    }

    public static void main(String[] args) {
        int n = 3 , k = 1;
        System.out.println(generateValidStrings(n, k));
    }
}
