package leetcode.leetcodeweek.test2025.test460;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/7/27 9:21
 * https://leetcode.cn/contest/weekly-contest-460/problems/minimum-jumps-to-reach-end-via-prime-teleportation/
 */
public class Code460_03 {
    public static int MAXN = 1000001;
    public static boolean flag = false;
    public static boolean[] noprime = new boolean[MAXN];
    static {
        noprime[1] = true;
        for (int i = 2; i < MAXN; i++) {
            if ((long) i * i < MAXN) {
                if (!noprime[i]) {
                    for (int j = i * i; j < MAXN; j += i) {
                        noprime[j] = true;
                    }
                }
            }
        }
    }
    public static int minJumps(int[] nums) {
        int n = nums.length;
        flag = false;
        return f(0, n, nums, 0) - 1;
    }

    private static int f(int i, int n, int[] nums, int sum) {
        if (i < 0) {
            return Integer.MAX_VALUE;
        }
        if (i >= n) {
            return sum;
        }
        int p1 = Integer.MAX_VALUE;
        int p2 = Integer.MAX_VALUE;
        if (noprime[nums[i]]) {
            p1 = Math.min(f(i + 1, n, nums, sum + 1), p1);
        } else {
            p2 = f(i + 1, n, nums, sum + 1);
            for (int j = i + 1; j < n; j++) {
                if (nums[j] % nums[i] == 0) {
                    p2 = Math.min(p2, f(j, n, nums, sum + 1));
                }
            }
        }
        return Math.min(p1, p2);
    }

    public static void main(String[] args) {
//        int[] arr = new int[] {4,6,5,8};
        int[] arr = new int[] {10,2,11,2};
//        int[] arr = new int[] {893,786,607,137,69,381,790,233,15,42,7,764,890,269,84,262,870,514,514,650,269,485,760,181,489,107,585,428,862,563};
        System.out.println(minJumps(arr));
    }
}
