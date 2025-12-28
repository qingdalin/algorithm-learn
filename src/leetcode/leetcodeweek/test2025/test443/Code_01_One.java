package leetcode.leetcodeweek.test2025.test443;

import java.util.Arrays;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/3/30 9:50
 */
public class Code_01_One {
    public static int MAXN = 101;
    public static int[] stack = new int[MAXN];
    public static int r;
    public static int[] minCosts(int[] cost) {
        int n = cost.length;
        int[] ans = new int[n];
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < n; i++) {
            if (i == 0) {
                ans[i] = cost[i];
                min = cost[i];
            }
            if (min < cost[i]) {
                ans[i] = min;
            } else {
                ans[i] = cost[i];
                min = cost[i];
            }
        }
        return ans;
    }

    public static void main(String[] args) {
        int[] arr = new int[] {5,3,4,1,3,2};
        System.out.println(Arrays.toString(minCosts(arr)));
    }
}
