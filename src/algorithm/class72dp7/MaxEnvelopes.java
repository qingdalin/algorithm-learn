package algorithm.class72dp7;

import java.util.Arrays;

/**
 * @author 汪大鹏
 * @version 1.0
 * @date 2024-06-02 10:24
 * 给你一个二维整数数组 envelopes ，其中 envelopes[i] = [wi, hi] ，表示第 i 个信封的宽度和高度。
 *
 * 当另一个信封的宽度和高度都比这个信封大的时候，这个信封就可以放进另一个信封里，如同俄罗斯套娃一样。
 *
 * 请计算 最多能有多少个 信封能组成一组“俄罗斯套娃”信封（即可以把一个信封放到另一个信封里面）。
 *
 * 注意：不允许旋转信封。
 * https://leetcode.cn/problems/russian-doll-envelopes/description/
 * 先按照宽度升序，宽度一样高度降序，最后取出所有高度求最长递增子序列即可
 */
public class MaxEnvelopes {
    public int maxEnvelopes(int[][] envelopes) {
        int n = envelopes.length;
        int[] ends = new int[n];
        Arrays.sort(envelopes, (a, b) -> (a[0] != b[0] ? a[0] - b[0] : b[1] - a[1]));
        int len = 0;
        int find;
        for (int i = 0; i < n; i++) {
            int num = envelopes[i][1];
            find = bs(ends, len, num);
            if (find == -1) {
                ends[len++] = num;
            } else {
                ends[find] = num;
            }
        }
        return len;
    }

    private int bs(int[] ends, int len, int num) {
        int l = 0, r = len - 1, ans = -1, m = 0;
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
}
