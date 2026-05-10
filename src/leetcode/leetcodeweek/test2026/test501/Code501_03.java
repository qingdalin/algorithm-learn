package leetcode.leetcodeweek.test2026.test501;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2026/5/10 10:12
 * https://leetcode.cn/contest/weekly-contest-501/problems/minimize-array-sum-using-divisible-replacements/
 */
public class Code501_03 {
    public static int MAXN = 100001;
    public static List<Integer>[] divisors = new ArrayList[MAXN];
    public static boolean initialized = false;
    static {
        Arrays.setAll(divisors, a -> new ArrayList<>());
        for (int i = 1; i < MAXN; i++) {
            for (int j = i; j < MAXN; j += i) {
                // i 是j的因子
                divisors[j].add(i);
            }
        }

    }
    public long minArraySum(int[] nums) {
        long ans = 0;
        Map<Integer, Integer> cnt = new HashMap<>();
        for (int x : nums) {
            cnt.merge(x, 1, Integer::sum);
        }
        for (Map.Entry<Integer, Integer> entry : cnt.entrySet()) {
            int x = entry.getKey();
            int c = entry.getValue();
            for (int d : divisors[x]) {
                if (cnt.containsKey(d)) {
                    ans += (long) d * c;
                    break;
                }
            }
        }
        return ans;
    }
}
