package algorithm.class118lca01;

import java.util.Arrays;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2024/9/21 8:14
 * 给你一棵树，树上有 n 个节点，按从 0 到 n-1 编号。
 * 树以父节点数组的形式给出，其中 parent[i] 是节点 i 的父节点。树的根节点是编号为 0 的节点。
 * 树节点的第 k 个祖先节点是从该节点到根节点路径上的第 k 个节点。
 * 实现 TreeAncestor 类：
 * TreeAncestor（int n， int[] parent） 对树和父数组中的节点数初始化对象。
 * getKthAncestor(int node, int k) 返回节点 node 的第 k 个祖先节点。如果不存在这样的祖先节点，返回 -1 。
 * https://leetcode.cn/problems/kth-ancestor-of-a-tree-node/description/
 */
public class Code01_KthAncestor {
    class TreeAncestor {
        public int MAXN = 50001;
        public int LIMIT = 16;
        public int power;

        public int log2(int n) {
            int ans = 0;
            while ((1 << ans) <= (n >> 1)) {
                ans++;
            }
            return ans;
        }
        public int[] head = new int[MAXN];
        public int[] next = new int[MAXN];
        public int[] to = new int[MAXN];
        public int cnt;
        // 树的深度
        public int[] deep = new int[MAXN];
        public int[][] stjump = new int[MAXN][LIMIT];
        public TreeAncestor(int n, int[] parent) {
            power = log2(n);
            cnt = 1;
            Arrays.fill(head, 0, n, 0);
            for (int i = 1; i < parent.length; i++) {
                addEdge(parent[i], i);
            }
            dfs(0, 0);
        }

        private void dfs(int i, int f) {
            if (i == 0) {
                // 如果是根节点，层数是1
                deep[i] = 1;
            } else {
                // 不是根节点，层数是父节点加1
                deep[i] = deep[f] + 1;
            }
            stjump[i][0] = f;
            for (int p = 1; (1 << p) <= deep[i]; p++) {
                stjump[i][p] = stjump[stjump[i][p - 1]][p - 1];
            }
            for (int e = head[i]; e != 0; e = next[e]) {
                dfs(to[e], i);
            }
        }

        private void addEdge(int u, int v) {
            next[cnt] = head[u];
            to[cnt] = v;
            head[u] = cnt++;
        }

        public int getKthAncestor(int node, int k) {
            if (deep[node] <= k) {
                return -1;
            }
            // s是要到达的层
            int s = deep[node] - k;
            for (int p = power; p >= 0; p--) {
                if (deep[stjump[node][p]] >= s) {
                    // 跳的层数大于s
                    node = stjump[node][p];
                }
            }
            return node;
        }
    }
}
