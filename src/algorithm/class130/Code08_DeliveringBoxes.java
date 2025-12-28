package algorithm.class130;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2024/11/9 15:24
 * // 送箱子到码头的最少行程数
 * // 一共有m个码头，编号1 ~ m， 给定长度为n的二维数组boxes
 * // boxes[i][0]表示i号箱子要送往的码头，boxes[i][1]表示i号箱子重量
 * // 有一辆马车，一次最多能装a个箱子并且箱子总重量不能超过b
 * // 马车一开始在仓库，可以在0位置，马车每开动一次，认为行程+1
 * // 箱子必须按照boxes规定的顺序被放上马车，也必须按照顺序被送往各自的码头
 * // 马车上相邻的箱子如果去往同一个码头，那么认为共享同一趟行程
 * // 马车可能经过多次送货，每次装货需要回到仓库，认为行程+1，送完所有的货，最终要回到仓库，行程+1
 * // 返回至少需要几个行程能把所有的货都送完
 * // 所有数据的范围 <= 10^5
 * // 测试链接 : https://leetcode.cn/problems/delivering-boxes-from-storage-to-ports/
 */
public class Code08_DeliveringBoxes {
    public int boxDelivering(int[][] boxes, int m, int maxBoxes, int maxWeight) {
        int n = boxes.length;
        // dp[i]:表示送完第i个货物回到仓库，至少需要的行程
        int[] dp = new int[n + 1];
        dp[1] = 2;
        int weight = boxes[0][1];
        int trip = 2;
        for (int l = 0, r = 1; r < n; r++) {
            weight += boxes[r][1];
            if (boxes[r][0] != boxes[r - 1][0]) {
                trip++;
            }
            // 1、箱子超出个数限制，l位置箱子向左移动
            // 2、重量超出限制，l位置箱子向左移动
            // 3、箱子向左移动未增加行程，，l位置箱子向左移动
            // 1) 最后一趟货物的个数超了，最后一趟不得不减少货物
            // 2) 最后一趟货物的总重量超了，最后一趟不得不减少货物
            // 3) 最后一趟最左侧的货，分给之前的过程，如果发现之前过程的dp值没变化，那就分出去
            // 最后一趟最左侧的货，分给之前的过程，如果发现之前过程的dp值增加了，一定不要分出去
            // 对于这个逻辑，课上进行了重点图解
            while (r - l + 1 > maxBoxes || weight > maxWeight || dp[l + 1] == dp[l]) {
                weight -= boxes[l++][1];
                if (boxes[l][0] != boxes[l - 1][0]) {
                    // 甩出去的箱子和现在最左位置箱子不在一个码头，行程减1
                    trip--;
                }
            }
            dp[r + 1] = dp[l] + trip;
        }
        return dp[n];
    }
}
