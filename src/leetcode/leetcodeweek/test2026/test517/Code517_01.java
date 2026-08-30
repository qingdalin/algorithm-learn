package leetcode.leetcodeweek.test2026.test517;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2026/8/30 9:49
 * https://leetcode.cn/contest/weekly-contest-517/problems/count-integers-appearing-in-a-single-block/description/
 */
public class Code517_01 {
    public static int countSpecialIntegers(int[] nums) {
        int n = nums.length;
        int[] visit = new int[101];
        int ans = 0;
        for (int i = 0; i < n; i++) {
            if (visit[nums[i]] == 1 || visit[nums[i]] == 2) {
                if (visit[nums[i]] == 1) {
                    visit[nums[i]] = 2;
                    ans--;
                }
                continue;
            }
            visit[nums[i]] = 1;
            while (i + 1 < n && nums[i] == nums[i + 1]) {
                i++;
            }
            ans++;
        }
        return ans;
    }

    public static void main(String[] args) {
        // int[] arr = {1,2,2,1};
        // int[] arr = {3,3,1,2,2,1};
//        int[] arr = {22};
        int[] arr = {66,65,66,66,66};
        System.out.println(countSpecialIntegers(arr));
    }
}
