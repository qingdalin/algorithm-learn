package leetcode.leetcodeweek.test2025.test464;

import java.util.Arrays;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/8/24 8:52
 * https://leetcode.cn/contest/weekly-contest-464/problems/jump-game-ix/
 */
public class Code464_03 {
    public static int[] maxValue(int[] arr) {
        // 从右往左，判断当前i之前的位置最大值
        int n = arr.length;
        int[] preMax = new int[n];
        preMax[0] = arr[0];
        for (int i = 1; i < n; i++) {
            preMax[i] = Math.max(preMax[i - 1], arr[i]);
        }
        int sufMin = Integer.MAX_VALUE;
        int mx = 0;
        for(int i = n - 1; i >= 0; i--) {
            if (preMax[i] <= sufMin) {
                mx = preMax[i];
            }
            sufMin = Math.min(sufMin, arr[i]);
            arr[i] = mx;
        }
        return arr;
    }

    public static int[] maxValue1(int[] arr) {
        // 从右往左，判断当前i之前的位置最大值
        int n = arr.length;
        int[] maxs = new int[n];
        int[][] mins = new int[n][2];
        maxs[0] = arr[0];
        for (int i = 1; i < n; i++) {
            maxs[i] = Math.max(maxs[i - 1], arr[i]);
        }
        mins[n - 1][0] = arr[n - 1];
        mins[n - 1][1] = n - 1;
        for(int i = n - 2; i >= 0; i--) {
            if (mins[i + 1][0] > arr[i] || mins[i + 1][0] == arr[i]) {
                mins[i][0] = arr[i];
                mins[i][1] = i;
            } else {
                mins[i][0] = mins[i + 1][0];
                mins[i][1] = mins[i + 1][1];
            }
        }
        int[] ans = new int[n];
        for(int i = n - 1; i >= 0; i--) {
            ans[i] = maxs[i];
        }

        for (int i = 0; i < n; i++) {
            ans[i] = Math.max(ans[i], ans[mins[i][1]]);
        }
        return ans;
    }

    public static void main(String[] args) {
//        int[] arr = {2,3,1};
//        int[] arr = {11,18,11};
        int[] arr = {30,21,5,35,24};
        System.out.println(Arrays.toString(maxValue(arr)));
    }
}
