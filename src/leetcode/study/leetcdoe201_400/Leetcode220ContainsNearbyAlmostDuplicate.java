package leetcode.study.leetcdoe201_400;

import java.util.TreeSet;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/7/12 20:51
 * https://leetcode.cn/problems/contains-duplicate-iii/
 */
public class Leetcode220ContainsNearbyAlmostDuplicate {
    public static boolean containsNearbyAlmostDuplicate(int[] arr, int k, int v) {
        TreeSet<Integer> set = new TreeSet<>();
        int n = arr.length;
        for (int i = 0; i < n; i++) {
            Integer ceiling = set.ceiling(arr[i] - v);
            if (ceiling != null && ceiling <= arr[i] + v) {
                return true;
            }
            set.add(arr[i]);
            // 1 2 3 1
            // 0 1 2 3
            // k = 2
            if (i - k >= 0) {
                set.remove(arr[i - k]);
            }
        }
        return false;
    }

    public static boolean containsNearbyAlmostDuplicate1(int[] arr, int k, int v) {
        TreeSet<Integer> set = new TreeSet<>();
        int n = arr.length;
        for (int i = 0; i < n; i++) {
            Integer floor = set.floor(arr[i] - v);
            if (floor != null && floor >= arr[i] - v) {
                return true;
            }
            Integer ceiling = set.ceiling(arr[i] + v);
            if (ceiling != null && ceiling <= arr[i] + v) {
                return true;
            }
            set.add(arr[i]);
            // 1 2 3 1
            // 0 1 2 3
            // k = 2
            if (i - k >= 0) {
                set.remove(arr[i - k]);
            }
        }
        return false;
    }

    public static void main(String[] args) {
        int[] arr = new int[] {1,5,9,1,5,9};
        int k = 2;
        int v = 3;
        System.out.println(containsNearbyAlmostDuplicate(arr, k, v));
    }
}
