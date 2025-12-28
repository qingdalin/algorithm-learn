package algorithm.class59map;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 汪大鹏
 * @version 1.0
 * @date 2024-03-23 10:34
 * https://leetcode.cn/problems/course-schedule-ii/
 * 拓扑排序，建图
 */
public class FindOrder {
    public int[] findOrder(int numCourses, int[][] prerequisites) {
        List<List<Integer>> graph = new ArrayList<>();
        for (int i = 0; i < numCourses; i++) {
            graph.add(new ArrayList<>());
        }
        // 入度表
        int[] indegree = new int[numCourses];
        for (int[] edge : prerequisites) {
            graph.get(edge[1]).add(edge[0]);
            indegree[edge[0]]++;
        }
        int[] queue = new int[numCourses];
        int l = 0;
        int r = 0;
        // 将0入度的入队列
        for (int i = 0; i < numCourses; i++) {
            if (indegree[i] == 0) {
                queue[r++] = i;
            }
        }
        // 只要队列不空，一直弹
        // cnt记录删除0的点数量和总点数对比
        int cnt = 0;
        while (l < r) {
            int cur = queue[l++];
            cnt++;
            for (Integer next : graph.get(cur)) {
                if (--indegree[next] == 0) {
                    queue[r++] = next;
                }
            }
        }
        return cnt == numCourses ? queue : new int[0];
    }
}
