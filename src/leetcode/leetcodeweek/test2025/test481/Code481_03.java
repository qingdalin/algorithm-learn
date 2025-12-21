package leetcode.leetcodeweek.test2025.test481;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.BiFunction;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/12/21 10:10
 * https://leetcode.cn/contest/weekly-contest-481/problems/minimum-swaps-to-avoid-forbidden-values/
 *
 */
public class Code481_03 {
    public static int minSwaps(int[] nums, int[] forbidden) {
        int n = nums.length;
        Map<Integer, Integer> total = new HashMap<>();
        for (int num : nums) {
            total.merge(num, 1, Integer::sum);
        }
        Map<Integer, Integer> cnt = new HashMap<>();
        int k = 0;
        int mx = 0;
        for (int i = 0; i < n; i++) {
            int x = forbidden[i];
            int c = total.merge(x, 1, Integer::sum);
            if (c > n) {
                return -1;
            }
            if (x == nums[i]) {
                k++;
                c = cnt.merge(x, 1, Integer::sum);
                mx = Math.max(mx, c);
            }
        }
        return Math.max((k + 1) / 2, mx);
    }

    public static int minSwaps1(int[] nums, int[] forbidden) {
        int cnt = 0;
        int n = nums.length;
        Set<Integer> set1 = new HashSet<>();
        Set<Integer> set2 = new HashSet<>();
        for (int i = 0; i < n; i++) {
            if (nums[i] == forbidden[i]) {
                set1.add(nums[i]);
                cnt++;
            } else {
                set2.add(forbidden[i]);
            }
        }
        for (Integer num : set1) {
            if (set2.contains(num)) {
                return -1;
            }
        }
        return (cnt + 1) / 2;
    }

    public static void main(String[] args) {
        int[] nums = {4,6,6,5};
        int[] forbidden = {4,6,5,5};
        System.out.println(minSwaps(nums, forbidden));
    }
    // 相同的位置大于等于剩余的位置就不能交换成功
    // 4 6 6 5
    // 4 6 5 5
    // 1 1 2 4
    // 1 1 1 6
    // 1 4 2 1
    // 1 2 3
    // 1 2 3
    // 2 1 3
    // 2 3 1
}
