package leetcode.study.leetcode401_600;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/9/23 21:19
 * https://leetcode.cn/problems/single-element-in-a-sorted-array/
 */
public class Leetcode540SingleNonDuplicate {
    public static int singleNonDuplicate1(int[] nums) {
        int ans = 0;
        for (int num : nums) {
            ans ^= num;
        }
        return ans;
    }

    public static int singleNonDuplicate(int[] nums) {
        int ans = 0;
        int n = nums.length;
        int l = 0, r = n - 1, mid;
        // 1,1,2,3,3,4,4,8,8
        // 0 1 2 3 4 5 6 7 8
        while (l <= r) {
            mid = (l + r) / 2;
            if (nums[mid] == nums[mid + 1]) {
                l = mid + 1;
            } else {
                r = mid;
            }
        }
        return nums[l];
    }

    public static void main(String[] args) {
        int[] arr = {1,1,2,3,3,4,4,8,8};
        System.out.println(singleNonDuplicate(arr));
    }
}
