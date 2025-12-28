package algorithm.class119lca02;

import java.util.List;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2024/9/22 11:40
 * https://leetcode.cn/problems/maximize-value-of-function-in-a-ball-passing-game/description/
 * 给你一个长度为 n 下标从 0 开始的整数数组 receiver 和一个整数 k 。
 * 总共有 n 名玩家，玩家 编号 互不相同，且为 [0, n - 1] 中的整数。
 * 这些玩家玩一个传球游戏，receiver[i] 表示编号为 i 的玩家会传球给编号为 receiver[i] 的玩家。
 * 玩家可以传球给自己，也就是说 receiver[i] 可能等于 i 。
 * 你需要从 n 名玩家中选择一名玩家作为游戏开始时唯一手中有球的玩家，球会被传 恰好 k 次。
 * 如果选择编号为 x 的玩家作为开始玩家，定义函数 f(x) 表示从编号为 x 的玩家开始，
 * k 次传球内所有接触过球玩家的编号之 和 ，如果有玩家多次触球，则 累加多次 。
 * 换句话说， f(x) = x + receiver[x] + receiver[receiver[x]] + ... + receiver(k)[x] 。
 * 你的任务时选择开始玩家 x ，目的是 最大化 f(x) 。
 * 请你返回函数的 最大值 。
 * 注意：receiver 可能含有重复元素。
 */
public class Code04_PassingBallMaximizeValue {
    public static int MAXN = 100001;
    public static int LIMIT = 34;
    public static int power;
    public static int m; // 二进制上有几个1

    public static int[] kbits = new int[LIMIT];
    public static int[][] stjump = new int[MAXN][LIMIT];
    public static long[][] stsum = new long[MAXN][LIMIT];
    public long getMaxFunctionValue(List<Integer> receiver, long k) {
        build(k);
        int n = receiver.size();
        for (int i = 0; i < n; i++) {
            stjump[i][0] = receiver.get(i);
            stsum[i][0] = receiver.get(i);
        }
        for (int p = 1; p <= power; p++) {
            for (int i = 0; i < n; i++) {
                stjump[i][p] = stjump[stjump[i][p - 1]][p - 1];
                stsum[i][p] = stsum[i][p - 1] + stsum[stjump[i][p - 1]][p - 1];
            }
        }
        long ans = 0, sum;
        for (int i = 0, cur; i < n; i++) {
            cur = i;
            sum = i;
            for (int j = 0; j < m; j++) {
                sum += stsum[cur][kbits[j]];
                cur = stjump[cur][kbits[j]];
            }
            ans = Math.max(ans, sum);
        }
        return ans;
    }

    private void build(long k) {
        power = 0;
        while ((1L << power) <= (k >> 1)) {
            power++;
        }
        m = 0;
        for (int p = power; p >= 0; p--) {
            if ((1L << p) <= k) {
                kbits[m++] = p;
                k  -= (1L << p);
            }
        }
    }

}
