package algorithm.class81dp16;

import java.util.Arrays;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2024/7/6 20:08
 *
 * 给你一个整数数组 nums 。如果 nums 的一个子集中，
 * 所有元素的乘积可以表示为一个或多个 互不相同的质数 的乘积，那么我们称它为 好子集 。
 * 比方说，如果 nums = [1, 2, 3, 4] ：
 * [2, 3] ，[1, 2, 3] 和 [1, 3] 是 好 子集，乘积分别为 6 = 2*3 ，6 = 2*3 和 3 = 3 。
 * [1, 4] 和 [4] 不是 好 子集，因为乘积分别为 4 = 2*2 和 4 = 2*2 。
 * 请你返回 nums 中不同的 好 子集的数目对 109 + 7 取余 的结果。
 *
 * nums 中的 子集 是通过删除 nums 中一些（可能一个都不删除，也可能全部都删除）
 * 元素后剩余元素组成的数组。如果两个子集删除的下标不同，那么它们被视为不同的子集。
 * https://leetcode.cn/problems/the-number-of-good-subsets/description/
 */
public class NumberOfGoodSubsets {
    public static int[] own = new int[] {
        // 30以内一共10个质数
        // 2 3 5 7 11 13 17 19 23 29
        0b0000000000, // 0
        0b0000000000, // 1
        0b0000000001, // 2
        0b0000000010, // 3
        0b0000000000, // 4
        0b0000000100, // 5
        0b0000000011, // 6
        0b0000001000, // 7
        0b0000000000, // 8
        0b0000000000, // 9
        0b0000000101, // 10
        0b0000010000, // 11
        0b0000000000, // 12
        0b0000100000, // 13
        0b0000001001, // 14
        0b0000000110, // 15
        0b0000000000, // 16
        0b0001000000, // 17
        0b0000000000, // 18
        0b0010000000, // 19
        0b0000000000, // 20
        0b0000001010, // 21
        0b0000010001, // 22
        0b0100000000, // 23
        0b0000000000, // 24
        0b0000000000, // 25
        0b0000100001, // 26
        0b0000000000, // 27
        0b0000000000, // 28
        0b1000000000, // 29
        0b0000000111, // 30
    };

    public static int limit = (1 << 10); // 十个质数
    public static int mod = 1000000007;
    public static int MAXV = 30;
    public int numberOfGoodSubsets1(int[] nums) {
        int[] cnt = new int[MAXV + 1];
        for (int num : nums) {
            cnt[num]++;
        }
        int[][] dp = new int[MAXV + 1][limit];
        for (int i = 0; i <= MAXV; i++) {
            Arrays.fill(dp[i], -1);
        }
        int ans = 0;
        for (int s = 1; s < limit; s++) {
            ans = (ans + f1(MAXV, s, cnt, dp)) % mod;
        }
        return ans;
    }

    private int f1(int i, int s, int[] cnt, int[][] dp) {
        if (dp[i][s] != -1) {
            return dp[i][s];
        }
        int ans = 0;
        if (i == 1) {
            if (s == 0) {
                // s 是0代表所有状态质因子全部搞定
                ans = 1;
                for (int j = 0; j < cnt[1]; j++) {
                    ans = (ans << 1) % mod;
                }
            }
        } else {
            ans = f1(i - 1, s, cnt, dp);
            int cur = own[i];
            int times = cnt[i];
            if (cur != 0 && times != 0 && (s & cur) == cur) {
                //ans = (ans + f1(i - 1, s ^ cur, cnt , dp) * times) % mod;
                ans = (int) (((long) f1(i - 1, s ^ cur, cnt, dp) * times + ans) % mod);
            }
        }
        dp[i][s] = ans;
        return ans;
    }

    public static int[] cnts = new int[MAXV + 1];
    public static int[] dp = new int[limit];
    public int numberOfGoodSubsets(int[] nums) {
        Arrays.fill(cnts, 0);
        Arrays.fill(dp, 0);
        for (int num : nums) {
            cnts[num]++;
        }
        dp[0] = 1;
        for (int i = 0; i < cnts[1]; i++) {
            dp[0] = (dp[0] << 1) % mod;
        }
        for (int i = 2, cur, times; i <= MAXV; i++) {
            cur = own[i];
            times = cnts[i];
            if (cur != 0 && times != 0) {
                for (int status = limit - 1; status >= 0; status--) {
                    if ((status & cur) == cur) {
                        // 强转long否则溢出
                        dp[status] = (int) ((dp[status] + (long) dp[status ^ cur] * times) % mod);
                    }
                }
            }
        }
        int ans = 0;
        for (int i = 1; i < limit; i++) {
            ans = (ans + dp[i]) % mod;
        }
        return ans;
    }
}
