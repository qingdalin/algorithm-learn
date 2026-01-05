package leetcode.study.everydayleetcode.every2025;

import java.util.Arrays;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/12/24 19:12
 * https://leetcode.cn/problems/apple-redistribution-into-boxes/?envType=daily-question&envId=2025-12-24
 */
public class Code_20251224MinimumBoxes {
    public int minimumBoxes(int[] apple, int[] capacity) {
        int sum = 0, n = apple.length, m = capacity.length;
        for (int i : apple) {
            sum += i;
        }
        Arrays.sort(capacity);
        int ans = 0;
        for (int i = m - 1; i >= 0; i--) {
            if (sum > 0) {
                sum -= capacity[i];
                ans++;
            } else {
                break;
            }
        }
        return ans;
    }
}
