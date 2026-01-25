package leetcode.leetcodeweek.test2026.test486;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2026/1/25 9:40
 * https://leetcode.cn/contest/weekly-contest-486/problems/minimum-prefix-removal-to-make-array-strictly-increasing/
 */
public class Code486_01 {
    public static int minimumPrefixLength(int[] nums) {
        int n = nums.length;
        int i = n - 1;
        for (; i > 0;) {
            if (nums[i] > nums[i - 1]) {
                i--;
            } else {
                break;
            }
        }
        return i;
    }

    public static void main(String[] args) {
        int[] arr = {1,2,3,4};
        System.out.println(minimumPrefixLength(arr));
    }
}
