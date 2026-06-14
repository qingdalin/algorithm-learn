package leetcode.leetcodeweek.test2026.test505;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2026/6/7 10:23
 * https://leetcode.cn/contest/weekly-contest-505/problems/sum-of-compatible-numbers-in-range-i/
 */
public class Code505_01 {
    public static int sumOfGoodIntegers(int n, int k) {
        int ans = 0;
        int min = Math.abs(n - k);
        int max = Math.abs(n + k);
        for (int i = 1; i <= Math.max(min, max); i++) {
            if (Math.abs(n - i) <= k && (n & i) == 0) {
                ans += i;
            }
        }
        return ans;
    }

    public static void main(String[] args) {
        int n = 1, k = 13;
        System.out.println(sumOfGoodIntegers(n, k));
    }
}
