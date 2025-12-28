package leetcode.leetcodeweek.test2025.test462;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/8/10 9:00
 * https://leetcode.cn/contest/weekly-contest-462/problems/maximum-k-to-sort-a-permutation/
 */
public class Code462_02 {
    public static int sortPermutation(int[] nums) {
        int n = nums.length;
        int k = -1;
        int ans = (1 << 30) - 1;
        for (int i = 0; i < n; i++) {
            if (nums[i] == i) {
                continue;
            }
            ans &= i;
            k = 0;
        }
        return k == -1 ? 0 : ans;
    }

    public static void main(String[] args) {
//        int[] arr = new int[] {0,3,2,1};
        int[] arr = new int[] {0,1,3,2};
        System.out.println(sortPermutation(arr));
    }
}
