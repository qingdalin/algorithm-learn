package leetcode.study.leetcode401_600;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/8/3 13:27
 * https://leetcode.cn/problems/arithmetic-slices/
 */
public class Leetcode413NumberOfArithmeticSlices {
    public static int numberOfArithmeticSlices(int[] nums) {
        int n = nums.length;
        if (n < 3) {
            return 0;
        }
        int ans = 0;
        int diff = nums[1] - nums[0];
        int cnt = 0;
        for (int i = 2; i < n; i++) {
            if (nums[i] - nums[i - 1] == diff) {
                cnt++;
            } else {
                diff = nums[i] - nums[i - 1];
                cnt = 0;
            }
            ans += cnt;
        }
        return ans;
    }

    public static int numberOfArithmeticSlices2(int[] nums) {
        int n = nums.length;
        if (n < 3) {
            return 0;
        }
        int ans = 0;
        for (int l = 0; l < n - 2;) {
            int r = l;
            int diff = nums[l + 1] - nums[l];
            while (r + 1 < n && nums[r + 1] - nums[r] == diff) {
                r++;
            }
            int len = r - l + 1 - 3 + 1;
            ans += (len + 1) * len / 2;
            // 1 2 3 4 5
            l = r;
        }
        return ans;
    }

    public static int numberOfArithmeticSlices1(int[] nums) {
        int n = nums.length;
        if (n < 3) {
            return 0;
        }
        int ans = 0;
        for (int r = 0, l = 0; r < n;l = ++r) {
            int diff = nums[r + 1] - nums[r];
            while (r + 1 < n && nums[r + 1] - nums[r] == diff) {
                r++;
            }
            int len = r - l + 1 - 3 + 1;
            ans += (len + 1) * len / 2;
            // 1 2 3 4 5
        }
        return ans;
    }

    public static void main(String[] args) {
//        int[] arr = new int[] {1, 2, 3, 4};
        int[] arr = new int[] {1, 2, 3, 7, 8 ,9};
        System.out.println(numberOfArithmeticSlices(arr));
    }
}
