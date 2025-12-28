package leetcode.study.leetcode401_600;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/9/21 16:10
 * https://leetcode.cn/problems/contiguous-array/
 */
public class Leetcode525FindMaxLength {
    public static int findMaxLength(int[] nums) {
        Map<Integer, Integer> map = new HashMap<>();
        int n = nums.length;
        int[] sum = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            sum[i] = sum[i - 1] + (nums[i - 1] == 0 ? -1 : 1);
        }
        map.put(0, 0);
        int ans = 0;
        for (int i = 1; i <= n; i++) {
            if (map.containsKey(sum[i])) {
                ans = Math.max(ans, i - map.get(sum[i]));
            } else {
                map.put(sum[i], i);
            }
        }
        return ans;
    }

    public static void main(String[] args) {
        int[] arr = {0,1,1,1,1,1,0,0,0};
        System.out.println(findMaxLength(arr));
    }
}
