package leetcode.leetcodeweek.test2025.test479;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/12/7 10:00
 * https://leetcode.cn/contest/weekly-contest-479/problems/total-score-of-dungeon-runs/
 */
public class Code479_03 {
    public static int MAXN = 100001;
    public static int n;
    public static long[] dsum = new long[MAXN];
    public static long totalScore(int hp, int[] damage, int[] requirement) {
        build(damage, requirement);
        long ans = 0;
        for (int i = 1; i <= n; i++) {
            int l = i, r = n;
            long tmp = 0;
            while (l <= r) {
                int mid = (l + r) >> 1;
                // long curHp = (long) hp - (dsum[mid - 1] - dsum[i - 1]);
                long curHp = dsum[i] + requirement[i - 1] - hp;
                if (curHp - dsum[mid] <= 0) {
                    tmp = mid - i + 1;
                    l = mid + 1;
                } else {
                    r = mid - 1;
                }
            }
            ans += tmp;
        }
        return ans;
    }

    private static void build(int[] damage, int[] requirement) {
        n = damage.length;
        for (int i = 0; i < n; i++) {
            dsum[i + 1] = dsum[i] + damage[i];
        }
    }

    public static void main(String[] args) {
        int hp = 11;
        int[] d = {3,6,7};
        int[] r = {4,2,5};
        System.out.println(totalScore(hp, d, r));
    }
}
