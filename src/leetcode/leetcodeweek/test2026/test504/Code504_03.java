package leetcode.leetcodeweek.test2026.test504;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2026/5/31 10:10
 * https://leetcode.cn/problems/maximum-number-of-items-from-sale-ii/description/
 */
public class Code504_03 {
    public int maximumSaleItems(int[][] items, int budget) {
        int n = items.length;
        int[] cntFactor = new int[n + 1];
        int minPrice = Integer.MAX_VALUE;
        for (int[] p : items) {
            cntFactor[p[0]]++;
            minPrice = Math.min(minPrice, p[1]);
        }
        int[] cntMulti = new int[n + 1];
        List<int[]> a = new ArrayList<>();
        for (int[] p : items) {
            int factor = p[0], price = p[1];
            if (price >= minPrice * 2) {
                continue;
            }
            if (cntMulti[factor] == 0) {
                for (int j = factor; j <= n; j += factor) {
                    cntMulti[factor] += cntFactor[j];
                }
            }
            if (cntMulti[factor] > 1) {
                a.add(new int[] {price, cntMulti[factor] - 1});
            }
        }
        a.sort((p, q) -> p[0] - q[0]);
        int ans = 0;
        for (int[] p : a) {
            int price = p[0], cnt = p[1];
            if (budget < price) {
                break;
            }
            int c = Math.min(cnt, budget / price);
            budget -= price * c;
            ans += c * 2;
        }
        return ans + budget / minPrice;
    }
}
