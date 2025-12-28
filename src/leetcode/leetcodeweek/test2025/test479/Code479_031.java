package leetcode.leetcodeweek.test2025.test479;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/12/7 10:00
 * https://leetcode.cn/contest/weekly-contest-479/problems/total-score-of-dungeon-runs/
 */
public class Code479_031 {
    public static long totalScore(int hp, int[] damage, int[] requirement) {
        int n = damage.length;
        int[] sum = new int[n + 1];
        for (int i = 0; i < n; i++) {
            sum[i + 1] = sum[i] + damage[i];
        }
        long ans = 0;
        for (int i = 1; i <= n; i++) {
            if (hp - damage[i - 1] < requirement[i - 1]) {
                continue;
            }
            int l = 1, r = i;
            int cur = 0;
            while (l <= r) {
                int mid = (l + r) >> 1;
                if (hp - (sum[i] - sum[mid - 1]) >= requirement[i - 1]) {
                    cur = mid;
                    r = mid - 1;
                } else {
                    l  = mid + 1;
                }
            }
            ans += i - cur + 1;
        }
        return ans ;
    }


}
