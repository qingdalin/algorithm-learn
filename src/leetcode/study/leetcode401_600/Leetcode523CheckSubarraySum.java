package leetcode.study.leetcode401_600;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/9/21 15:54
 * https://leetcode.cn/problems/continuous-subarray-sum/description/
 */
public class Leetcode523CheckSubarraySum {
    public boolean checkSubarraySum(int[] nums, int k) {
        int n = nums.length;
        if (n < 2) {
            return false;
        }
        Map<Integer, Integer> map = new HashMap<>();
        map.put(0, -1);
        int mod = 0;
        for (int i = 0; i < n; i++) {
            mod = (mod + nums[i]) % k;
            if (map.containsKey(mod)) {
                int idx = map.get(mod);
                if (i - idx >= 2) {
                    return true;
                }
            } else {
                map.put(mod, i);
            }
        }
        return false;
    }
}
