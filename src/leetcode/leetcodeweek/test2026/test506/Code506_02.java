package leetcode.leetcodeweek.test2026.test506;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2026/6/14 9:15
 * https://leetcode.cn/contest/weekly-contest-506/problems/frequency-balance-subarray/description/
 */
public class Code506_02 {
    public static int getLength(int[] nums) {
        int ans = 1;
        int n = nums.length;
        for (int i = 0; i < n; i++) {
            Map<Integer, Integer> cnt = new HashMap<>();
            Map<Integer, Integer> cc = new HashMap<>();
            for (int j = i; j < n; j++) {
                int x = nums[j];
                Integer c = cnt.get(x);
                if (c != null) {
                    if (cc.merge(c, -1, Integer::sum) == 0) {
                        cc.remove(c);
                    }
                }
                c = cnt.merge(x, 1, Integer::sum);
                cc.merge(c, 1, Integer::sum);
                if (cnt.size() == 1) {
                    ans = Math.max(ans, j - i + 1);
                } else if (cc.size() == 2) {
                    Iterator<Integer> it = cc.keySet().iterator();
                    Integer c1 = it.next();
                    Integer c2 = it.next();
                    if (Math.min(c1, c2) * 2 == Math.max(c1, c2)) {
                        ans = Math.max(ans, j - i + 1);
                    }
                }
            }
        }
        return ans;
    }

    public static int getLength1(int[] nums) {
        Map<Integer, Integer> map = new HashMap<>();
        int ans = 1;
        int n = nums.length;
        for (int i = 0; i < n; i++) {
            int cur = 1, maxCnt = 0, minCnt = Integer.MAX_VALUE, max = 0, min = Integer.MAX_VALUE;
            map.clear();
            for (int j = i; j < n; j++) {
                map.put(nums[j], map.getOrDefault(nums[j], 0) + 1);
                int cnt = map.get(nums[j]);
                if (cnt >= maxCnt || max == nums[j]) {
                    max = nums[j];
                    maxCnt = cnt;
                }
                if (cnt < minCnt || min == nums[j]) {
                    min = nums[j];
                    minCnt = cnt;
                }
                if ((maxCnt != 0 && maxCnt == minCnt * 2) || map.size() == 1) {
                    cur = Math.max(cur, j - i + 1);
                }
            }
            ans = Math.max(ans, cur);
            map.put(nums[i], map.get(nums[i]) - 1);
            if (map.get(nums[i]) == 0) {
                map.remove(nums[i]);
            }
        }
        return ans;
    }

    public static void main(String[] args) {
        int[] arr = {1,2,2,1,2,3,3,3};
//        int[] arr = {5,5,5,5};
//        int[] arr = {1,2,3,4};
//        int[] arr = {1,2,1,1,2};
        System.out.println(getLength(arr));
    }
}
