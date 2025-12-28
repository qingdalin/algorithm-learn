package leetcode.study.leetcode401_600;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/9/21 17:06
 * https://leetcode.cn/problems/random-pick-with-weight/
 */
public class RandomWeight {
    public int sum, n;
    public int[] preSum;
    public RandomWeight(int[] w) {
        n = w.length;
        sum = 0;
        preSum = new int[n];
        for (int i = 0; i < n; i++) {
            sum += w[i];
            preSum[i] = sum;
        }
    }

    public int pickIndex() {
        int idx = (int) (Math.random() * sum ) + 1;
        return f(idx);
    }

    private int f(int num) {
        int l = 0, r = n - 1, mid, ans = 0;
        while (l <= r) {
            mid = (l + r) / 2;
            if (preSum[mid] >= num) {
                ans = mid;
                r = mid - 1;
            } else {
                l = mid + 1;
            }
        }
        return ans;
    }
}
