package leetcode.leetcodeweek.test2025.test479;

import java.util.Arrays;
import java.util.Comparator;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/12/7 10:00
 * https://leetcode.cn/contest/weekly-contest-479/problems/sort-integers-by-binary-reflection/
 */
public class Code479_01 {
    public static int[] sortByReflection(int[] nums) {
        int n = nums.length;
        int[][] arr = new int[n][2];
        for (int i = 0; i < n; i++) {
            arr[i][0] = nums[i];
            arr[i][1] = reverse(nums[i]);
        }
        Arrays.sort(arr, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                if (o1[1] != o2[1]) {
                    return o1[1] - o2[1];
                }
                return o1[0] - o2[0];
            }
        });
        for (int i = 0; i < n; i++) {
            nums[i] = arr[i][0];
        }
        return nums;
    }

    public static int reverse(int num) {
        // 8 / 2 = 4   0
        // 4 / 2 = 2   0
        // 2 / 2 = 1   0
        int ans = 0;
        while (num > 0) {
            ans = ans * 2 + num % 2;
            num /= 2;
        }
        return ans;
    }

    public static void main(String[] args) {
        int[] arr = {3,6,5,8};
        System.out.println(Arrays.toString(sortByReflection(arr)));
    }
}
