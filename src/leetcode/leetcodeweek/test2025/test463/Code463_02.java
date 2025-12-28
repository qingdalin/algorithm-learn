package leetcode.leetcodeweek.test2025.test463;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/8/17 9:08
 * https://leetcode.cn/contest/weekly-contest-463/problems/xor-after-range-multiplication-queries-i/
 */
public class Code463_02 {
    public static int MOD = 1000000007;
    public static int xorAfterQueries(int[] nums, int[][] queries) {
        int n = nums.length;
        int m = queries.length;
        for (int i = 0, l, r, v, k; i < m; i++) {
            l = queries[i][0];
            r = queries[i][1];
            k = queries[i][2];
            v = queries[i][3];
            for (int j = l; j <= r; j += k) {
                nums[j] = (int) ((long) nums[j] * v % MOD);
            }
        }
        int ans = 0;
        for (int num : nums) {
            ans ^= num;
        }
        return ans;
    }
}
