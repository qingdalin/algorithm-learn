package algorithm.class79dp14;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 汪大鹏
 * @version 1.0
 * @date 2024-06-29 13:09
 * 给你一棵 树（即一个连通、无向、无环图），根节点是节点 0 ，这棵树由编号从 0 到 n - 1 的 n 个节点组成。
 * 用下标从 0 开始、长度为 n 的数组 parent 来表示这棵树，其中 parent[i] 是节点 i 的父节点，由于节点 0 是根节点，所以 parent[0] == -1 。
 *
 * 另给你一个字符串 s ，长度也是 n ，其中 s[i] 表示分配给节点 i 的字符。
 *
 * 请你找出路径上任意一对相邻节点都没有分配到相同字符的 最长路径 ，并返回该路径的长度。
 * https://leetcode.cn/problems/longest-path-with-different-adjacent-characters/description/
 */
public class LongestPath {
    public int longestPath(int[] parent, String str) {
        char[] s = str.toCharArray();
        int n = s.length;
        List<List<Integer>> graph = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
        }
        for (int i = 1; i < n; i++) {
            graph.get(parent[i]).add(i);
        }
        return f(s, graph, 0).maxPath;
    }

    private Info f(char[] s, List<List<Integer>> graph, int u) {
        if (graph.get(u).isEmpty()) {
            // 如果是叶子节点，最大路径都是1
            return new Info(1, 1);
        }
        int max1 = 0; // 下方最长
        int max2 = 0; // 下方次长
        int maxPath = 1; // 最大路径1，表示自己
        for (Integer v : graph.get(u)) {
            Info nextInfo = f(s, graph, v);
            // 不包含自己的最大路径，每次都更新
            maxPath = Math.max(maxPath, nextInfo.maxPath);
            if (s[u] != s[v]) {
                if (nextInfo.maxPathFromHead > max1) {
                    // 如果有从头部最大长度大于最长max1，那么max1给次长max2，max1更新为现在nextInfo.maxPathFromHead
                    max2 = max1;
                    max1 = nextInfo.maxPathFromHead;
                } else if (nextInfo.maxPathFromHead > max2) {
                    // 如果nextInfo.maxPathFromHead小于max1最长，大于max2次长，只更新max2
                    max2 = nextInfo.maxPathFromHead;
                }
            }
        }
        // 当前节点从头开始的最长路径
        int maxPathFromHead = max1 + 1;
        // 两种情况比较最大
        maxPath = Math.max(maxPath, max1 + max2 + 1);
        return new Info(maxPathFromHead, maxPath);
    }

    public static class Info {
        int maxPathFromHead;
        int maxPath;

        public Info(int maxPathFromHead, int maxPath) {
            this.maxPathFromHead = maxPathFromHead;
            this.maxPath = maxPath;
        }
    }
}
