package algorithm.class60map2;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 汪大鹏
 * @version 1.0
 * @date 2024-03-24 10:34
 * https://leetcode.cn/problems/loud-and-rich/
 * 拓扑排序
 */
public class LoudAndRich {
    public int[] loudAndRich(int[][] richer, int[] quiet) {
        List<List<Integer>> graph = new ArrayList<>();
        int n = quiet.length;
        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
        }
        int[] indegree = new int[n];
        for (int[] r : richer) {
            graph.get(r[0]).add(r[1]);
            indegree[r[1]]++;
        }
        int[] queue = new int[n];
        int l = 0, r = 0;
        for (int i = 0; i < n; i++) {
            if (indegree[i] == 0) {
                queue[r++] = i;
            }
        }
        int[] ans = new int[n];
        for (int i = 0; i < n; i++) {
            // 默认最安静的人是自己
            ans[i] = i;
        }
        while (l < r) {
            int cur = queue[l++];
            for (Integer next : graph.get(cur)) {
                // cur    ->>   next
                // quiet[ans[cur]] < quiet[ans[next]]
                if (quiet[ans[cur]] < quiet[ans[next]]) {
                    ans[next] = ans[cur];
                }
                if (--indegree[next] == 0) {
                    queue[r++] = next;
                }
            }
        }
        return ans;
    }
}
