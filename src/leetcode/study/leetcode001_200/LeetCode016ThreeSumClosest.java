package leetcode.study.leetcode001_200;

import java.util.Arrays;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/4/9 20:20
 * https://leetcode.cn/problems/3sum-closest/
 */
public class LeetCode016ThreeSumClosest {
    public static int threeSumClosest1(int[] nums, int target) {
        int n = nums.length;
        int diff = Integer.MAX_VALUE;
        int ans = Integer.MAX_VALUE;
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                for (int k = j + 1; k < n; k++) {
                    int sum = nums[i] + nums[j] + nums[k];
                    if (Math.abs(target - sum) < diff) {
                        diff = Math.abs(target - sum);
                        ans = sum;
                    }
                }
            }
        }
        return ans;
    }

    public static int threeSumClosest(int[] nums, int target) {
        Arrays.sort(nums);
        int n = nums.length;
        int diff = Integer.MAX_VALUE;
        int ans = Integer.MIN_VALUE;
        for (int i = 0; i < n; i++) {
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }
            int l = i + 1;
            int r = n - 1;
            while (l < r) {
                int sum = nums[i] + nums[l] + nums[r];
                if (sum == target) {
                    return sum;
                } else if (sum > target){
                    if (sum - target < diff) {
                        diff = sum - target;
                        ans = sum;
                    }
                    r--;
                } else {
                    if (target - sum < diff) {
                        diff = target - sum;
                        ans = sum;
                    }
                    l++;
                }
            }
        }
        return ans;
    }

    public static void main(String[] args) {
        // int[] nums = new int[] {1,1,1,0};
        int[] nums = new int[] {10,20,30,40,50,60,70,80,90};
        System.out.println(threeSumClosest(nums, -100));
    }
}
