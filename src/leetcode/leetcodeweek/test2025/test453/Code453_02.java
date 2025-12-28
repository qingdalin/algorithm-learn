package leetcode.leetcodeweek.test2025.test453;

import java.util.Arrays;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/6/8 10:20
 * https://leetcode.cn/contest/weekly-contest-453/problems/count-the-number-of-computer-unlocking-permutations/
 */
public class Code453_02 {
    public static int MOD = 1000000007;
    public static int countPermutations(int[] arr) {
        int n = arr.length;
        Arrays.sort(arr, 1, n);
        if (arr[1] <= arr[0]) {
            return 0;
        }
        long ans = 1;
        for (int i = n - 1; i >= 1; i++) {
            ans = (ans * i) % MOD;
        }
        return (int) ans;
    }


}
