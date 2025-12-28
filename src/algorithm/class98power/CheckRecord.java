package algorithm.class98power;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2024/8/4 10:14
 * 可以用字符串表示一个学生的出勤记录，其中的每个字符用来标记当天的出勤情况（缺勤、迟到、到场）。记录中只含下面三种字符：
 * 'A'：Absent，缺勤
 * 'L'：Late，迟到
 * 'P'：Present，到场
 * 如果学生能够 同时 满足下面两个条件，则可以获得出勤奖励：
 *
 * 按 总出勤 计，学生缺勤（'A'）严格 少于两天。
 * 学生 不会 存在 连续 3 天或 连续 3 天以上的迟到（'L'）记录。
 * 给你一个整数 n ，表示出勤记录的长度（次数）。请你返回记录长度为 n 时，
 * 可能获得出勤奖励的记录情况 数量 。答案可能很大，所以返回对 109 + 7 取余 的结果。
 * https://leetcode.cn/problems/student-attendance-record-ii/description/
 */
public class CheckRecord {
    // dp[i][a][b]:表示第i天里有a太难缺勤且最后连续b天迟到的合法数量
    // dp[i][0][0] -> dp[i][0] -> 0天缺勤0天迟到
    // dp[i][0][1] -> dp[i][1] -> 0天缺勤1天迟到
    // dp[i][0][2] -> dp[i][2] -> 0天缺勤2天迟到
    // dp[i][1][0] -> dp[i][3] -> 1天缺勤0天迟到
    // dp[i][1][1] -> dp[i][4] -> 1天缺勤1天迟到
    // dp[i][1][2] -> dp[i][5] -> 1天缺勤2天迟到
    // dp[i][0] <- dp[i-1][0,1,2]
    // dp[i][1] <- dp[i-1][0]
    // dp[i][2] <- dp[i-1][1]
    // dp[i][3] <- dp[i-1][0-5]
    // dp[i][4] <- dp[i-1][3]
    // dp[i][5] <- dp[i-1][4]
    public static int mod = 1000000007;
    public int checkRecord(int n) {
        int[][] start = { { 1, 1, 0, 1, 0, 0 } };
        int[][] base = {
            { 1, 1, 0, 1, 0, 0 }, // 0
            { 1, 0, 1, 1, 0, 0 }, // 1
            { 1, 0, 0, 1, 0, 0 }, // 2
            { 0, 0, 0, 1, 1, 0 }, // 3
            { 0, 0, 0, 1, 0, 1 }, // 4
            { 0, 0, 0, 1, 0, 0 }  // 5
        };
        int[][] ans = multiply(start, power(base, n - 1));
        int res = 0;
        for (int a : ans[0]) {
            res = (res + a) % mod;
        }
        return res;
    }
    private static int[][] multiply(int[][] a, int[][] b) {
        int n = a.length;
        int m = b[0].length;
        int k = a[0].length;
        int[][] ans = new int[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                for (int c = 0; c < k; c++) {
                    ans[i][j] = (int) (((long) a[i][c] * b[c][j] + ans[i][j]) % mod);
                }
            }
        }
        return ans;
    }

    public static int[][] power(int[][] base, int p) {
        int n = base.length;
        int[][] ans = new int[n][n];
        for (int i = 0; i < n; i++) {
            // 对角线设置1就相当于ans=1
            ans[i][i] = 1;
        }
        for (; p != 0; p >>= 1) {
            if ((p & 1) != 0) {
                ans = multiply(ans, base);
            }
            base = multiply(base, base);
        }
        return ans;
    }
}
