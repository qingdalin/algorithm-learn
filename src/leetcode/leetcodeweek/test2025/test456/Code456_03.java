package leetcode.leetcodeweek.test2025.test456;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/6/29 7:50
 * https://leetcode.cn/contest/weekly-contest-456/problems/partition-array-to-minimize-xor/
 */
public class Code456_03 {
    public static int minXor(int[] arr, int k) {
        int n = arr.length;
        List<Integer> path = new ArrayList<>();
        return f(0, n, arr, k, path, 0);
    }

    private static int f(int i, int n, int[] arr, int k, List<Integer> path, int x) {
        if (i == n) {
            int min = Integer.MAX_VALUE;
            if (path.size() == k) {
                for (int num : path) {
                    min = Math.min(min, num);
                }
                return min;
            }
            return min;
        } else {
            int ans = Integer.MAX_VALUE;
            for (int j = i; j < n; j++) {

                x ^= arr[j];
                path.add(x);
                ans = Math.min(ans, f(j + 1, n, arr, k, path, x));
                path.remove(path.size() - 1);
            }
            return ans;
        }
    }

    public static void main(String[] args) {
        int[] arr = new int[] {
            2,3,3,2
        };
        int k = 3;
        System.out.println(minXor(arr, k));
    }
}
