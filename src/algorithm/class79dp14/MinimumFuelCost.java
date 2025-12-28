package algorithm.class79dp14;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 汪大鹏
 * @version 1.0
 * @date 2024-06-29 11:24
 * 给你一棵 n 个节点的树（一个无向、连通、无环图），每个节点表示一个城市，编号从 0 到 n - 1 ，
 * 且恰好有 n - 1 条路。0 是首都。给你一个二维整数数组 roads ，其中 roads[i] = [ai, bi] ，表示城市 ai 和 bi 之间有一条 双向路 。
 *
 * 每个城市里有一个代表，他们都要去首都参加一个会议。
 *
 * 每座城市里有一辆车。给你一个整数 seats 表示每辆车里面座位的数目。
 *
 * 城市里的代表可以选择乘坐所在城市的车，或者乘坐其他城市的车。相邻城市之间一辆车的油耗是一升汽油。
 *
 * 请你返回到达首都最少需要多少升汽油。
 * https://leetcode.cn/problems/minimum-fuel-cost-to-report-to-the-capital/description/
 */
public class MinimumFuelCost {
    public long minimumFuelCost(int[][] roads, int seats) {
        int n = roads.length + 1;
        List<List<Integer>> graph = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
        }
        for (int[] r : roads) {
            graph.get(r[0]).add(r[1]);
            graph.get(r[1]).add(r[0]);
        }
        int[] size = new int[n];
        long[] cost = new long[n];
        f(graph, seats, 0, -1, size, cost);
        return cost[0];
    }

    // u 当前节点，
    // p 是当前节点父亲，不走回头路
    private void f(List<List<Integer>> graph, int seats, int u, int p, int[] size, long[] cost) {
        size[u] = 1;
        for (Integer v : graph.get(u)) {
            if (v != p) {
                f(graph, seats, v, u, size, cost);
                size[u] += size[v];
                cost[u] += cost[v];
                // v -> u还需要一次油耗，人数除以载重向上取整
                // a / b 向上取整 (a + b - 1) / b
                cost[u] += (size[v] + seats - 1) / seats;
            }
        }
    }
}
