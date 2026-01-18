package leetcode.leetcodeweek.test2026.test485;

import java.util.Arrays;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2026/1/18 9:29
 * https://leetcode.cn/contest/weekly-contest-485/problems/maximum-capacity-within-budget/
 */
public class Code485_02 {
    public static int maxCapacity(int[] costs, int[] capacity, int budget) {
        int n = costs.length;
        Integer[] idx = new Integer[n];
        for (int i = 0; i < n; i++) {
            idx[i] = i;
        }
        Arrays.sort(idx, (i, j) -> costs[i] - costs[j]);
        int[] preMax = new int[n + 1];
        int ans = 0;
        for (int k = 0; k < n && costs[idx[k]] < budget; k++) {
            int i = idx[k];
            int j1 = bs(idx, k, costs, budget - costs[i]);
            int j = bs1(idx, k, costs, budget - costs[i]);
            ans = Math.max(ans, capacity[i] + preMax[j]);
            preMax[k + 1] = Math.max(preMax[k], capacity[i]);
        }
        return ans;
    }
    private static int bs1(Integer[] idx, int right, int[] costs, int target) {
        int left = -1;
        while (left + 1 < right) {
            int mid = left + (right - left) / 2;
            if (costs[idx[mid]] >= target) {
                right = mid;
            } else {
                left = mid;
            }
        }
        return right;
    }
    private static int bs(Integer[] idx, int right, int[] costs, int target) {
        int left = 0, mid, ans = right;
        while (left <= right) {
            mid = (left + right) >> 1;
            if (costs[idx[mid]] >= target) {
                ans = mid;
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        return ans;
    }

    public static int maxCapacity1(int[] costs, int[] capacity, int budget) {
        int n = costs.length;
        int[][] arr = new int[n + 1][2];
        int len = 0;
        for (int i = 0; i < n; i++) {
            if (costs[i] < budget) {
                arr[++len][0] = costs[i];
                arr[len][1] = capacity[i];
            }
        }
        Arrays.sort(arr, 1, len + 1, (a, b) -> {
            double v1 = (double) a[1] / a[0];
            double v2 = (double) b[1] / b[0];
            return Double.compare(v2, v1);
        });
        int ans = 0;
        int cnt = 2;
        int tmp = budget;
        for (int i = 1; i <= len && cnt > 0; i++) {
            if (arr[i][0] < budget) {
                cnt--;
                ans += arr[i][1];
                budget -= arr[i][0];
            }
        }
        if (cnt == 1) {
            for (int i = 1; i <= len; i++) {
                if (arr[i][0] < tmp) {
                    ans = Math.max(ans, arr[i][1]);
                }
            }
        }
        return ans;
    }

    public static void main(String[] args) {
//        int[] a1 = {4,8,5,3};
//        int[] b1 = {1,5,2,7};
//        int n = 8;

//        int[] a1 = {3,7};
//        int[] b1 = {3,6};
//        int n = 12;

//        int[] a1 = {3,5,7,4};
//        int[] b1 = {2,4,3,6};
//        int n = 7;

        int[] a1 = {2,2,2};
        int[] b1 = {3,5,4};
        int n = 5;
        System.out.println(maxCapacity(a1, b1, n));
    }
}
