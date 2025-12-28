package algorithm.class80dp15;

/**
 * @author 汪大鹏
 * @version 1.0
 * @date 2024-06-30 9:37
 * 在 "100 game" 这个游戏中，两名玩家轮流选择从 1 到 10 的任意整数，累计整数和，先使得累计整数和 达到或超过  100 的玩家，即为胜者。
 *
 * 如果我们将游戏规则改为 “玩家 不能 重复使用整数” 呢？
 *
 * 例如，两个玩家可以轮流从公共整数池中抽取从 1 到 15 的整数（不放回），直到累计整数和 >= 100。
 *
 * 给定两个整数 maxChoosableInteger （整数池中可选择的最大数）和 desiredTotal（累计和），
 * 若先出手的玩家能稳赢则返回 true ，否则返回 false 。假设两位玩家游戏时都表现 最佳 。
 * https://leetcode.cn/problems/can-i-win/description/
 */
public class CanIWin {
    public boolean canIWin(int n, int m) {
        if (m <= 0) {
            return true;
        }
        if (n * (n + 1) / 2 < m) {
            return false;
        }
        // 0表示没算过
        // 1表示算过返回false
        // -1表示算过返回true
        int[] dp = new int[1 << (n + 1)];
        return f(n, (1 << (n + 1)) - 1, m, dp);
    }

    // 从1到n，已经选择的状态为status，当前累加和为rest，当前选手的先手是否能赢
    private boolean f(int n, int status, int rest, int[] dp) {
        if (rest <= 0) {
            return false;
        }
        if (dp[status] != 0) {
            return dp[status] == 1;
        }
        boolean ans = false;
        // status，从1-4范围上选，0忽略，1表示可选，0表示不可选
        // 1 1 1 1 1
        for (int i = 1; i <= n; i++) {
            // 当前状态没选过，并且对手没有赢，那么我就是赢家
            if ((status & (1 << i)) != 0 && !f(n, (status ^ (1 << i)), rest - i, dp)) {
                ans = true;
                break;
            }
        }
        dp[status] = ans ? 1 : -1;
        return ans;
    }
}
