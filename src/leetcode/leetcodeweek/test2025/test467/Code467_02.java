package leetcode.leetcodeweek.test2025.test467;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/9/14 8:30
 * https://leetcode.cn/contest/weekly-contest-467/problems/maximize-sum-of-at-most-k-distinct-elements/description/
 */
public class Code467_02 {
    public static int[] maxKDistinct(int[] nums, int k) {
        Set<Integer> set = new HashSet<>();
        Arrays.sort(nums);
        int n = nums.length;
        List<Integer> list = new ArrayList<>();
        for(int i = n - 1; i >= 0; i--) {
            if (set.contains(nums[i])) {
                continue;
            }
            set.add(nums[i]);
            list.add(nums[i]);
            if (list.size() == k) {
                break;
            }
        }
        int[] ans = new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
            ans[i] = list.get(i);
        }
        return ans;
    }
}
