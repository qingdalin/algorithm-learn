package leetcode.leetcodeweek.test2026.test517;

import java.util.Arrays;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2026/8/30 9:49
 * https://leetcode.cn/contest/weekly-contest-517/problems/minimum-operations-to-form-subset-sum-i/
 */
public class Code517_03 {
    public int minOperations(int[] nums, int sum) {
        int[] f = new int[sum + 1];
        Arrays.fill(f, Integer.MAX_VALUE / 2);
        f[0] = 0;
        for (int x : nums) {
            int w = 32 - Integer.numberOfLeadingZeros(x);
            for (int i = sum; i > 0; i--) {
                // 回想一下，0-1 背包是选或不选，状态转移方程为 f[i] = min(f[i], f[i-物品体积] + 物品价值)
                // 本题是分组背包，要枚举选哪个物品（枚举乘了 a 次或者除了 a 次）
                for (int a = 0; (x << a) <= i; a++) {
                    // 物品体积为 x<<a，价值为 a
                    f[i] = Math.min(f[i], f[i - (x << a)] + a);
                }
                // 从小到大枚举 x>>a，方便在 x>>a > i 时跳出循环
                for (int a = w - 1; a > 0 && (x >> a) <= i; a--) {
                    // 物品体积为 x>>a，价值为 a
                    f[i] = Math.min(f[i], f[i - (x >> a)] + a);
                }
            }
        }
        return f[sum] == Integer.MAX_VALUE / 2 ? -1 : f[sum];
    }
}
