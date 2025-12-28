package leetcode.leetcodeweek.test2025.test480;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/12/14 9:48
 * https://leetcode.cn/contest/weekly-contest-480/problems/minimum-moves-to-balance-circular-array/
 */
public class Code480_03 {
    public static long minMoves(int[] arr) {
        long sum = 0;
        int num = Integer.MAX_VALUE, index = 0, n = arr.length;
        for (int i = 0; i < n; i++) {
            sum += arr[i];
            if (arr[i] < num) {
                index = i;
                num = arr[i];
            }
        }
        if (sum < 0) {
            return -1;
        }
        long ans = 0;
        for (int left = (index + n - 1) % n, right = (index + n + 1) % n; num < 0;) {
            int rdist = (right - index + n) % n;
            int ldist = (index - left + n) % n;
            if (rdist < ldist) {
                int k = Math.min(arr[right], Math.abs(num));
                num += arr[right];
                ans += (long) rdist * k;
                right = (right + 1 + n) % n;
            } else {
                int k = Math.min(arr[left], Math.abs(num));
                num += arr[left];
                ans += (long) ldist * k;
                left = (left - 1 + n) % n;
            }
        }
        return ans;
    }

    public static void main(String[] args) {
        int[] arr = {14,12,7,-16};
        System.out.println(minMoves(arr));
    }
    // len = 6;
    // 1 2 -5 2 4 5
    // 0 1 2  3 4 5
    // (i + n - 1) % n
    // (i + n + 1 ) % n
 }
