package algorithm.class96game02;

import java.util.Arrays;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2024/7/31 20:18
 * // 三堆石头拿取斐波那契数博弈
 * // 有三堆石头，数量分别为a、b、c
 * // 两个人轮流拿，每次可以选择其中一堆石头，拿取斐波那契数的石头
 * // 拿到最后一颗石子的人获胜，根据a、b、c返回谁赢
 * // 来自真实大厂笔试，每堆石子的数量在10^5以内
 * // 没有在线测试，对数器验证
 */
public class ThreeStonesPickFibonacci {
    public static int MAXN = 201;
    public static String[][][] dp = new String[MAXN][MAXN][MAXN];
    public static int[] fib = {1, 2, 3, 5, 8, 13, 21, 34, 55, 89, 144};
    public static String win1(int a, int b, int c) {
        if (a + b + c == 0) {
            return "后手";
        }
        if (dp[a][b][c] != null) {
            return dp[a][b][c];
        }
        String ans = "后手";// ans : 赢的是当前的先手，还是当前的后手
        for (int i = 0; i < fib.length; i++) {
            if (fib[i] <= a) {
                if (win1(a - fib[i], b, c).equals("后手")) {
                    // 后续过程的赢家是后续过程的后手
                    // 那就表示当前的先手，通过这个后续过程，能赢
                    ans = "先手";
                    break;
                }
            }
            if (fib[i] <= b) {
                if (win1(a, b - fib[i], c).equals("后手")) {
                    // 后续过程的赢家是后续过程的后手
                    // 那就表示当前的先手，通过这个后续过程，能赢
                    ans = "先手";
                    break;
                }
            }
            if (fib[i] <= c) {
                if (win1(a, b, c - fib[i]).equals("后手")) {
                    // 后续过程的赢家是后续过程的后手
                    // 那就表示当前的先手，通过这个后续过程，能赢
                    ans = "先手";
                    break;
                }
            }
        }
        dp[a][b][c] = ans;
        return ans;
    }

    public static int[] sg = new int[MAXN];
    public static boolean[] appear = new boolean[MAXN];

    public static void build() {
        for (int i = 1; i < MAXN; i++) {
            Arrays.fill(appear, false);
            for (int j = 0; j < fib.length && i - fib[j] >= 0; j++) {
                appear[sg[i - fib[j]]] = true;
            }
            for (int s = 0; s < MAXN; s++) {
                if (!appear[s]) {
                    sg[i] = s;
                    break;
                }
            }
        }
    }

    public static String win2(int a, int b, int c) {
        return (sg[a] ^ sg[b] ^ sg[c]) != 0 ? "先手" : "后手";
    }

    public static void main(String[] args) {
        System.out.println("测试开始");
        build();
        for (int a = 0; a < MAXN; a++) {
            for (int b = 0; b < MAXN; b++) {
                for (int c = 0; c < MAXN; c++) {
                    String ans1 = win1(a, b, c);
                    String ans2 = win2(a, b, c);
                    if (!ans1.equals(ans2)) {
                        System.out.println("出错了");
                    }
                }
            }
        }
        System.out.println("测试结束");
        // 试图找到简洁规律，想通过O(1)的过程就得到sg(x)
        // 于是打印200以内的sg值，开始观察
        // 刚开始有规律，但是在sg(138)之后开始发生异常波动
        // 这道题在考的时候，数据量并没有大到需要O(1)的过程才能通过
        // 那就用build方法计算sg值，不再找寻简洁规律
        // 考试时一切根据题目数据量来决定是否继续优化
        for (int i = 0; i < MAXN; i++) {
            System.out.println("sg(" + i + ") : " + sg[i]);
        }
    }
}
