package algorithm.class89greedy01;

import java.util.Arrays;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2024/7/21 10:56
 * 公司计划面试 2n 人。给你一个数组 costs ，其中 costs[i] = [aCosti, bCosti] 。
 * 第 i 人飞往 a 市的费用为 aCosti ，飞往 b 市的费用为 bCosti 。
 *
 * 返回将每个人都飞到 a 、b 中某座城市的最低费用，要求每个城市都有 n 人抵达。
 * https://leetcode.cn/problems/two-city-scheduling/description/
 */
public class TwoCitySchedCost {
    public int twoCitySchedCost(int[][] costs) {
        int n = costs.length;
        int[] arr = new int[n];
        int sum = 0;
        for (int i = 0; i < n; i++) {
            // 计算所有人去a的费用
            sum += costs[i][0];
            // 所有人从a换到b的额外费用
            arr[i] = costs[i][1] - costs[i][0];
        }
        Arrays.sort(arr);
        for (int i = 0; i < n / 2; i++) {
            sum += arr[i];
        }
        return sum;
    }
}
