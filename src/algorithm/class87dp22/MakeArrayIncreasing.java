package algorithm.class87dp22;

import java.util.Arrays;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2024/7/20 14:41
 * 给你两个整数数组 arr1 和 arr2，返回使 arr1 严格递增所需要的最小「操作」数（可能为 0）。
 *
 * 每一步「操作」中，你可以分别从 arr1 和 arr2 中各选出一个索引，
 * 分别为 i 和 j，0 <= i < arr1.length 和 0 <= j < arr2.length，然后进行赋值运算 arr1[i] = arr2[j]。
 *
 * 如果无法让 arr1 严格递增，请返回 -1。
 * https://leetcode.cn/problems/make-array-strictly-increasing/description/
 */
public class MakeArrayIncreasing {
    public int makeArrayIncreasing1(int[] arr1, int[] arr2) {
        Arrays.sort(arr2);
        int m = 1;
        // 将arr2排序去重得到m的长度
        for (int i = 1; i < arr2.length; i++) {
            if (arr2[i] != arr2[m - 1]) {
                arr2[m++] = arr2[i];
            }
        }
        int n = arr1.length;
        int[] dp = new int[n];
        Arrays.fill(dp, -1);
        int ans = f(arr1, arr2, n, m, 0, dp);
        return ans != Integer.MAX_VALUE ? ans : -1;
    }

    // arr1的长度n，arr2的有效长度m
    // arr1的[0..i-1]严格递增且arr[i-1]一定没有被替换
    // 返回让arr1严格递增，arr1[i..]范围上还需要的替换次数
    // 如果做不到返回最大值
    private int f(int[] arr1, int[] arr2, int n, int m, int i, int[] dp) {
        if (i == n) {
            return 0;
        }
        if (dp[i] != -1) {
            return dp[i];
        }
        int ans = Integer.MAX_VALUE;
        // 如果i==0，前一位没有被替换是最小值，否则取到前一位
        int pre = i == 0 ? Integer.MIN_VALUE : arr1[i - 1];
        // 找到arr2比pre大的最左位置
        int find = bs(arr2, pre, m);
        for (int j = i, k = 0, next; j <= n; j++, k++) {
            if (j == n) {
                ans = Math.min(ans, k);
            } else {
                if (pre < arr1[j]) {
                    // 前一位比j位置小，去寻找下一个需要替换的
                    next = f(arr1, arr2, n, m, j + 1, dp);
                    if (next != Integer.MAX_VALUE) {
                        ans = Math.min(next + k, ans);
                    }
                }
                if (find != -1 && find < m) {
                    pre = arr2[find++];
                } else {
                    break;
                }
            }
        }
        dp[i] = ans;
        return ans;
    }

    private int bs(int[] arr2, int pre, int len) {
        int l = 0, r = len - 1, ans = -1;
        int m = 0;
        while (l <= r) {
            m = (l + r) / 2;
            if (arr2[m] > pre) {
                ans = m;
                r = m - 1;
            } else {
                l = m + 1;
            }
        }
        return ans;
    }

    public int makeArrayIncreasing(int[] arr1, int[] arr2) {
        Arrays.sort(arr2);
        int m = 1;
        // 将arr2排序去重得到m的长度
        for (int i = 1; i < arr2.length; i++) {
            if (arr2[i] != arr2[m - 1]) {
                arr2[m++] = arr2[i];
            }
        }
        int n = arr1.length;
        int[] dp = new int[n + 1];
        dp[n] = 0;
        for (int i = n - 1; i >= 0; i--) {
            int ans = Integer.MAX_VALUE;
            int pre = i == 0 ? Integer.MIN_VALUE : arr1[i - 1];
            int find = bs(arr2, pre, m);
            for (int j = i, k = 0, next; j <= n; j++, k++) {
                if (j == n) {
                    ans = Math.min(ans, k);
                } else {
                    if (pre < arr1[j]) {
                        // 前一位比j位置小，去寻找下一个需要替换的
                        next = dp[j + 1];
                        if (next != Integer.MAX_VALUE) {
                            ans = Math.min(next + k, ans);
                        }
                    }
                    if (find != -1 && find < m) {
                        pre = arr2[find++];
                    } else {
                        break;
                    }
                }
            }
            dp[i] = ans;
        }
        return dp[0] != Integer.MAX_VALUE ? dp[0] : -1;
    }
}
