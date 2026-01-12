package leetcode.study.everydayleetcode.every2026;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2026/1/12 19:20
 * https://leetcode.cn/problems/minimum-time-visiting-all-points/?envType=daily-question&envId=2026-01-12
 */
public class Code_20260112MinTimeToVisitAllPoints {
    public int minTimeToVisitAllPoints(int[][] points) {
        int n = points.length;
        if (n == 1) {
            return 0;
        }
        int cnt = 0;
        int[] pre, cur;
        for (int i = 1, x1, x2, y1, y2, diffX, diffY; i < n; i++) {
            pre = points[i - 1];
            cur = points[i];
            x1 = pre[0];
            y1 = pre[1];
            x2 = cur[0];
            y2 = cur[1];
            // 加上差值的最大值
            diffX = Math.abs(x1 - x2);
            diffY = Math.abs(y1 - y2);
            cnt += Math.max(diffX, diffY);
        }
        return cnt;
    }

    public int minTimeToVisitAllPoints1(int[][] points) {
        int n = points.length;
        if (n == 1) {
            return 0;
        }
        int cnt = 0;
        int[] pre, cur;
        for (int i = 1, x1, x2, y1, y2, diffX, diffY; i < n; i++) {
            pre = points[i - 1];
            cur = points[i];
            x1 = pre[0];
            y1 = pre[1];
            x2 = cur[0];
            y2 = cur[1];
            // 加上差值的最大值
            diffX = Math.abs(x1 - x2);
            diffY = Math.abs(y1 - y2);
            if (diffX <= diffY) {
                cnt += diffX;
                cnt += diffY - diffX;
            } else {
                cnt += diffY;
                cnt += diffX - diffY;
            }
        }
        return cnt;
    }
}
