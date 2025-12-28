package leetcode.study.leetcdoe201_400;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/7/26 17:25
 * https://leetcode.cn/problems/self-crossing/description/
 */
public class Leetcode335IsSelfCrossing {
    public boolean isSelfCrossing(int[] arr) {
        int n = arr.length;
        if (n <= 3) {
            return false;
        }
        for (int i = 3, a, b, c, d; i < n; i++) {
            if (arr[i] >= arr[i - 2] && arr[i - 1] <= arr[i - 3]) {
                return true;
            }
            if (i == 4 && (arr[3] == arr[1] && arr[4] >= arr[2] - arr[0])) {
                return true;
            }
            if (i >= 5 && (arr[i - 3] - arr[i - 5] <= arr[i - 1]
                && arr[i - 1] <= arr[i - 3]
                && arr[i] >= arr[i - 2] - arr[i - 4]
                && arr[i - 2] > arr[i - 4])) {
                return true;
            }
        }
        return false;
    }

    public boolean isSelfCrossing1(int[] arr) {
        int n = arr.length;
        if (n <= 3) {
            return false;
        }
        for (int i = 0, a, b, c, d; i < n; i += 4) {
            a = arr[i];
            if (i + 1 >= n || i + 2 >= n || i + 3 >= n) {
                return false;
            }
            b = arr[i + 1];
            c = arr[i + 2];
            d = arr[i + 3];
            if (a >= c && d >= b) {
                return true;
            }
            if (i == 4 && d == b && arr[i] >= c - a) {
                return true;
            }
        }
        return false;
    }
}
