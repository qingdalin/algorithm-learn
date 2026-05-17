package leetcode.leetcodeweek.test2026.test502;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2026/5/17 9:26
 * https://leetcode.cn/contest/weekly-contest-502/problems/count-k-th-roots-in-a-range/
 */
public class Code502_02 {
    public static int MAXK = 31;
    public static int MAXN = 32000;
    public static long INF = 1000000000L;
    public static int[][] arr = new int[MAXK][MAXN];
    static {
        for (int i = 0; i < MAXK; i++) {
            for (int j = 0; j < MAXN; j++) {
                arr[i][j] = 1000000001;
            }
        }
        for (int k = 2; k < MAXK; k++) {
            for (int i = 0; i < MAXN; i++) {
                long power = power(i, k);
                if (power > INF) {
                    break;
                }
                arr[k][i] = (int) power;
            }
        }
    }

    public static long power(long num, int p) {
        long ans = 1;
        while (p != 0) {
            if ((p & 1) == 1) {
                ans = ans * num;
            }
            num *= num;
            p >>= 1;
        }
        return ans;
    }

    public static int countKthRoots(int l, int r, int k) {
        if (k == 1) {
            return r - l + 1;
        }
        int ans = 0;
        int[] cur = arr[k];
        int high = high(cur, r);
        int lower = lower(cur, l);
        return high - lower + 1;
    }

    public static int lower(int[] nums, int num) {
        int l = 0, r = nums.length - 1, mid, ans = -1;
        while (l <= r) {
            mid = (l + r) >> 1;
            if (nums[mid] >= num) {
                ans = mid;
                r = mid - 1;
            } else {
                l = mid + 1;
            }
        }
        return ans;
    }

    public static int high(int[] nums, int num) {
        int l = 0, r = nums.length - 1, mid, ans = -1;
        while (l <= r) {
            mid = (l + r) >> 1;
            if (nums[mid] <= num) {
                ans = mid;
                l = mid + 1;
            } else {
                r = mid - 1;
            }
        }
        return ans;
    }

    public static void main(String[] args) {
        int l = 8;
        int r = 30;
        System.out.println(countKthRoots(l, r, 2));
    }
}
