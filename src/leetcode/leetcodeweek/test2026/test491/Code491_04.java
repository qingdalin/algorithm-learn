package leetcode.leetcodeweek.test2026.test491;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2026/3/1 10:24
 * https://leetcode.cn/contest/weekly-contest-491/problems/count-subarrays-with-k-distinct-integers/
 */
public class Code491_04 {
    public static int MAXN = 100001;
    public static int[] cntArr = new int[MAXN];
    public static int[] kindArr = new int[MAXN];
    public static int cnt, kind, n;
    public static long countSubarrays(int[] nums, int k, int m) {
        return calc(nums, k, k, m) - calc(nums, k + 1, k, m);
    }

    private static long calc(int[] nums, int limit, int k, int m) {
        long ans = 0;
        Map<Integer, Integer> cnt = new HashMap<>();
        int left = 0, gem = 0;
        for (int i = 0; i < nums.length; i++) {
            int c = cnt.merge(nums[i], 1, Integer::sum);
            if (c == m) {
                gem++;
            }
            while (cnt.size() >= limit && gem >= k) {
                int out = nums[left];
                c = cnt.get(out);
                if (c == m) {
                    gem--;
                }
                if (c == 1) {
                    cnt.remove(out);
                } else {
                    cnt.put(out, c - 1);
                }
                left++;
            }
            ans += left;
        }
        return ans;
    }

    public static long countSubarrays1(int[] nums, int k, int m) {
        cnt = kind = 0;
        n = nums.length;
        long ans = 0;
        for (int l = 0, r = -1; r < n; r++) {
            while (r + 1 < n && (kind < k || cntArr[nums[r + 1]] < m)) {
                cntArr[nums[r + 1]]++;
                if (cntArr[nums[r + 1]] == 1) {
                    kind++;
                }
                r++;
            }
            ans++;
            while (l < r && ((kind > k || cntArr[nums[l]] > m))) {
                cntArr[nums[l]]--;
                if (cntArr[nums[l]] == 0) {
                    kind--;
                }
                l++;
            }
        }
        return ans;
    }

    public static void main(String[] args) {
        int[] arr = {3,1,2,4};
        int k = 2, m = 1;
        System.out.println(countSubarrays(arr, k, m));
    }
}
