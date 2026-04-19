package leetcode.leetcodeweek.test2026.test498;

import java.util.Arrays;
import java.util.Map;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2026/4/19 9:55
 * https://leetcode.cn/problems/count-good-integers-on-a-grid-path/solutions/3954671/shang-xia-jie-shu-wei-dppythonjavacgo-by-feyc/
 */
public class Code498_04 {
    public long countGoodIntegersOnPath(long l, long r, String directions) {
        char[] lowS = String.valueOf(l).toCharArray();
        char[] highS = String.valueOf(r).toCharArray();
        int n = highS.length;
        boolean[] inPath = new boolean[n];
        int pos = n - 16;
        for (char d : directions.toCharArray()) {
            if (pos >= 0) {
                inPath[pos] = true;
            }
            pos += d == 'R' ? 1 : 4;
        }
        inPath[n - 1] = true;

        long[][] memo = new long[n][10];
        for (long[] row : memo) {
            Arrays.fill(row, -1);
        }
        return dfs(0, 0, true, true, lowS, highS, inPath, memo);
    }

    private long dfs(int i, int pre, boolean limitLow, boolean limitHigh, char[] lowS, char[] highS, boolean[] inPath, long[][] memo) {
        if (i == highS.length) {
            return 1;
        }
        if (!limitLow && !limitHigh && memo[i][pre] >= 0) {
            return memo[i][pre];
        }
        int diff = highS.length - lowS.length;
        int lo = limitLow && i >= diff ? lowS[i - diff] - '0' : 0;
        int hi = limitHigh ? highS[i] - '0' : 9;
        long res = 0;
        int start = inPath[i] ? Math.max(lo, pre) : lo;
        for (int d = start; d <= hi; d++) {
            res += dfs(i + 1, inPath[i] ? d :pre,
                limitLow && d == lo,
                limitHigh && d == hi,
                lowS, highS, inPath, memo);
        }
        if (!limitLow && !limitHigh) {
            memo[i][pre] = res;
        }
        return res;
    }
}
