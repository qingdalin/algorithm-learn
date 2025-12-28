package leetcode.study.leetcode401_600;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/8/3 8:27
 * https://leetcode.cn/problems/maximum-fruits-harvested-after-at-most-k-steps/description/?envType=daily-question&envId=2025-08-03
 */
public class MaxTotalFruits {
    public int maxTotalFruits(int[][] fruits, int start, int k) {
        int n = fruits.length;
        int l = lower(fruits, start - k);
        int ans = 0;
        int sum = 0;
        for (int r = l; r < n && fruits[r][0] <= start + k; r++) {
            sum += fruits[r][1];
            while (fruits[r][0] - start + fruits[r][0] - fruits[l][0] > k
                && start - fruits[l][0] + fruits[r][0] - fruits[l][0] > k) {
                sum -= fruits[l][1];
                l++;
            }
            ans = Math.max(sum, ans);
         }
        return ans;
    }

    private int lower(int[][] fruits, int limit) {
        int l = -1;
        int r = fruits.length;
        int m;
        while (l + 1 < r) {
            m = (l + r) / 2;
            if (fruits[m][0] < limit) {
                l = m;
            } else {
                r = m;
            }
        }
        return r;
    }
}
