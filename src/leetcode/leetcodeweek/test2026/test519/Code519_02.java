package leetcode.leetcodeweek.test2026.test519;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2026/9/13 10:43
 * https://leetcode.cn/contest/weekly-contest-519/problems/minimum-operations-to-make-every-element-palindromic/
 */
public class Code519_02 {
    public static int MAXN = 2000000002;
    public static List<Integer>[] palindromes = new ArrayList[2];
    public static boolean flag1, flag2;
    static {
        Arrays.setAll(palindromes, a -> new ArrayList<>());
        palindromes[0].add(0);
        palindromes[1].add(0);
        for (int base = 1; ; base *= 10) {
            for (int i = base; i < base * 10; i++) {
                int x = i;
                for (int t = i / 10; t > 0; t /= 10) {
                    x = x * 10 + t % 10;
                }
                if (x > MAXN) {
                    flag1 = true;
                    break;
                }
                palindromes[x % 2].add(x);
            }
            if (flag1) {
                break;
            }
            for (int i = base; i < base * 10; i++) {
                int x = i;
                for (int t = i; t > 0; t /= 10) {
                    x = x * 10 + t % 10;
                }
                if (x > MAXN) {
                    flag2 = true;
                    break;
                }
                palindromes[x % 2].add(x);
            }
            if (flag2) {
                break;
            }
        }
    }
    public static long minOperations(int[] nums) {
        long ans = 0;
        for (int x : nums) {
            List<Integer> p = palindromes[x % 2];
            int i = lowerBound(p, x);
            ans += Math.min(p.get(i) - x, x - p.get(i - 1));
        }
        return ans / 2;
    }

    private static int lowerBound(List<Integer> nums, int target) {
        int left = -1, right = nums.size(); // 开区间
        while (left + 1 < right) {
            int mid = (left + right) >>> 1;
            if (nums.get(mid) >= target) {
                right = mid;
            } else {
                left = mid;
            }
        }
        return right;
    }

    public static int len(int num) {
        int len = 0;
        while (num > 0) {
            len++;
            num /= 10;
        }
        return len;
    }

    public static int power(int n, int p) {
        int ans = 1;
        while (p != 0) {
            if ((p & 1) == 1) {
                ans = ans * n;
            }
            n = n * n;
            p >>= 1;
        }
        return ans;
    }

    public static void main(String[] args) {
        int n = 8;
        System.out.println(len(n));
        System.out.println(power(2,6));
    }
}
