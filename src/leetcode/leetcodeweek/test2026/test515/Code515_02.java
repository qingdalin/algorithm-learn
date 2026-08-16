package leetcode.leetcodeweek.test2026.test515;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2026/8/16 9:50
 * https://leetcode.cn/contest/weekly-contest-515/problems/minimize-the-maximum-waiting-time-at-synchronized-traffic-lights/description/
 *
 */
public class Code515_02 {
    public static int minPenalty(int period, int[] lights, int[] arrivalTime) {
        int ans = 0;
        int m = arrivalTime.length;
        int maxL = Integer.MIN_VALUE;
        for (int i = 0; i < lights.length; i++) {
            maxL = Math.max(maxL, lights[i]);
        }
        for (int i = 0; i < m; i++) {
            int r = arrivalTime[i] % period;
            if (r >= maxL) {
                ans = Math.max(ans, period - r);
            }
        }
        return ans;
    }

    public static void main(String[] args) {
        int p = 2;
        int[] lights = {1,1,1,1,1};
        int[] arrivalTime = {30};
        System.out.println(minPenalty(p, lights, arrivalTime));
    }
}
