package algorithm.class64Dijkstra;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

/**
 * @author 汪大鹏
 * @version 1.0
 * @date 2024-04-20 11:51
 * 小明的电动车电量充满时可行驶距离为 cnt，每行驶 1 单位距离消耗 1 单位电量，且花费 1 单位时间。小明想选择电动车作为代步工具。
 * 地图上共有 N 个景点，景点编号为 0 ~ N-1。他将地图信息以 [城市 A 编号,城市 B 编号,两城市间距离] 格式整理在在二维数组 paths，
 * 表示城市 A、B 间存在双向通路。初始状态，电动车电量为 0。每个城市都设有充电桩，
 * charge[i] 表示第 i 个城市每充 1 单位电量需要花费的单位时间。
 * 请返回小明最少需要花费多少单位时间从起点城市 start 抵达终点城市 end。
 * https://leetcode.cn/problems/DFPeFJ/description/
 */
public class ElectricCarPlan {
    public int electricCarPlan(int[][] paths, int cnt, int start, int end, int[] charge) {
        int n = charge.length;
        List<List<int[]>> graph = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
        }
        for (int[] edge : paths) {
            graph.get(edge[0]).add(new int[] {edge[1], edge[2]});
            graph.get(edge[1]).add(new int[] {edge[0], edge[2]});
        }
        int[][] distance = new int[n][cnt + 1];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j <= cnt; j++) {
                distance[i][j] = Integer.MAX_VALUE;
            }
        }
        boolean[][] visited = new boolean[n][cnt + 1];
        // 0，当前城市
        // 1，当前电量
        // 2，当前话费
        PriorityQueue<int[]> heap = new PriorityQueue<>((a, b) -> a[2] - b[2]);
        distance[start][0] = 0;
        heap.add(new int[]{start, 0, 0});
        while (!heap.isEmpty()) {
            int[] record = heap.poll();
            int cur = record[0];
            int power = record[1];
            int cost = record[2];
            if (cur == end) {
                // 遇到终点剪枝结束
                return cost;
            }
            visited[cur][power] = true;
            if (power < cnt) {
                // 电不满充一格
                if (!visited[cur][power + 1] && cost + charge[cur] < distance[cur][power + 1]) {
                    distance[cur][power + 1] = cost + charge[cur];
                    heap.add(new int[] {cur, power + 1, cost + charge[cur]});
                }
            }
            for (int[] edge : graph.get(cur)) {
                int nextCity = edge[0];
                int restPower = power - edge[1];
                int nextCost = cost + edge[1];
                if (restPower >= 0 && !visited[nextCity][restPower]
                    && nextCost< distance[nextCity][restPower]) {
                    distance[nextCity][restPower] = nextCost;
                    heap.add(new int[] {nextCity, restPower, nextCost});
                }
            }
        }
        return -1;
    }
}
