package leetcode.leetcodeweek.test2026.test517;

import java.util.Arrays;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2026/8/30 9:49
 * https://leetcode.cn/contest/weekly-contest-517/problems/minimum-operations-to-form-subset-sum-ii/
 */
public class Code517_04 {
    public int minOperations(int[] nums, int sum) {
        int[] f = new int[sum + 1];
        Arrays.fill(f, Integer.MAX_VALUE / 2);
        f[0] = 0;
        for (int x : nums) {
            for (int i = sum; i > 0; i--) {
                // 回想一下，0-1 背包是选或不选，状态转移方程为 f[i] = min(f[i], f[i-物品体积] + 物品价值)
                // 本题是分组背包，要枚举选哪个物品（枚举除法操作次数为 a，乘法操作次数为 b）
                for (int a = 0; (x >> a) > 0; a++) {
                    for (int b = 0; (x >> a << b) <= i; b++) {
                        // 物品体积为 x>>a<<b，价值为 a+b
                        f[i] = Math.min(f[i], f[i - (x >> a << b)] + a + b);
                    }
                }
            }
        }
        return f[sum] == Integer.MAX_VALUE / 2 ? -1 : f[sum];
    }
}
