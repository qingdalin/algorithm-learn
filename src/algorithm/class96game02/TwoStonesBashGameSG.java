package algorithm.class96game02;

import java.util.Arrays;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2024/7/31 19:44
 * // 两堆石头的巴什博弈
 * // 有两堆石头，数量分别为a、b
 * // 两个人轮流拿，每次可以选择其中一堆石头，拿1~m颗
 * // 拿到最后一颗石子的人获胜，根据a、b、m返回谁赢
 * // 来自真实大厂笔试，没有在线测试，对数器验证
 */
public class TwoStonesBashGameSG {
    public static int MAXN = 101;
    public static String[][][] dp = new String[MAXN][MAXN][MAXN];

    public static String win1(int a, int b, int m) {
        if (m >= Math.max(a, b)) {
            // 假设a=2,b=1，m=3
            // 先手在b拿一个，保证a和b石子数量相等，模仿后者即可获胜
            return a != b ? "先手" : "后手";
        }
        if (a == b) {
            return "后手";
        }
        if (dp[a][b][m] != null) {
            return dp[a][b][m];
        }
        // 默认后手胜
        String ans = "后手";
        for (int pick = 1; pick <= Math.min(a, m); pick++) {
            if (win1(a - pick, b, m).equals("后手")) {
                // 后续过程的后手就是当前的先手，后续过程返回后手赢，就是当前的先手赢
                ans = "先手";
            }
            if (ans.equals("先手")) {
                break;
            }
        }
        for (int pick = 1; pick <= Math.min(b, m); pick++) {
            if (win1(a, b - pick, m).equals("后手")) {
                // 后续过程的后手就是当前的先手，后续过程返回后手赢，就是当前的先手赢
                ans = "先手";
            }
            if (ans.equals("先手")) {
                break;
            }
        }
        dp[a][b][m] = ans;
        return ans;
    }

    public static String win2(int a, int b, int m) {
        int n = Math.max(a , b);
        int[] sg = new int[n + 1];
        boolean[] appear = new boolean[m + 1];
        for (int i = 1; i <= n; i++) {
            Arrays.fill(appear, false);
            for (int j = 1; j <= m && i - j >= 0; j++) {
                appear[sg[i - j]] = true;
            }
            for (int s = 0; s <= m; s++) {
                if (!appear[s]) {
                    sg[i] = s;
                }
            }
        }
        for (int i = 0; i <= n; i++) {
            System.out.println("sg[" + i + "] = " + sg[i]);
        }
        return (sg[b] ^ sg[a]) != 0 ? "先手" : "后手";
    }

    public static String win3(int a, int b, int m) {
        return a % (m + 1) != b % (m + 1) ? "先手" : "后手";
    }

    public static void main(String[] args) {
//        System.out.println("测试开始");
//        for (int i = 0; i < 2000; i++) {
//            int a = (int) (Math.random() * MAXN);
//            int b = (int) (Math.random() * MAXN);
//            int m = (int) (Math.random() * MAXN);
//            String ans1 = win1(a, b, m);
//            String ans2 = win2(a, b, m);
//            String ans3 = win3(a, b, m);
//            if (!ans1.equals(ans2) || !ans2.equals(ans3)) {
//                System.out.println("出错了");
//            }
//        }
//        System.out.println("测试结束");
        win2(50, 54, 5);
    }
}
