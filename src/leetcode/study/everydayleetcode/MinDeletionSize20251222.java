package leetcode.study.everydayleetcode;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/12/22 19:18
 * https://leetcode.cn/problems/delete-columns-to-make-sorted-iii/?envType=daily-question&envId=2025-12-22
 */
public class MinDeletionSize20251222 {
    public static int minDeletionSize(String[] strs) {
        int ans = 0;
        for (String str : strs) {
            ans = Math.max(ans, getLengthOfLIS(str.toCharArray()));
        }
        return ans;
    }
    
    public static int getLengthOfLIS(char[] s) {
        int n = s.length;
        int[] dp = new int[n];
        int ans = 0;
        for (int i = 0; i < n; i++) {
            dp[i] = 1;
            for (int j = 0; j < i; j++) {
                if (s[j] < s[i]) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
            ans = Math.max(ans, dp[i]);
        }
        return n - ans;
    }


}
