package leetcode.leetcodeweek.test2025.test450;

import java.util.Arrays;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/5/18 9:16
 * https://leetcode.cn/contest/weekly-contest-450/problems/minimum-swaps-to-sort-by-digit-sum/
 */
public class Code02 {

    public static int MAXN = 100001;
    public static int n;
    public static int[] father = new int[MAXN];
    public static int minSwaps(int[] nums) {
        n = nums.length;
        for (int i = 0; i < n; i++) {
            father[i] = i;
        }
        int[][] arr = new int[n][3];
        for (int i = 0; i < n; i++) {
            int sum = 0;
            int curNum = nums[i];
            while (curNum > 0) {
                sum += curNum % 10;
                curNum /= 10;
            }
            arr[i][0] = sum;
            arr[i][1] = nums[i];
            arr[i][2] = i;
        }
        Arrays.sort(arr, 0, n, (a, b) -> a[0] != b[0] ? a[0] - b[0] : a[1] - b[1]);
        for (int i = 0; i < n; i++) {
            union(arr[i][2], i);
        }
        int size = 0;
        for (int i = 0; i < n; i++) {
            if (i == father[i]) {
                size++;
            }
        }
        return n - size;
    }

    public static int find(int i) {
        if (father[i] != i) {
            father[i] = find(father[i]);
        }
        return father[i];
    }

    public static void union(int x, int y) {
        int fx = find(x);
        int fy = find(y);
        if (fx != fy) {
            father[fy] = fx;
        }
    }

    public static void main(String[] args) {
        int[] arr = new int[] {18,43,34,16};
        System.out.println(minSwaps(arr));
    }
}
