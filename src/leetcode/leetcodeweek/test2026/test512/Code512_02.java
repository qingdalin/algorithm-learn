package leetcode.leetcodeweek.test2026.test512;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2026/7/26 10:10
 * https://leetcode.cn/contest/weekly-contest-512/problems/aggregate-two-time-series/
 */
public class Code512_02 {
    public static List<List<Integer>> aggregateTimeSeries(int[][] s1, int[][] s2) {
        List<List<Integer>> ans = new ArrayList<>();
        int n = s1.length, m = s2.length;
        // [1,3], [1,5]
        // [2,2], [2,3]
        // [4,1], [4,3]
        // [5,2], [5,2]
        for (int i = 0, j = 0; i < n || j < m;) {
            List<Integer> cur = new ArrayList<>();
            if (i < n && j < m) {
                if (s1[i][0] < s2[j][0]) {
                    cur.add(s1[i][0]);
                    int val = s1[i][1];
                    if (s2[j][0] > s1[i][0]) {
                        val += s2[j][1];
                    }
                    cur.add(val);
                    i++;
                } else if (s1[i][0] > s2[j][0]) {
                    cur.add(s2[j][0]);
                    int val = s2[j][1];
                    if (s2[j][0] < s1[i][0]) {
                        val += s1[i][1];
                    }
                    cur.add(val);
                    j++;
                } else {
                    cur.add(s1[i][0]);
                    cur.add(s1[i][1] + s2[j][1]);
                    i++;
                    j++;
                }
            } else if (i < n) {
                cur.add(s1[i][0]);
                int val = s1[i][1];
                if (s2[m - 1][0] > s1[i][0]) {
                    val += s2[m - 1][1];
                }
                cur.add(val);
                i++;
            } else {
                cur.add(s2[j][0]);
                int val = s2[j][1];
                if (s2[j][0] < s1[n - 1][0]) {
                    val += s1[n - 1][1];
                }
                cur.add(val);
                j++;
            }
            ans.add(cur);
        }
        return ans;
    }

    public static List<List<Integer>> aggregateTimeSeries1(int[][] s1, int[][] s2) {
        List<List<Integer>> ans = new ArrayList<>();
        int n = s1.length, m = s2.length;
        // [1,3], [1,5]
        // [2,2], [2,3]
        // [4,1], [4,3]
        // [5,2], [5,2]
        for (int i = 0, j = 0; i < n || j < m;) {
            List<Integer> cur = new ArrayList<>();
            if (i < n && j < m) {
                if (s1[i][0] < s2[j][0]) {
                    cur.add(s1[i][0]);
                    cur.add(s1[i][1]);
                    i++;
                } else if (s1[i][0] > s2[j][0]) {
                    cur.add(s2[j][0]);
                    cur.add(s2[j][1]);
                    j++;
                } else {
                    cur.add(s1[i][0]);
                    cur.add(s1[i][1] + s2[j][1]);
                    i++;
                    j++;
                }
            } else if (i < n) {
                cur.add(s1[i][0]);
                cur.add(s1[i][1]);
                i++;
            } else {
                cur.add(s2[j][0]);
                cur.add(s2[j][1]);
                j++;
            }
            ans.add(cur);
        }
        int siz = ans.size();
        for (int i = 0; i < siz; i++) {
            if (i + 1 < siz) {
                List<Integer> pre = ans.get(i);
                List<Integer> suf = ans.get(i + 1);
                pre.set(1, pre.get(1) + suf.get(1));
            }
        }
        return ans;
    }

    public static void main(String[] args) {
//        int[][] s1 = {
//            {1,3},
//            {4,1}
//        };
//        int[][] s2 = {
//            {2,2},
//            {5,2}
//        };
        int[][] s1 = {
            {6,7},
        };
        int[][] s2 = {
            {9,8},
            {13,7}
        };
//        int[][] s1 = {
//            {16,49},
//        };
//        int[][] s2 = {
//            {16,28},
//        };
        System.out.println(aggregateTimeSeries(s1,s2));
    }
}
