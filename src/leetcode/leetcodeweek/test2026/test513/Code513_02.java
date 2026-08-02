package leetcode.leetcodeweek.test2026.test513;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2026/8/2 10:12
 * https://leetcode.cn/contest/weekly-contest-513/problems/count-subarrays-with-even-odd-ratio-i/
 */
public class Code513_02 {
    public static int countRatioSubarrays(int[] nums, int a, int b) {
        int n = nums.length;
        int ans = 0;
        double limit = (double) a / b;
        double odd = 0, even = 0;
        for (int l = 0, r = -1; r < n; r++) {
            while (r + 1 < n && (odd == 0 || even / odd <= limit)) {
                if (nums[r + 1] % 2 == 0) {
                    even++;
                } else {
                    odd++;
                }
                r++;
                if (odd > 0 && even / odd <= limit) {
                    ans++;
                }
            }
            while (l< n && (odd == 0 || even / odd > limit)) {
                if (nums[l] % 2 == 0) {
                    even--;
                } else {
                    odd--;
                }
                l++;
                if (odd > 0 && even / odd <= limit) {
                    ans++;
                }
            }
        }
        return ans;
    }


    public static int countRatioSubarrays1(int[] nums, int a, int b) {
        int n = nums.length;
        int ans = 0;
        double limit = (double) a / b;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                double odd = 0, even = 0;
                for (int k = i; k <= j; k++) {
                    if (nums[k] % 2 == 0) {
                        even++;
                    } else {
                        odd++;
                    }
                }
                if (odd > 0 && even / odd <= limit) {
                    ans++;
                }
            }
        }
        return ans;
    }

    public static void main(String[] args) {
//        int[] arr = {304,979,652,115};
//        int a = 182;
//        int b = 922;
        int[] arr = {1,2,1,2};
        int a = 3;
        int b = 2;
        System.out.println(countRatioSubarrays(arr, a, b));
    }
}
