package leetcode.leetcodeweek.test2026.test494;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2026/3/22 10:02
 * https://leetcode.cn/problems/count-good-subarrays/solutions/3933380/mo-ban-logtrick-ji-lu-mei-ge-yuan-su-de-otgcv/
 */
public class Code494_04 {
    public long countGoodSubarrays(int[] nums) {
        List<int[]> orLeft = new ArrayList<>();
        Map<Integer, Integer> last = new HashMap<>();
        long ans = 0;
        for (int i = 0; i < nums.length; i++) {
            int x = nums[i];
            last.put(x, i);
            for (int[] p : orLeft) {
                p[0] |= x;
            }
            orLeft.add(new int[] {x, i});
            int m = 1;
            for (int j = 1; j < orLeft.size(); j++) {
                if (orLeft.get(j)[0] != orLeft.get(j - 1)[0]) {
                    orLeft.set(m++, orLeft.get(j));
                }
            }
            orLeft.subList(m, orLeft.size()).clear();
            for (int k = 0; k < m; k++) {
                int orVal = orLeft.get(k)[0];
                int left = orLeft.get(k)[1];
                int right = k < m - 1 ? orLeft.get(k + 1)[1] - 1 : i;
                int j = last.getOrDefault(orVal, -1);
                if (j >= left) {
                    ans += Math.min(right, j) - left + 1;
                }
            }
        }
        return ans;
    }
}
