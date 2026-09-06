package leetcode.leetcodeweek.test2026.test518;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2026/9/6 10:19
 * https://leetcode.cn/contest/weekly-contest-518/problems/count-rotations-with-exactly-k-equal-adjacent-pairs/description/
 */
public class Code518_01 {
    public static int countRotations(String str, int k) {
        char[] s = str.toCharArray();
        int n = s.length;
        int ans = 0;
        int same = 0;
        for (int i = 0; i < n * 2 - 2; i++) {
            if (s[i % n] == s[(i + 1) % n]) {
                same++;
            }
            int left = i - n + 2;
            if (left < 0) {
                continue;
            }
            if (same == k) {
                ans++;
            }
            if (s[left] == s[(left + 1) % n]) {
                same--;
            }
        }
        return ans;
    }

    public static int countRotations1(String str, int k) {
        char[] s = str.toCharArray();
        int n = s.length;
        int ans = 0;
        for (int i = 0; i < n; i++) {
            int cnt = 0;
            for (int j = i, idx = 0; idx < n - 1; idx++, j++) {
                if (s[j % n] == s[(j + 1) % n]) {
                    cnt++;
                }
            }
            if (cnt == k) {
                ans++;
            }
        }
        return ans;
    }

    public static void main(String[] args) {
        String s = "abca";
        int k = 0;
        System.out.println(countRotations(s, k));
    }
}
