package leetcode.study.leetcode401_600;

import java.util.Arrays;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/8/6 19:45
 * https://leetcode.cn/problems/fruits-into-baskets-iii/?envType=daily-question&envId=2025-08-06
 */
public class NumOfUnplacedFruits {
    public static int MAXN = 100001;
    public static int MAXB = 401;
    public static int n;
    public static int blen, bnum;
    public static int[] bi = new int[MAXN];
    public static int[] bl = new int[MAXB];
    public static int[] br = new int[MAXB];
    public static int[] maxv = new int[MAXB];
    public static int numOfUnplacedFruits(int[] fruits, int[] baskets) {
        n = fruits.length;
        build(baskets);
        int cnt = 0;
        for (int fruit : fruits) {
            int b;
            int unset = 1;
            for (b = 1; b <= bnum; b++) {
                if (maxv[b] < fruit) {
                    continue;
                }
                int choose = 0;
                maxv[b] = 0;
                for (int i = bl[b]; i <= br[b]; i++) {
                    if (baskets[i - 1] >= fruit && choose == 0) {
                        baskets[i - 1] = 0;
                        choose = 1;
                    }
                    maxv[b] = Math.max(maxv[b], baskets[i - 1]);
                }
                unset = 0;
                break;
            }
            cnt += unset;
        }
        return cnt;
        // 4 2 1 5 3
        // 3 5 4 5 2
        // 4 -> 5
        // 2 -> 3
        // 1 -> 4
        // 5 -> 5
        // 3无法放入
        // 5 6 4 1 2
        // 3 4 5 2 3
        // 5 -> 5
        // 6
        // 4 -> 4
        // 1 -> 3
        // 2 -> 2
        // [ 3 4 2 3]
    }

    private static void build(int[] baskets) {
        blen = (int) Math.sqrt(n);
        bnum = (n + blen - 1) / blen;
        Arrays.fill(maxv, 0, bnum + 1, 0);
        for (int i = 1; i <= n; i++) {
            bi[i] = (i - 1) / blen + 1;
        }
        for (int i = 1; i <= bnum; i++) {
            bl[i] = (i - 1) * blen + 1;
            br[i] = Math.min(i * blen, n);
        }
        for (int i = 1; i <= n; i++) {
            maxv[bi[i]] = Math.max(maxv[bi[i]], baskets[i - 1]);
        }
    }
}
