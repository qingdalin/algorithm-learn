package leetcode.leetcodeweek.test2026.test515;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2026/8/16 9:50
 * https://leetcode.cn/contest/weekly-contest-515/problems/maximum-gap-between-stations/
 */
public class Code515_03 {
    public static int maximumGap(String skill, String station) {
        char[] s = skill.toCharArray();
        char[] t = station.toCharArray();
        int n = s.length;
        int[] suf = new int[n];
        int j = t.length;
        for (int i = n - 1; i > 0; i--) {
            j--;
            while (t[j] != s[i]) {
                j--;
            }
            suf[i] = j;
        }
        int pre = -1;
        int ans = 0;
        for (int i = 0; i < n - 1; i++) {
            pre++;
            while (t[pre] != s[i]) {
                pre++;
            }
            ans = Math.max(ans, suf[i + 1] - pre);
        }
        return ans;
    }
}
