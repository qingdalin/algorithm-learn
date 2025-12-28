package leetcode.study.leetcode401_600;

import java.util.Arrays;
import java.util.TreeMap;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/8/15 20:14
 * https://leetcode.cn/problems/find-right-interval/
 */
public class Leetcode436FindRightInterval {
    public int[] findRightInterval(int[][] arr) {
        int n = arr.length;
        int[] ans = new int[n];
        int[][] startArr = new int[n][2];
        for (int i = 0; i < n; i++) {
            startArr[i][0] = arr[i][0];
            startArr[i][1] = i;
        }
        Arrays.sort(startArr, (a, b) -> a[0] - b[0]);
        for (int i = 0; i < n; i++) {
            ans[i] = find(startArr, 0, n - 1, arr[i][1]);
        }
        return ans;
    }

    private int find(int[][] arr, int l, int r, int num) {
        int find = -1, mid;
        while (l <= r) {
            mid = (l + r) >> 1;
            if (arr[mid][0] >= num) {
                find = arr[mid][1];
                r = mid - 1;
            } else {
                l = mid + 1;
            }
        }
        return find;
    }

    public int[] findRightInterval1(int[][] arr) {
        TreeMap<Integer, Integer> map = new TreeMap<>();
        int n = arr.length;
        for (int i = 0; i < n; i++) {
            map.put(arr[i][0], i);
        }
        int[] ans = new int[n];
        Arrays.fill(ans, -1);
        for (int i = 0; i < n; i++) {
            Integer ceilingKey = map.ceilingKey(arr[i][1]);
            if (ceilingKey != null) {
                ans[i] = map.get(ceilingKey);
            }
        }
        return ans;
    }
}
