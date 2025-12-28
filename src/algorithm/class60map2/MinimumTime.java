package algorithm.class60map2;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 汪大鹏
 * @version 1.0
 * @date 2024-03-24 11:02
 * https://leetcode.cn/problems/parallel-courses-iii/
 */
public class MinimumTime {
    public int minimumTime(int n, int[][] relations, int[] time) {
        List<List<Integer>> graph = new ArrayList<>();
        for (int i = 0; i <= n; i++) {
            graph.add(new ArrayList<>());
        }
        int[] indegree = new int[n + 1];
        for (int[] r : relations) {
            graph.get(r[0]).add(r[1]);
            indegree[r[1]]++;
        }
        int[] queue = new int[n + 1];
        int l = 0, r = 0;
        for (int i = 1; i <= n; i++) {
            if (indegree[i] == 0) {
                queue[r++] = i;
            }
        }
        int[] cost = new int[n + 1];
        int ans = 0;
        while (l < r) {
            int cur = queue[l++];
            // 花费时间等于当前节点加上前置节点
            cost[cur] += time[cur - 1];
            // 花费的最大时间，当前节点完成，最后返回
            ans = Math.max(ans, cost[cur]);
            for (Integer next : graph.get(cur)) {
                // 邻居和自己的花费最大时间
                cost[next] = Math.max(cost[next], cost[cur]);
                if (--indegree[next] == 0) {
                    queue[r++] = next;
                }
            }
        }
        return ans;
    }
}
