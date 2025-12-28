package leetcode.leetcodeweek.test2025.test478;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/11/30 9:46
 * https://leetcode.cn/contest/weekly-contest-478/problems/minimum-absolute-distance-between-mirror-pairs/
 */
public class Code478_03 {
    public static int minMirrorPairDistance(int[] nums) {
        int n = nums.length;
        Map<Integer, Integer> map = new HashMap<>();
        int ans = Integer.MAX_VALUE;
        for (int i = 0, cur; i < n; i++) {
            cur = reverse(nums[i]);
            if (map.containsKey(nums[i])) {
                ans = Math.min(ans, i - map.get(nums[i]));
            }
            map.put(cur, i);
        }
        return ans == Integer.MAX_VALUE ? -1 : ans;
    }

    public static int reverse(int num) {
        int ans = 0;
        while (num > 0) {
            ans = ans * 10 + (num % 10);
            num /= 10;
        }
        return ans;
    }

    public static void main(String[] args) {
        int[] nums = {21,120};
        System.out.println(minMirrorPairDistance(nums));
    }
}
