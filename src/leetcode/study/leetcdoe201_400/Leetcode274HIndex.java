package leetcode.study.leetcdoe201_400;

import java.util.Arrays;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/7/21 20:25
 * https://leetcode.cn/problems/h-index/
 */
public class Leetcode274HIndex {
    public int hIndex(int[] arr) {
        int n = arr.length;
        int ans = 0;
        int l = 0, r = n - 1, m;
        while (l <= r) {
            m = (l + r) >> 1;
            if (arr[m] >= n - m) {
                ans = n - m;
                l = m + 1;
            } else {
                r = m - 1;
            }
        }
        return ans;
    }


    public int hIndex1(int[] arr) {
        int n = arr.length;
        Arrays.sort(arr);
//        for(int i = n - 1; i >= 0; i--) {
//            if (arr[i] <= n && arr[i] <= n - i) {
//                return arr[i];
//            }
//        }
        int h = 0;
        int i = n - 1;
        while (i >= 0 && arr[i] > h) {
            h++;
            i--;
        }
        return h;
        // 3,0,6,1,5
        // 0 1 3 5 6
        // 0 1 2 3 4
    }
}
