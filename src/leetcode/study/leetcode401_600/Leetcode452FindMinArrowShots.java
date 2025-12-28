package leetcode.study.leetcode401_600;

import java.util.Arrays;
import java.util.Comparator;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/8/17 9:44
 * https://leetcode.cn/problems/minimum-number-of-arrows-to-burst-balloons/
 */
public class Leetcode452FindMinArrowShots {
    public static int findMinArrowShots(int[][] arr) {
        int n = arr.length;
        int cnt = 0;
        Arrays.sort(arr, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                if (o1[1] < o2[1]) {
                    return -1;
                } else if (o1[1] > o2[1]){
                    return 1;
                } else {
                    return 0;
                }
            }
        });
        for (int l = 0, r = -1; r < n; l = ++r) {
            while (r + 1 < n && arr[r + 1][0] <= arr[l][1]) {
                r++;
            }
            cnt++;
        }
        return cnt;
    }

    public static void main(String[] args) {
        int[][] arr = {
            {10,16},
            {2,8},
            {1,6},
            {7,12}
        };
//        int[][] arr = {
//            {-2147483646,-2147483645},
//            {2147483646,2147483647}
//        };
//        int[][] arr = {
//            {3, 9},
//            {7, 12},
//            {3, 8},
//            {6, 8},
//            {9, 10},
//            {2, 9},
//            {0, 9},
//            {3, 9},
//            {0, 6},
//            {2, 8}
//        };
        System.out.println(findMinArrowShots(arr));
    }
}
