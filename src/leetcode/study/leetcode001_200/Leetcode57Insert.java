package leetcode.study.leetcode001_200;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/5/18 19:06
 * https://leetcode.cn/problems/insert-interval/
 */
public class Leetcode57Insert {
    public static int[][] insert(int[][] intervals, int[] newInterval) {
        List<int[]> list = new ArrayList<>();
        int n = intervals.length;
        int s = newInterval[0];
        int e = newInterval[1];
        int l = -1, r = -1, i = 0;
        for (; i < n && (r == -1 || l == -1); i++) {
            if (l == -1) {
                if (intervals[i][1] < s) {
                    list.add(intervals[i]);
                } else {
                    l = Math.min(s, intervals[i][0]);
                }
            }
            if (intervals[i][0] > e) {
                r = e;
                break;
            }
            if (r == -1) {
                if (intervals[i][1] >= e && intervals[i][0] <= e) {
                    r = intervals[i][1];
                }
            }
        }
        if (l == -1 && r == -1) {
            list.add(new int[]{s, e});
        } else if (l != -1 && r == -1){
            list.add(new int[] {l, e});
        } else if (l == -1){
            list.add(new int[] {s, r});
        } else {
            list.add(new int[] {l, r});
        }
        for (; i < n; i++) {
            list.add(intervals[i]);
        }
        int[][] ans = new int[list.size()][2];
        for (int j = 0; j < list.size(); j++) {
            ans[j] = list.get(j);
        }
        return ans;
    }

    public static void main(String[] args) {
        int[][] arr1 = new int[][] {{1,2},{3,5},{6,7},{8,10},{12,16}};
        int[] arr2 = new int[] {4,8};
//        int[][] arr1 = new int[][] {{1,3},{6,9}};
//        int[] arr2 = new int[] {2,5};
        System.out.println(Arrays.deepToString(insert(arr1, arr2)));
    }
}
