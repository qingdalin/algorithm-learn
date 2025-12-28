package leetcode.study.leetcode401_600;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/9/23 19:42
 * https://leetcode.cn/problems/k-diff-pairs-in-an-array/
 */
public class Leetcode532FindPairs {
    public static int findPairs1(int[] nums, int k) {
        int n = nums.length, cnt = 0;
        if (k == 0) {
            cnt = getCnt(nums, cnt);
        } else {
            int s = 0;
            Arrays.sort(nums);
            for (int i = 1; i < n; i++) {
                if (nums[s] != nums[i]) {
                    nums[++s] = nums[i];
                }
            }
            for (int i = 0; i <= s; i++) {
                for (int j = i + 1; j <= s; j++) {
                    if (Math.abs(nums[i] - nums[j]) == k) {
                        cnt++;
                    }
                }
            }
        }
        return cnt;
    }

    private static int getCnt(int[] nums, int cnt) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int num : nums) {
            int val = map.getOrDefault(num, 0) + 1;
            map.put(num, val);
        }
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            if (entry.getValue() > 1) {
                cnt++;
            }
        }
        return cnt;
    }

    public static int findPairs(int[] nums, int k) {
        int n = nums.length, cnt = 0;
        int s = 0;
        Map<Integer, Integer> map = new HashMap<>();
        for (int num : nums) {
            int val = map.getOrDefault(num, 0) + 1;
            map.put(num, val);
        }
        Arrays.sort(nums);
        for (int i = 1; i < n; i++) {
            if (nums[s] != nums[i]) {
                nums[++s] = nums[i];
            }
        }
        for (int i = 0; i <= s; i++) {
            if (k == 0) {
                if (map.containsKey(nums[i])) {
                    if (map.get(nums[i]) > 1) {
                        cnt++;
                    }
                }
            } else {
                if (map.containsKey(nums[i] + k)) {
                    cnt++;
                }
            }
        }
        return cnt;
    }

    public static void main(String[] args) {
        int[] arr = {-1,-1,1,1};
        int k = 0;
        System.out.println(findPairs(arr, k));
    }
}
