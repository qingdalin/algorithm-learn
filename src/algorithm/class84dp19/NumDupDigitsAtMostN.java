package algorithm.class84dp19;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2024/7/13 20:08
 * 给定正整数 n，返回在 [1, n] 范围内具有 至少 1 位 重复数字的正整数的个数。
 * https://leetcode.cn/problems/numbers-with-repeated-digits/description/
 * 和https://leetcode.cn/problems/count-special-integers/description/互斥，n - ans即可
 */
public class NumDupDigitsAtMostN {
    public int numDupDigitsAtMostN(int n) {
        return n - countSpecialNumbers(n);
    }

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
