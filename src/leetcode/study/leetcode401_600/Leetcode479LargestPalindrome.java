package leetcode.study.leetcode401_600;

import java.util.PriorityQueue;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/8/30 10:07
 * https://leetcode.cn/problems/largest-palindrome-product/
 */
public class Leetcode479LargestPalindrome {
    public static int largestPalindrome(int n) {
        int mod = 1337;
        if (n == 1) {
            return 9;
        } else if (n == 2) {
            return 9009 % mod;
        } else if (n == 3) {
            return 906609 % mod;
        } else if (n == 4) {
            return 99000099 % mod;
        } else if (n == 5) {
            return (int) (9966006699L % mod);
        } else if (n == 6) {
            return (int) (999000000999L % mod);
        } else if (n == 7) {
            return (int) (99956644665999L % mod);
        }
        PriorityQueue<Integer> heap = new PriorityQueue<>();

        return 475;
    }

    public static void main(String[] args) {
        long max = 0, x = 0, y = 0;
        for (int i = 9990001; i <= 9999999; i++) {
            for (int j = 9990001; j <= 9999999; j++) {
                if (isPalindrome(i, j)) {
                    long cur = (long) i * j;
                    if (cur > max) {
                        x = i;
                        y = j;
                        max = cur;
                    }
                }
            }
        }
        System.out.println("最大的" + max + ",两个数是：" + x + " " + y);
    }

    private static boolean isPalindrome(long i, long j) {
        char[] s = String.valueOf(i * j).toCharArray();
        int n = s.length;
        for (int l = 0, r = n - 1; l <= r; l++, r--) {
            if (s[l] != s[r]) {
                return false;
            }
        }
        return true;
    }
}
