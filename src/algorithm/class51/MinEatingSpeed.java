package algorithm.class51;

/**
 * @author 汪大鹏
 * @version 1.0
 * @date 2024-03-03 15:53
 * https://leetcode.cn/problems/koko-eating-bananas/
 */
public class MinEatingSpeed {
    public int minEatingSpeed(int[] piles, int h) {
        int l = 1;
        int r = 0;
        for (int pile : piles) {
            r = Math.max(r, pile);
        }
        int m = 0;
        int ans = 0;
        while (l <= r) {
            m = (l + r) / 2;
            if (f(piles, m) <= h) {
                ans = m;
                r = m -1;
            } else {
                l = m + 1;
            }
        }
        return ans;
    }
    public long f (int[] piles, int speed) {
        long ans = 0;
        for (int pile : piles) {
            ans += (pile + speed - 1) / speed;
        }
        return ans;
    }
}
