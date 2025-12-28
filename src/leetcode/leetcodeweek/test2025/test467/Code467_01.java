package leetcode.leetcodeweek.test2025.test467;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/9/14 8:30
 * https://leetcode.cn/contest/weekly-contest-467/problems/earliest-time-to-finish-one-task/
 */
public class Code467_01 {
    public static int earliestTime(int[][] tasks) {
        int n = tasks.length;
        int ans = Integer.MAX_VALUE;
        for (int[] task : tasks) {
            ans = Math.min(ans, task[0] + task[1]);
        }
        return ans;
    }
}
