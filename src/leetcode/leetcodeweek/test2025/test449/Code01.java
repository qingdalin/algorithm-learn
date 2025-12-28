package leetcode.leetcodeweek.test2025.test449;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/5/11 9:50
 * https://leetcode.cn/contest/weekly-contest-449/problems/minimum-deletions-for-at-most-k-distinct-characters/description/
 */
public class Code01 {
    public static int minDeletion(String str, int k) {
        char[] s = str.toCharArray();
        int kind = 0;
        int n = s.length;
        int[] cnts = new int[26];
        List<Character> list = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            cnts[s[i] - 'a']++;
            if (cnts[s[i] - 'a'] == 1) {
                kind++;
            }
        }
        if (kind <= k) {
            return 0;
        }
        int del = kind - k;
        Arrays.sort(cnts);
        int ans = 0;
        for (int i = 0; i < 26 && del > 0; i++) {
            if (cnts[i] != 0) {
                del--;
                ans += cnts[i];
            }
        }
        return ans;
    }

    public static void main(String[] args) {
        String s = "yyyzz";
        int k = 1;
        System.out.println(minDeletion(s, k));
    }
}
