package leetcode.leetcodeweek.test2025.test459;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/7/20 10:10
 * https://leetcode.cn/contest/weekly-contest-459/problems/count-number-of-trapezoids-ii/description/
 */
public class Code459_04 {
    public static int n;
    public static Map<Double, Integer> map = new HashMap<>();

    // C(n,m): n! / m! / (n-m)!
    public static int countTrapezoids(int[][] arr) {
        n = arr.length;
        map.clear();
        long ans = 0;
        for (int i = 0; i < n; i++) {
            int x1 = arr[i][0], y1 = arr[i][1];
            for (int j = i + 1; j < n; j++) {
                int x2 = arr[j][0], y2 = arr[j][1];
                double k = (double) (y2 - y1) / (x2 - x1);
                map.put(k, map.getOrDefault(k, 0) + 1);
            }
        }
        Set<Double> set = new HashSet<>();
        for (int i = 0; i < n; i++) {
            int x1 = arr[i][0], y1 = arr[i][1];
            for (int j = i + 1; j < n; j++) {
                int x2 = arr[j][0], y2 = arr[j][1];
                double k = (double) (y2 - y1) / (x2 - x1);
                int v = map.get(k);
                if (v <= 1) {
                    continue;
                }
                set.add(k);
                v -= 1;
                int pre = (1 + v) * v / 2;
                ans += pre;
            }
        }
        return (int) ans;
    }


    public static void main(String[] args) {
//        int[][] arr = new int[][] {
//            {1,0},
//            {2,0},
//            {3,0},
//            {2,2},
//            {3,2}
//        };
//        int[][] arr = new int[][] {
//            {-73,-72},
//            {-1,-56},
//            {-92,30},
//            {-57,-89},
//            {-19,-89},
//            {-35,30},
//            {64,-72}
//        };
        int[][] arr = new int[][]{
            {0, 0},
            {1, 0},
            {0, 1},
            {2, 1}
        };
        System.out.println(countTrapezoids(arr));
    }
}
