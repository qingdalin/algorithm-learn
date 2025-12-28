package leetcode.study.leetcode001_200;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/6/27 20:46
 * https://leetcode.cn/problems/max-points-on-a-line/
 */
public class Leetcode149MaxPoints {
    public static int maxPoints(int[][] arr) {
        int n = arr.length;
        int ans = 0;
        if (n == 1) {
            return 0;
        }
        Map<Double, Integer> map = new HashMap<>();
        for (int i = 0; i < n; i++) {
            map.clear();
            for (int j = i; j < n; j++) {
                double k = getK(arr[i], arr[j]);
                map.put(k, map.getOrDefault(k, 0) + 1);
            }
            for (Map.Entry<Double, Integer> entry : map.entrySet()) {
                ans = Math.max(ans, entry.getValue());
            }
        }
        return ans;
    }

    public static double getK(int[] x, int[] y) {
        if (x[0] - y[0] == 0) {
            return 1;
        }
        if (x[1] - y[1] == 0) {
            return 0;
        }
        return ((double) (y[1] - x[1]) / (y[0] - x[0])) * 10000;
    }

    public static void main(String[] args) {
        int[][] arr = new int[][] {
            {1, 1},
            {2, 2},
            {3, 3}
        };
        System.out.println(maxPoints(arr));
    }
}
