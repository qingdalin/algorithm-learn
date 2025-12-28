package algorithm.class85dp20;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2024/7/14 14:11
 * 给定一个整数 n，计算所有小于等于 n 的非负整数中数字 1 出现的个数。
 * https://leetcode.cn/problems/number-of-digit-one/description/
 * https://leetcode.cn/problems/digit-count-in-range/description/
 * https://www.luogu.com.cn/problem/P2602
 */
public class CountDigitOne {
    public int countDigitOne(int n) {
        return countDigit(n, 1);
    }

    private int countDigit(int n, int d) {
        int ans = 0;
        for (int right = 1, left, tmp = n; tmp != 0;  right *= 10, tmp /= 10) {
            /*
                30583
                情况1 d != 0
                1.  1 ~ 30583 d == 5
                    cur < d
                    个位cur=3 0-3058 5
                    个位没有额外加的
                2.  cur > d
                    十位cur=8 0~304 5 0~9
                    十位额外加 0~9
                3. cur == d
                    百位cur=5 0~29 5 0~99
                    百位额外加 30 5 0~83
                情况2 d == 0
                1. 1~30583 d == 0
                   cur > d
                   个位cur=3 1~3057
                   个位额外加0
                   十位cur=8 1~304 0 0~9
                   十位额外加 305 0 0~9
                   百位cur=5 1~29 0 0~99
                   百位额外加 30 0 0~99
                   千位cur=0 1~2 0 0~999
                   千位额外加 3 0 0~583
             */
            left = tmp / 10;
            int cur = tmp % 10;
            if (d == 0) {
                left--;
            }
            ans += (right * left);
            if (cur > d) {
                ans += right;
            } else if (cur == d) {
                ans += (n % right + 1);
            }
        }
        return ans;
    }
}
