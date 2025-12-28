package algorithm.class84dp19;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2024/7/13 19:51
 * 如果一个正整数每一个数位都是 互不相同 的，我们称它是 特殊整数 。
 *
 * 给你一个 正 整数 n ，请你返回区间 [1, n] 之间特殊整数的数目。
 * https://leetcode.cn/problems/count-special-integers/description/
 */
public class CountSpecialNumbers {
    public int countSpecialNumbers(int n) {
        int len = 1;
        int offset = 1;
        int tmp = n / 10;
        while (tmp > 0) {
            len++;
            offset *= 10;
            tmp /= 10;
        }
        int[] cnt = new int[len];
        cnt[0] = 1;
        for (int i = 1, k = 10 - len + 1; i < len; i++, k++) {
            cnt[i] = k * cnt[i - 1];
        }
        int ans = 0;
        if (len >= 2) {
            ans = 9;
            for (int a = 9, b = 9, k = 2; k < len; k++, b--) {
                a *= b;
                ans += a;
            }
        }
        int first = n / offset;
        ans += (first - 1) * cnt[len - 1];
        ans += f1(cnt, n, len - 1, offset / 10, 1 << first);
        return ans;
    }

    private int f1(int[] cnt, int num, int len, int offset, int status) {
        if (len == 0) {
            return 1;
        }
        int first = num / offset % 10;
        int ans = 0;
        for (int cur = 0; cur < first; cur++) {
            if ((status & (1 << cur)) == 0) {
                ans += cnt[len - 1];
            }
        }
        if ((status & (1 << first)) == 0) {
            ans += f1(cnt, num, len - 1, offset / 10, status | (1 << first));
        }
        return ans;
    }
}
