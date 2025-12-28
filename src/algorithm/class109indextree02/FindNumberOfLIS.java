package algorithm.class109indextree02;

import java.util.Arrays;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2024/8/25 11:40
 * https://leetcode.cn/problems/number-of-longest-increasing-subsequence/description/
 * 给定一个未排序的整数数组 nums ， 返回最长递增子序列的个数 。
 *
 * 注意 这个数列必须是 严格 递增的。
 */
public class FindNumberOfLIS {
    public static int MAXN = 2001;
    // 维护信息 : 以数值i结尾的最长递增子序列，长度是多少
    // 维护的信息以树状数组组织
    public static int[] maxLenTree = new int[MAXN];
    // 维护信息 : 以数值i结尾的最长递增子序列，个数是多少
    // 维护的信息以树状数组组织
    public static int[] maxLenCntTree = new int[MAXN];
    public static int[] sort = new int[MAXN];
    public static int m, maxLen, maxLenCnt;
    public int findNumberOfLIS(int[] nums) {
        int n = nums.length;
        for (int i = 1; i <= n; i++) {
            sort[i] = nums[i - 1];
        }
        Arrays.sort(sort, 1, n + 1);
        m = 1;
        for (int i = 2; i <= n; i++) {
            if (sort[m] != sort[i]) {
                sort[++m] = sort[i];
            }
        }
        Arrays.fill(maxLenTree, 1, m + 1, 0);
        Arrays.fill(maxLenCntTree, 1, m + 1, 0);
        for (int num : nums) {
            int i = rank(num);
            query(i - 1);
            if (maxLen == 0) {
                // 如果查出数值<=i-1结尾的最长递增子序列长度为0
                // 那么说明，以值i结尾的最长递增子序列长度就是1，计数增加1
                add(i , 1, 1);
            } else {
                // 如果查出数值<=i-1结尾的最长递增子序列长度为maxLen != 0
                // 那么说明，以值i结尾的最长递增子序列长度就是maxLen + 1，计数增加maxLenCnt
                add(i, maxLen + 1, maxLenCnt);
            }
        }
        query(m);
        return maxLenCnt;
    }

    private void add(int i, int len, int cnt) {
        while (i <= m) {
            if (maxLenTree[i] == len) {
                maxLenCntTree[i] += cnt;
            } else if (maxLenTree[i] < len) {
                maxLenTree[i] = len;
                maxLenCntTree[i] = cnt;
            }
            i += lowBit(i);
        }
    }

    private void query(int i) {
        maxLen = maxLenCnt = 0;
        while (i > 0) {
            if (maxLen == maxLenTree[i]) {
                maxLenCnt += maxLenCntTree[i];
            } else if (maxLen < maxLenTree[i]) {
                maxLen = maxLenTree[i];
                maxLenCnt = maxLenCntTree[i];
            }
            i -= lowBit(i);
        }
    }

    private int lowBit(int i) {
        return i & -i;
    }

    private int rank(int v) {
        int l = 1, r = m, mid;
        int ans = 0;
        while (l <= r) {
            mid = (l + r) / 2;
            if (sort[mid] >= v) {
                ans = mid;
                r = mid - 1;
            } else {
                l = mid + 1;
            }
        }
        return ans;
    }
}
