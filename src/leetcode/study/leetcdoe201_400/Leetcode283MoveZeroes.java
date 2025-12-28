package leetcode.study.leetcdoe201_400;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/7/23 20:27
 * https://leetcode.cn/problems/move-zeroes/
 */
public class Leetcode283MoveZeroes {
    public static void moveZeroes(int[] arr) {
        int n = arr.length;
        for (int i = 0, j = 0; i < n; i++) {
            if (arr[i] != 0) {
                swap(arr, i, j++);
            }
        }
    }

    public static void swap(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

}
