package leetcode.leetcodeweek.test2025.test478;

import java.util.Arrays;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/11/30 9:46
 * https://leetcode.cn/contest/weekly-contest-478/problems/count-elements-with-at-least-k-greater-values/
 */
public class Code478_01 {
    public static int MAXN = 100001;
    public static int[] tree = new int[MAXN];
    public static int[] sorted = new int[MAXN];
    public static int n, cntv;
    public static int countElements(int[] nums, int k) {
        build(nums);
        for (int i = 0; i < n; i++) {
            add(nums[i], 1);
        }
        int ans = 0;
        for (int i = 0; i < n; i++) {
            if (query(cntv) - query(nums[i]) >= k) {
                ans++;
            }
        }
        return ans;
    }

    private static void build(int[] nums) {
        n = nums.length;
        Arrays.fill(tree, 0, n + 1, 0);
        for (int i = 0; i < n; i++) {
            sorted[i + 1] = nums[i];
        }
        Arrays.sort(sorted, 1, n + 1);
        int len = 1;
        for (int i = 2; i <= n; i++) {
            if (sorted[len] != sorted[i]) {
                sorted[++len] = sorted[i];
            }
        }
        cntv = len;
        for (int i = 0; i < n; i++) {
            nums[i] = kth(nums[i]);
        }
    }

    private static int kth(int num) {
        int l = 1, r = cntv, mid, ret = 0;
        while (l <= r) {
            mid = (l + r) >> 1;
            if (sorted[mid] <= num) {
                ret = mid;
                l = mid + 1;
            } else {
                r = mid - 1;
            }
        }
        return ret;
    }


    public static int lowbit(int i) {
        return i & -i;
    }

    public static void add(int i, int v) {
        while (i <= n) {
            tree[i] += v;
            i += lowbit(i);
        }
    }

    public static int query(int i) {
        int ret = 0;
        while (i > 0) {
            ret += tree[i];
            i -= lowbit(i);
        }
        return ret;
    }

    public static void main(String[] args) {
        int[] arr = {656249884,846115159};
        int k = 1;
        System.out.println(countElements(arr, k));
    }

}
