package leetcode.study.leetcode401_600;

import java.util.TreeMap;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/8/20 19:53
 * https://leetcode.cn/problems/132-pattern/
 */
public class Leetcode456Find132pattern {
    public static int MAXN = 200001;
    public static int[] stack = new int[MAXN];
    public static int r;
    public static boolean find132pattern(int[] nums) {
        int n = nums.length, r = 0;
        if (n < 3) {
            return false;
        }
        stack[r++] = n - 1;
        int k = Integer.MIN_VALUE;
        for(int i = n - 2; i >= 0; i--) {
            if (nums[i] < k) {
                return true;
            }
            while (r > 0 && nums[i] > nums[stack[r - 1]]) {
                k = nums[stack[r - 1]];
                r--;
            }
            if (nums[i] > k) {
                stack[r++] = i;
            }
        }
        return false;
    }

    public static boolean find132pattern1(int[] nums) {
        // 0,1,2,3
        // 3,1,4,2
        // 1 1
        // 2 3
        // 3 0
        // 4 2
        int n = nums.length;
        if (n < 3) {
            return false;
        }
        int leftMin = nums[0];
        TreeMap<Integer, Integer> map = new TreeMap<>();
        for (int i = 2; i < n; i++) {
            map.put(nums[i], map.getOrDefault(nums[i], 0) + 1);
        }
        for (int j = 1; j < n - 1; j++) {
            if (leftMin < nums[j]) {
                Integer k = map.ceilingKey(leftMin + 1);
                if (k != null && k < nums[j]) {
                    return true;
                }
            }
            leftMin = Math.min(leftMin, nums[j]);
            if (map.get(nums[j + 1]) == 1) {
                map.remove(nums[j + 1]);
            } else {
                map.put(nums[j + 1], map.get(nums[j + 1]) - 1);
            }
        }
        return false;
    }
}
