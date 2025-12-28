package algorithm.class59map;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author 汪大鹏
 * @version 1.0
 * @date 2024-03-23 13:36
 */
public class MovesToStamp {
    public int[] movesToStamp(String stamp, String target) {
        char[] s = stamp.toCharArray();
        char[] t = target.toCharArray();
        int m = s.length;
        int n = t.length;
        int[] inegree = new int[n - m + 1];
        Arrays.fill(inegree, m);
        List<List<Integer>> graph = new ArrayList<>();
        for (int i = 0; i <= n; i++) {
            graph.add(new ArrayList<>());
        }
        int[] queue = new int[n - m + 1];
        int l = 0, r = 0;
        for (int i = 0; i <= n - m; i++) {
            // i开头的m个
            // i+0 i+1 i+2..i+m-1
            for (int j = 0; j < m; j++) {
                if (t[i + j] == s[j]) {
                    if (--inegree[i] == 0) {
                        queue[r++] = i;
                    }
                } else {
                    // i + j
                    // from：错误的位置
                    // to: i开头的下标
                    graph.get(i + j).add(i);
                }
            }
        }
        // 同一个位置取消错误，不重复统计
        boolean[] visited = new boolean[n];
        int[] path = new int[n - m + 1];
        int size = 0;
        while (l < r) {
            int cur = queue[l++];
            path[size++] = cur;
            for (int i = 0; i < m; i++) {
                if (!visited[cur + i]) {
                    visited[cur + i] = true;
                    for (Integer next : graph.get(cur + i)) {
                        if (--inegree[next] == 0) {
                            queue[r++] = next;
                        }
                    }
                }
            }
        }
        if (size != n - m + 1) {
            return new int[0];
        }
        for (int i = 0, j = size - 1; i < j; i++, j--) {
            int temp = path[i];
            path[i] = path[j];
            path[j] = temp;
        }
        return path;
    }
}
