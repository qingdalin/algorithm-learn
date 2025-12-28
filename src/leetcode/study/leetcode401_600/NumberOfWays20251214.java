package leetcode.study.leetcode401_600;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/12/14 18:31
 */
public class NumberOfWays20251214 {
    public static int MOD = 1000000007;
    public int numberOfWays(String str) {
        int n = str.length();
        long ans = 1;
        int cnt = 0, lastS = 0;
        for (int i = 0; i < n; i++) {
            if (str.charAt(i) == 'S') {
                cnt++;
                if (cnt >= 3 && cnt % 2 > 0) {
                    ans = ans * (i - lastS) % MOD;
                }
                lastS = i;
            }
        }
        if (cnt == 0 || cnt % 2 != 0) {
            return 0;
        }
        return (int) ans;
    }
}
