package leetcode.leetcodeweek.test2025.test459;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/7/20 10:10
 * https://leetcode.cn/contest/weekly-contest-459/problems/count-number-of-trapezoids-i/
 */
public class Code459_02 {
    public static int MAXN = 100001;
    public static int mod = 1000000007;
    public static int n;
    public static Map<Integer, Integer> map = new HashMap<>();

    // C(n,m): n! / m! / (n-m)!
    public static int countTrapezoids(int[][] arr) {
        n = arr.length;
        map.clear();
        long ans = 0;
        for (int[] cur : arr) {
            map.put(cur[1], map.getOrDefault(cur[1], 0) + 1);
        }
        long pre = 0;
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            int v = entry.getValue();
            v -= 1;
            long cur = ((long) ((1 + v) % mod) * v % mod) / 2;
            // 总选择方法 = ans + (pre选法*cur选法)
            ans = (ans + (pre * cur) % mod) % mod;
            // 之前的选法最后吸收当前选法
            pre = (pre + cur) % mod;
        }
        // 之前的所有宣发

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
            {-17, -64},
            {45, 65},
            {85, -64},
            {-13, -64},
            {-92, 69},
            {-97, 69},
            {56, 69},
            {88, 65}
        };
        System.out.println(countTrapezoids(arr));
    }
}
