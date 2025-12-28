package algorithm.class76dp11;

/**
 * @author 汪大鹏
 * @version 1.0
 * @date 2024-06-22 11:15
 * 给你一个整数数组 nums 。玩家 1 和玩家 2 基于这个数组设计了一个游戏。
 *
 * 玩家 1 和玩家 2 轮流进行自己的回合，玩家 1 先手。开始时，两个玩家的初始分值都是 0 。
 * 每一回合，玩家从数组的任意一端取一个数字（即，nums[0] 或 nums[nums.length - 1]），
 * 取到的数字将会从数组中移除（数组长度减 1 ）。玩家选中的数字将会加到他的得分上。当数组中没有剩余数字可取时，游戏结束。
 *
 * 如果玩家 1 能成为赢家，返回 true 。如果两个玩家得分相等，同样认为玩家 1 是游戏的赢家，也返回 true 。
 * 你可以假设每个玩家的玩法都会使他的分数最大化。
 */
public class PredictTheWinner {
    public boolean predictTheWinner1(int[] nums) {
        int sum = 0;
        for (int num : nums) {
            sum += num;
        }
        int n = nums.length;
        int first = f1(nums, 0, n - 1);
        int second = sum - first;
        return first >= second;
    }

    // 从l到r范围上，玩家1获得的最大分值，都按最好情况拿
    private int f1(int[] nums, int l, int r) {
        if (l == r) {
            return nums[l];
        }
        if (l + 1 == r) {
            return Math.max(nums[l], nums[r]);
        }
        // 可能性1：玩家1拿走了l为值的数，
        // 玩家2从 l+1---r位置拿，玩家2拿了l + 1, 玩家1从l+2拿到r，玩家2拿了r，玩家1从l+1拿到r-1,两者取最小，就是玩家2的最大收益
        int p1 = nums[l] + Math.min(f1(nums, l + 2, r), f1(nums, l + 1, r - 1));
        // 可能性1：玩家1拿走了r为值的数，
        // 玩家2从 l---r - 1位置拿，玩家2拿了l, 玩家1从l+1拿到r-1，玩家2拿了r - 1，玩家1从l拿到r-2,两者取最小，就是玩家2的最大收益
        int p2 = nums[r] + Math.min(f1(nums, l + 1, r - 1), f1(nums, l, r - 2));
        return Math.max(p1, p2);
    }

    public boolean predictTheWinner2(int[] nums) {
        int sum = 0;
        for (int num : nums) {
            sum += num;
        }
        int n = nums.length;
        int[][] dp = new int[n][n];
        for (int i = 0; i < n - 1; i++) {
            dp[i][i] = nums[i];
            dp[i][i + 1] = Math.max(nums[i], nums[i + 1]);
        }
        dp[n - 1][n - 1] = nums[n - 1];
        for (int l = n - 3; l >= 0; l--) {
            for (int r = l + 2; r < n; r++) {
                dp[l][r] = Math.max(nums[l] + Math.min(dp[l + 2][r], dp[l + 1][r - 1]),
                    nums[r] + Math.min(dp[l + 1][r - 1], dp[l][r - 2]));
            }
        }
        int first = dp[0][n - 1];
        int second = sum - first;
        return first >= second;
    }

    public boolean predictTheWinner(int[] nums) {
        int sum = 0;
        for (int num : nums) {
            sum += num;
        }
        int n = nums.length;
        int[][] dp = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                dp[i][j] = -1;
            }
        }
        int first = f2(nums, 0, n - 1, dp);
        int second = sum - first;
        return first >= second;
    }

    // 从l到r范围上，玩家1获得的最大分值，都按最好情况拿
    private int f2(int[] nums, int l, int r, int[][] dp) {
        if (dp[l][r] != -1) {
            return dp[l][r];
        }
        int ans = 0;
        if (l == r) {
            ans = nums[l];
        } else if (l + 1 == r) {
            ans = Math.max(nums[l], nums[r]);
        } else {
            // 可能性1：玩家1拿走了l为值的数，
            // 玩家2从 l+1---r位置拿，玩家2拿了l + 1, 玩家1从l+2拿到r，玩家2拿了r，玩家1从l+1拿到r-1,两者取最小，就是玩家2的最大收益
            int p1 = nums[l] + Math.min(f2(nums, l + 2, r, dp), f2(nums, l + 1, r - 1, dp));
            // 可能性1：玩家1拿走了r为值的数，
            // 玩家2从 l---r - 1位置拿，玩家2拿了l, 玩家1从l+1拿到r-1，玩家2拿了r - 1，玩家1从l拿到r-2,两者取最小，就是玩家2的最大收益
            int p2 = nums[r] + Math.min(f2(nums, l + 1, r - 1, dp), f2(nums, l, r - 2, dp));
            ans = Math.max(p1, p2);
        }
        dp[l][r] = ans;
        return ans;
    }
}
