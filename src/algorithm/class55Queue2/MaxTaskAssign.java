package algorithm.class55Queue2;

import java.util.Arrays;

/**
 * @author 汪大鹏
 * @version 1.0
 * @date 2024-03-16 12:16
 * https://leetcode.cn/problems/maximum-number-of-tasks-you-can-assign/
 * 单调队列、二分、贪心
 */
public class MaxTaskAssign {
    public static int h, t;
    public static int[] ts;
    public static int[] ws;
    public static int[] deque = new int[50001];
    public int maxTaskAssign(int[] tasks, int[] workers, int pills, int strength) {
        ws = workers;
        ts = tasks;
        Arrays.sort(ws);
        Arrays.sort(ts);
        int wsize = ws.length;
        int tsize = ts.length;
        int ans = 0;
        for (int l = 0, r = Math.min(wsize, tsize), m; l <= r;) {
            m = (l + r) / 2;
            if (f(0, m - 1, wsize - m, wsize - 1, strength, pills)) {
                ans = m;
                l = m + 1;
            } else {
                r = m - 1;
            }
        }
        return ans;
    }
    // tl - tr 任务需要能量最小的几个任务
    // wl - wr 工人力量最大的几个
    public static boolean f(int tl, int tr, int wl, int wr, int strength, int pills) {
        int cnt = 0;
        h = t = 0;
        for (int i = wl, j = tl; i <= wr; i++) {
            for (; j <= tr && ts[j] <= ws[i]; j++) {
                // 1.工人不吃药，能解锁的任务
                deque[t++] = j;
            }
            if (h < t && ts[deque[h]] <= ws[i]) {
                // 2.工人不吃药完成最低所需能量任务
                h++;
            } else {
                // 3.工人吃药，解锁的任务
                for (;j <= tr && ts[j] <= ws[i] + strength; j++) {
                    deque[t++] = j;
                }
                if (h < t) {
                    // 4.工人吃药后完成队列最大能量的任务
                    cnt++;
                    t--;
                } else {
                    // 工人吃药队列没任务，证明这个工人一定没任务，失败返回
                    return false;
                }
            }
        }
        return cnt <= pills;
    }
}
