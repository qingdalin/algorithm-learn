package leetcode.study.leetcdoe201_400;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/7/12 19:39
 * https://leetcode.cn/problems/contains-duplicate-ii/
 */
public class Leetcode219ContainsNearbyDuplicate {
    public static boolean containsNearbyDuplicate(int[] arr, int k) {
        Set<Integer> set = new HashSet<>();
        int n = arr.length;
        for (int i = 0; i < n; i++) {
            if (!set.add(arr[i])) {
                return true;
            }
            // 1 2 3 1
            // 0 1 2 3
            // k = 2
            if (i - k >= 0) {
                set.remove(arr[i - k]);
            }
        }
        return false;
    }

    public static boolean containsNearbyDuplicate1(int[] arr, int k) {
        int n = arr.length;
        int[][] tmp = new int[n][2];
        for (int i = 0; i < n; i++) {
            tmp[i][0] = arr[i];
            tmp[i][1] = i;
        }
        Arrays.sort(tmp, (a, b) -> a[0] != b[0] ? a[0] - b[0] : b[1] - a[1]);
        for (int i = 0; i < n - 1; i++) {
            if (tmp[i][0] == tmp[i + 1][0] && tmp[i][1] - tmp[i + 1][1] <= k) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        int[] arr = new int[] {1,2,3,1,2,3};
        int k = 2;
        System.out.println(containsNearbyDuplicate(arr, k));
    }
}
