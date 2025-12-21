package leetcode.leetcodeweek.test2025.test481;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/12/21 10:10
 * https://leetcode.cn/contest/weekly-contest-481/problems/minimum-deletion-cost-to-make-all-characters-equal/
 */
public class Code481_02 {
    public static long minCost(String str, int[] cost) {
        // 统计所有字符删除的代价，
        Map<Character, Long> map = new HashMap<>();
        char[] s = str.toCharArray();
        int n = s.length;
        long sum = 0;
        long max = Long.MIN_VALUE;
        for (int i = 0; i < n; i++) {
            sum += cost[i];
            map.put(s[i], map.getOrDefault(s[i], 0L) + cost[i]);
            max = Math.max(max, map.get(s[i]));
        }
        return sum - max;
    }

    public static void main(String[] args) {
        String s = "zzzzz";
        int[] cost = {67,67,67,67,67};
        System.out.println(minCost(s, cost));
    }
}
