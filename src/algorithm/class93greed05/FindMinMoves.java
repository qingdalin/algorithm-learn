package algorithm.class93greed05;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2024/7/28 8:50
 * 假设有 n 台超级洗衣机放在同一排上。开始的时候，每台洗衣机内可能有一定量的衣服，也可能是空的。
 *
 * 在每一步操作中，你可以选择任意 m (1 <= m <= n) 台洗衣机，与此同时将每台洗衣机的一件衣服送到相邻的一台洗衣机。
 *
 * 给定一个整数数组 machines 代表从左至右每台洗衣机中的衣物数量，
 * 请给出能让所有洗衣机中剩下的衣物的数量相等的 最少的操作步数 。如果不能使每台洗衣机中衣物的数量相等，则返回 -1 。
 * https://leetcode.cn/problems/super-washing-machines/description/
 */
public class FindMinMoves {
    public int findMinMoves(int[] machines) {
        int sum = 0;
        int n = machines.length;
        for (int machine : machines) {
            sum += machine;
        }
        if (sum % n != 0) {
            return -1;
        }
        int avg = sum / n; // 平均每台机器的衣服
        int leftSum = 0; // 左侧累加和
        int leftNeed = 0; // 左侧需要的衣服
        int rightNeed = 0; // 右侧需要的衣服
        int bottleNeck = 0; // 瓶颈
        int ans = 0;
        for (int i = 0; i < n; leftSum += machines[i], i++) {
            // 0 ... i - 1,正好i个数
            leftNeed = i * avg - leftSum;
            // 0...i - 1, i, i + 1...n - 1
            rightNeed = (n - 1 - (i + 1) + 1) * avg - (sum - leftSum - machines[i]);
            if (leftNeed > 0 && rightNeed > 0) {
                // 来到当前位置，如果左侧和右侧都需要衣服，那么至少需要左加上右的轮数
                bottleNeck = leftNeed + rightNeed;
            } else {
                // 如果有一次不需要衣服，轮数等于左侧或者右侧绝对值最大值
                bottleNeck = Math.max(Math.abs(leftNeed), Math.abs(rightNeed));
            }
            ans = Math.max(ans, bottleNeck);
        }
        return ans;
    }
}
