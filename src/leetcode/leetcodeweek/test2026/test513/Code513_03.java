package leetcode.leetcodeweek.test2026.test513;

import java.util.Arrays;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2026/8/2 11:12
 * https://leetcode.cn/contest/weekly-contest-513/problems/count-of-unfinished-tasks-after-each-shift/
 */
public class Code513_03 {
    public static int[] countTasks(int[] tasks, int[] shifts) {
        int n = tasks.length, m = shifts.length;
        long[] s = new long[n];
        s[0] = tasks[0];
        for (int i = 1; i < n; i++) {
            s[i] = s[i - 1] + tasks[i];
        }
        long t = 0;
        for (int i = 0; i < m; i++) {
            t += shifts[i];
            if (t >= s[n - 1]) {
                t = 0;
                shifts[i] = 0;
            } else {
                int j = Arrays.binarySearch(s, t + 1);
                if (j < 0) {
                    j = ~j;
                }
                shifts[i] = n - j;
            }
        }
        return shifts;
    }

    public static void main(String[] args) {
        int[] a1 = {4,2};
        int[] a2 = {3,6,1};
        System.out.println(Arrays.toString(countTasks(a1, a2)));
    }
}
