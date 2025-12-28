package leetcode.study.leetcode401_600;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/8/30 16:50
 * https://leetcode.cn/problems/teemo-attacking/
 */
public class Leetcode495FindPoisonedDuration {
    public static int findPoisonedDuration(int[] arr, int t) {
        int ans = 0, n = arr.length;
        for (int i = 0; i < n; i++) {
            if (i < n - 1) {
//                ans += (Math.min(arr[i + 1] - arr[i], arr[i] + t - 1));
                if (arr[i] + t - 1 <= arr[i + 1]) {
                    ans += t;
                } else {
                    ans += arr[i + 1]- arr[i];
                }
            }
        }
        return ans + t;
    }
}
