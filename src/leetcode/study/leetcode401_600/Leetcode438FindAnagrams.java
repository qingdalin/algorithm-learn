package leetcode.study.leetcode401_600;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/8/16 10:02
 * https://leetcode.cn/problems/find-all-anagrams-in-a-string/
 */
public class Leetcode438FindAnagrams {
    public static int n, m;
    public static char[] s, p;
    public static List<Integer> findAnagrams(String str, String ptr) {
        s = str.toCharArray();
        p = ptr.toCharArray();
        n = s.length;
        m = p.length;
        List<Integer> ans = new ArrayList<>();
        int[] cntp = new int[26];
        int[] cnts = new int[26];
        for (int j = 0; j < m; j++) {
            cntp[p[j] - 'a']++;
        }
        for (int r = 0; r < n; r++) {
            cnts[s[r] - 'a']++;
            int l = r - m + 1;
            if (l < 0) {
                continue;
            }
            boolean flag = true;
            for (int i = 0; i < 26; i++) {
                if (cntp[i] != cnts[i]) {
                    flag = false;
                    break;
                }
            }
            if (flag) {
                ans.add(l);
            }
            cnts[s[l] - 'a']--;
        }
        return ans;
    }

    public static List<Integer> findAnagrams1(String str, String ptr) {
        s = str.toCharArray();
        p = ptr.toCharArray();
        n = s.length;
        m = p.length;
        List<Integer> ans = new ArrayList<>();
        int[][] dp = prepare();
        int[] cnt = new int[26];
        for (int j = 0; j < m; j++) {
            cnt[p[j] - 'a']++;
        }
        for (int l = 0, r = m - 1; r < n; l++, r++) {
            boolean flag = true;
            for (int i = 0; i < 26; i++) {
                if (l > 0) {
                    if (dp[r][i] - dp[l - 1][i] != cnt[i]) {
                        flag = false;
                        break;
                    }
                } else {
                    if (dp[r][i] != cnt[i]) {
                        flag = false;
                        break;
                    }
                }
            }
            if (flag) {
                ans.add(l);
            }
        }
        return ans;
    }

    private static int[][] prepare() {
        int[][] dp = new int[n][26];
        for (int i = 0; i < n; i++) {
            dp[i][s[i] - 'a'] = 1;
        }
        for (int i = 1; i < n; i++) {
            dp[i][s[i] - 'a'] = dp[i - 1][s[i] - 'a'] + 1;
            for (int j = 0; j < 26; j++) {
                dp[i][j] = dp[i - 1][j];
            }
            dp[i][s[i] - 'a']++;
        }
        return dp;
    }

    public static void main(String[] args) {
//        String s1 = "abab";
//        String s2 = "ab";
        String s1 = "cbaebabacd";
        String s2 = "abc";
        System.out.println(findAnagrams(s1, s2));
    }
}
