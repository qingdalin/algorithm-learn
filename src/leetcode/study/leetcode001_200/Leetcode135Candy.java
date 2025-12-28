package leetcode.study.leetcode001_200;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/6/25 20:22
 * https://leetcode.cn/problems/candy/
 */
public class Leetcode135Candy {
    public static int candy(int[] arr) {
        int n = arr.length;
        int cnt = 0;
        int[] left = new int[n];
        for (int i = 0; i < n; i++) {
            if (i > 0 && arr[i] > arr[i - 1]) {
                left[i] = left[i - 1] + 1;
            } else {
                left[i] = 1;
            }
        }
        int right = 0;
        for(int i = n - 1; i >= 0; i--) {
            if (i < n - 1 && arr[i] > arr[i + 1]) {
                right++;
            } else {
                right = 1;
            }
            cnt += Math.max(left[i], right);
        }
        return cnt;
    }
}
