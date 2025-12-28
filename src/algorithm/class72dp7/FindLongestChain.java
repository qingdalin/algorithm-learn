package algorithm.class72dp7;

import java.util.Arrays;

/**
 * @author 汪大鹏
 * @version 1.0
 * @date 2024-06-02 11:12
 * 给你一个由 n 个数对组成的数对数组 pairs ，其中 pairs[i] = [lefti, righti] 且 lefti < righti 。
 *
 * 现在，我们定义一种 跟随 关系，当且仅当 b < c 时，数对 p2 = [c, d] 才可以跟在 p1 = [a, b] 后面。我们用这种形式来构造 数对链 。
 *
 * 找出并返回能够形成的 最长数对链的长度 。
 *
 * 你不需要用到所有的数对，你可以以任何顺序选择其中的一些数对来构造。
 * https://leetcode.cn/problems/maximum-length-of-pair-chain/description/
 */
public class FindLongestChain {
    public int findLongestChain1(int[][] pairs) {
        int n = pairs.length;
        int[] ends = new int[n];
        Arrays.sort(pairs, (a, b) -> a[0] - b[0]);
        int len = 0;
        int find = 0;
        for (int i = 0; i < n; i++) {
            find = bs(ends, len, pairs[i][0]);
            if (find == -1) {
                ends[len++] = pairs[i][1];
            } else {
                ends[find] = Math.min(ends[find], pairs[i][1]);
            }
        }
        return len;
    }

    private int bs(int[] ends, int len, int num) {
        int l = 0, r = len - 1;
        int m = 0;
        int ans = -1;
        while (l <= r) {
            m = (l + r) / 2;
            if (num <= ends[m]) {
                ans = m;
                r = m - 1;
            } else {
                l = m + 1;
            }
        }
        return ans;
    }

    public int findLongestChain(int[][] pairs) {
        int n = pairs.length;
        int pre = Integer.MIN_VALUE;
        int ans = 0;
        Arrays.sort(pairs, (a, b) -> a[1] - b[1]);
        for (int i = 0; i < n; i++) {
            if (pre < pairs[i][0]) {
                pre = pairs[i][1];
                ans++;
            }
        }
        return ans;
    }
}
