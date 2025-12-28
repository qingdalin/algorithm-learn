package leetcode.study.leetcode401_600;

import java.util.Arrays;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/9/26 20:09
 * https://leetcode.cn/problems/valid-triangle-number/?envType=daily-question&envId=2025-09-26
 */
public class TriangleNumber20250926 {
    public static void main(String[] args) {
//        int[] arr = {4,2,3,4};
        int[] arr = {2,2,3,4};
        System.out.println(triangleNumber(arr));
    }
    public static int triangleNumber2(int[] nums) {
        int n = nums.length;
        int cnt = 0;
        Arrays.sort(nums);
        for (int k = 2; k < n; k++) {
            int c = nums[k];
            int i = 0, j = k - 1;
            while (i < j) {
                if (nums[i] + nums[j] > c) {
                    cnt += j - i;
                    j--;
                } else {
                    i++;
                }
            }
        }
        return cnt;
    }

    public static int triangleNumber(int[] nums) {
        int n = nums.length;
        int cnt = 0;
        Arrays.sort(nums);
        for (int k = n - 1; k >= 0; k--) {
            int c = nums[k];
            if (nums[0] + nums[1] > c) {
                cnt += (k + 1) * k * (k - 1) / 6;
                break;
            }
            if (nums[k - 1] + nums[k - 2] < c) {
                continue;
            }
            int i = 0, j = k - 1;
            while (i < j) {
                if (nums[i] + nums[j] > c) {
                    cnt += j - i;
                    j--;
                } else {
                    i++;
                }
            }
        }
        return cnt;
    }

    private static int f(int l, int r, int[] nums) {
        int left = l, right = r, mid, ans = -1;
        while (left <= right) {
            mid = (left + right) / 2;
            if (nums[mid] + nums[l] > nums[r]) {
                ans = mid;
                // 4 5 6 7 10
                // 0 1 2 3 4
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        return ans;
    }

    public static int triangleNumber1(int[] nums) {
        int n = nums.length;
        int cnt = 0;
        Arrays.sort(nums);
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                for (int k = j + 1; k < n; k++) {
                    if (nums[i] + nums[j] > nums[k]) {
                        cnt++;
                    } else {
                        break;
                    }
                }
            }
        }
        return cnt;
    }
}
