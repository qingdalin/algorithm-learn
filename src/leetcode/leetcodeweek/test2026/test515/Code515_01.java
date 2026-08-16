package leetcode.leetcodeweek.test2026.test515;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2026/8/16 9:50
 * https://leetcode.cn/contest/weekly-contest-515/problems/nearest-available-drone/description/
 */
public class Code515_01 {
    public static int nearestDrone(int[][] drones, int[] target) {
        int idx = 1000, ans = Integer.MAX_VALUE;
        int n = drones.length;
        int s = target[0], t = target[1];
        for (int i = 0, x, y, range; i < n; i++) {
            x = drones[i][0];
            y = drones[i][1];
            range = drones[i][2];
            int dist = Math.abs(x - s) + Math.abs(y - t);
            if (dist <= range) {
                if (dist < ans || (dist == ans && i < idx)) {
                    ans = dist;
                    idx = i;
                }
            }
        }
        return idx == 1000 ? -1 : idx;
    }
}
