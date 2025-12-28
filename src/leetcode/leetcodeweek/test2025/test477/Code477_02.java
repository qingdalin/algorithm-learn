package leetcode.leetcodeweek.test2025.test477;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/11/23 11:31
 * https://leetcode.cn/contest/weekly-contest-477/problems/find-maximum-balanced-xor-subarray-length/description/
 */
public class Code477_02 {
    public static int maxBalancedSubarray(int[] arr) {
        int n = arr.length;
        int ans = 0;
        int xor = 0;
        int diff = n;
        Map<Long, Integer> map = new HashMap<>();
        map.put((long) xor << 32 | diff, -1);
        for (int i = 0; i < n; i++) {
            xor ^= arr[i];
            if ((arr[i] & 1) == 1) {
                diff++;
            } else {
                diff--;
            }
            long key = (long) xor << 32 | diff;
            if (map.containsKey(key)) {
                ans = Math.max(ans, i - map.get(key));
            } else {
                map.put(key, i);
            }
        }
        return ans;
    }

    public static void main(String[] args) {
        int[] arr = {3,1,3,2,0};
        System.out.println(maxBalancedSubarray(arr));
    }
}
