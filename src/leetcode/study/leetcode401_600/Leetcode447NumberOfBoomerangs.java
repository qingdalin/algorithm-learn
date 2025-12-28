package leetcode.study.leetcode401_600;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/8/16 16:37
 * https://leetcode.cn/problems/number-of-boomerangs/
 */
public class Leetcode447NumberOfBoomerangs {
    public static int numberOfBoomerangs(int[][] arr) {
        int n = arr.length;
        int cnt = 0;
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < n; i++) {
            map.clear();
            int a = arr[i][0], b = arr[i][1];
            for (int j = 0, d, x, y; j < n; j++) {
                x = a - arr[j][0];
                y = b - arr[j][1];
                d = x * x + y * y;
                int num = map.getOrDefault(d, 0);
                cnt += num * 2;
                map.put(d, num + 1);
            }
        }
        return cnt;
    }

    public static int numberOfBoomerangs1(int[][] arr) {
        int n = arr.length;
        int cnt = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i == j) {
                    continue;
                }
                int d1 = dis(arr[i], arr[j]);
                for (int k = 0; k < n; k++) {
                    if (k == i || k == j) {
                        continue;
                    }
                    if (d1 == dis(arr[i], arr[k])) {
                        cnt++;
                    }
                }
            }
        }
        return cnt;
    }

    public static int dis(int[] x, int[] y) {
        int i = x[0] - y[0];
        int j = x[1] - y[1];
        return i * i + j * j;
    }
}
