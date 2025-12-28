package leetcode.study.leetcode001_200;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/6/7 14:38
 * https://leetcode.cn/problems/remove-duplicates-from-sorted-array-ii/
 */
public class Leetcode080RemoveDuplicates {
    public static int removeDuplicates(int[] nums) {
        int n = nums.length;
        int cnt = 2;
        for (int i = 2; i < n; i++) {
            // 0 0 1 1 1 2
            // 0 1 2 3 4 6
            //     c     i
            // int[] num = new int[] {0,0,1,1,1,1,2,3,3};
            if (nums[cnt - 2] != nums[i]) {
                nums[cnt] = nums[i];
                cnt++;
            }
        }
        return cnt;
    }

    private static void swap(int[] nums, int i, int j) {
        int tmp = nums[i];
        nums[i] = nums[j];
        nums[j] = tmp;
    }

    public static void main(String[] args) {
        int[] nums = new int[] {1,1,1,2,2,3};
        int[] num = new int[] {0,0,1,1,1,1,2,3,3};
        System.out.println(removeDuplicates(num));
    }
}
