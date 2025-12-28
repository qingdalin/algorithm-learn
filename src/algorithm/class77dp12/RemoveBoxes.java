package algorithm.class77dp12;

/**
 * @author 汪大鹏
 * @version 1.0
 * @date 2024-06-23 11:23
 * 给出一些不同颜色的盒子 boxes ，盒子的颜色由不同的正数表示。
 *
 * 你将经过若干轮操作去去掉盒子，直到所有的盒子都去掉为止。
 * 每一轮你可以移除具有相同颜色的连续 k 个盒子（k >= 1），这样一轮之后你将得到 k * k 个积分。
 *
 * 返回 你能获得的最大积分和 。
 * https://leetcode.cn/problems/remove-boxes/description/
 */
public class RemoveBoxes {
    public int removeBoxes(int[] boxes) {
        int n = boxes.length;
        int[][][] dp = new int[n][n][n];
        return f1(boxes, 0, n - 1, 0, dp);
    }

    private int f1(int[] boxes, int l, int r, int k, int[][][] dp) {
        if (l > r) {
            return 0;
        }
        // dp[l][r][k]表示：从l到r上消除，跟随k个前缀为boxed[l]颜色的盒子
        if (dp[l][r][k] > 0) {
            return dp[l][r][k];
        }
        int s = l;
        while (s + 1 <= r && boxes[l] == boxes[s + 1]) {
            s++;
        }
        // 计算一共多少个boxed[l]相同盒子
        int cnt = k + s - l + 1;
        int ans = cnt * cnt + f1(boxes, s + 1, r, 0, dp);
        for (int m = s + 2; m <= r; m++) {
            if (boxes[l] == boxes[m] && boxes[m - 1] != boxes[m]) {
                // boxes[l] == boxes[m]跟随前缀一起消除，一定要等于boxes[l]的盒子
                // boxes[m - 1] != boxes[m]，只跟随第一个消除，后续的剪枝
                ans = Math.max(ans, f1(boxes, s + 1, m - 1, 0, dp) + f1(boxes, m, r, cnt, dp));
            }
        }
        dp[l][r][k] = ans;
        return ans;
    }
}
