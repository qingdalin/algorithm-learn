package leetcode.study.leetcode401_600;

import java.util.Arrays;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/8/31 16:51
 * https://leetcode.cn/problems/relative-ranks/
 */
public class Leetcode506FindRelativeRanks {
    public String[] findRelativeRanks(int[] arr) {
        int n = arr.length;
        String[] ans = new String[n];
        int[][] sort = new int[n][2];
        for (int i = 0; i < n; i++) {
            sort[i][0] = arr[i];
            sort[i][1] = i;
        }
        Arrays.sort(sort, (a, b) -> b[0] - a[0]);
        for (int i = 0; i < n; i++) {
            if (i <= 2) {
                if (i == 0) {
                    ans[sort[i][1]] = "Gold Medal";
                } else if (i == 1) {
                    ans[sort[i][1]] = "Silver Medal";
                } else {
                    ans[sort[i][1]] = "Bronze Medal";
                }
            } else {
                ans[sort[i][1]] = (i + 1) + "";
            }
        }
        return ans;
    }
}
