package leetcode.leetcodeweek.test2026.test491;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2026/3/1 10:24
 * https://leetcode.cn/contest/weekly-contest-491/problems/minimum-cost-to-split-into-ones/
 */
public class Code491_02 {
    public static int MAXN = 501;
    public static int[] arr = new int[MAXN];
    static {
        arr[1] = 0;
        arr[2] = 1;
        arr[3] = 3;
        for (int i = 4; i < MAXN; i++) {
            arr[i] = arr[i - 1] + i - 1;
        }
    }
    public static int minCost(int n) {

        // 5 = 4 + 1 4
        // 5 = 2 + 3 6
        // 6 = 1 + 5 5
        // 6 = 2 + 4 8
//        return arr[n];
        return n * (n - 1) / 2;
    }

    public static void main(String[] args) {
        int n = 4;
        System.out.println(minCost(n));
    }
}
