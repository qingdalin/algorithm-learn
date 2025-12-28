package leetcode.leetcodeweek.test2025.test482;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/12/28 9:53
 * https://leetcode.cn/contest/weekly-contest-482/problems/minimum-cost-to-acquire-required-items/
 */
public class Code482_02 {
    public static long minimumCost(int cost1, int cost2, int costBoth, int need1, int need2) {
        long ans = 0;
        if (cost1 + cost2 <= costBoth) {
            ans = (long) need1 * cost1 + (long) need2 * cost2;
        } else {
            if (need1 <= need2) {
                ans = (long) costBoth * need1;
                ans += (long) Math.min(costBoth, cost2) * (need2 - need1);
            } else {
                ans = (long) costBoth * need2;
                ans += (long) Math.min(costBoth, cost1) * (need1 - need2);
            }
        }
        return ans;
    }

    public static void main(String[] args) {
        int cost1 = 50, cost2 = 55, costBoth = 72, need1 = 5, need2 = 3;
        System.out.println(minimumCost(cost1, cost2, costBoth, need1, need2));
    }
}
