package algorithm.class85dp20;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2024/7/14 13:26
 * 给定一个正整数 n ，请你统计在 [0, n] 范围的非负整数中，有多少个整数的二进制表示中不存在 连续的 1 。
 * https://leetcode.cn/problems/non-negative-integers-without-consecutive-ones/description/
 */
public class FindIntegers {
    public int findIntegers1(int n) {
        int[] cnt = new int[31];
        cnt[0] = 1;
        cnt[1] = 2;
        for (int i = 2; i <= 30; i++) {
            cnt[i]  = cnt[i - 1] + cnt[i - 2];
        }
        return f1(cnt, n, 30);
    }

    // i当前来到i位
    private int f1(int[] cnt, int num, int i) {
        if (i == -1) {
            return 1;
        }
        int ans = 0;
        if ((num & (1 << i)) != 0) {
            // num的i位不是0，结算一下
            ans += cnt[i];
            if ((num & (1 << (i + 1))) != 0) {
                // 有连续两个1，结束
                return ans;
            }
        }
        ans += f1(cnt, num, i - 1);
        return ans;
    }

    public int findIntegers(int n) {
        int[] cnt = new int[31];
        cnt[0] = 1;
        cnt[1] = 2;
        for (int i = 2; i <= 30; i++) {
            cnt[i]  = cnt[i - 1] + cnt[i - 2];
        }
        int ans = 0;
        for (int i = 30; i >= -1; i--) {
            if (i == -1) {
                ans++;
                break;
            }
            if ((n & (1 << i)) != 0) {
                // num的i位不是0，结算一下
                ans += cnt[i];
                if ((n & (1 << (i + 1))) != 0) {
                    // 有连续两个1，结束
                    break;
                }
            }
        }
        return ans;
    }
}
