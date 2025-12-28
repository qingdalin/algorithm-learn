package algorithm.class79dp14;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author 汪大鹏
 * @version 1.0
 * @date 2024-06-29 15:04
 * 存在一棵无向连通树，树中有编号从 0 到 n - 1 的 n 个节点， 以及 n - 1 条边。
 *
 * 给你一个下标从 0 开始的整数数组 nums ，长度为 n ，其中 nums[i] 表示第 i 个节点的值。
 * 另给你一个二维整数数组 edges ，长度为 n - 1 ，其中 edges[i] = [ai, bi] 表示树中存在一条位于节点 ai 和 bi 之间的边。
 *
 * 删除树中两条 不同 的边以形成三个连通组件。对于一种删除边方案，定义如下步骤以计算其分数：
 *
 * 分别获取三个组件 每个 组件中所有节点值的异或值。
 * 最大 异或值和 最小 异或值的 差值 就是这一种删除边方案的分数。
 * 例如，三个组件的节点值分别是：[4,5,7]、[1,9] 和 [3,3,3] 。
 * 三个异或值分别是 4 ^ 5 ^ 7 = 6、1 ^ 9 = 8 和 3 ^ 3 ^ 3 = 3 。最大异或值是 8 ，最小异或值是 3 ，分数是 8 - 3 = 5 。
 * 返回在给定树上执行任意删除边方案可能的 最小 分数。
 * https://leetcode.cn/problems/minimum-score-after-removals-on-a-tree/description/
 */
public class MinimumScore {
    public static int MAXN = 1001;
    public static int[] dfn = new int[MAXN];
    public static int[] size = new int[MAXN];
    public static int[] xor = new int[MAXN];
    public static int dfnCnt;

    public int minimumScore(int[] nums, int[][] edges) {
        dfnCnt = 0;
        int n = nums.length;
        List<List<Integer>> graph = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
        }
        for (int[] edge : edges) {
            graph.get(edge[0]).add(edge[1]);
            graph.get(edge[1]).add(edge[0]);
        }
        Arrays.fill(dfn, 0, n, 0);
        f(nums, graph, 0);
        int ans = Integer.MAX_VALUE;
        int m = edges.length;
        for (int i = 0, a, b, sum1, sum2, sum3, pre, pos; i < m; i++) {
            // 边的最大dfn点
            a = Math.max(dfn[edges[i][0]], dfn[edges[i][1]]);
            for (int j = i + 1; j < m; j++) {
                b = Math.max(dfn[edges[j][0]], dfn[edges[j][1]]);
                if (a < b) {
                    pre = a;
                    pos = b;
                } else {
                    pre = b;
                    pos = a;
                }
                sum1 = xor[pos];
                if (pos < pre + size[pre]) {
                    sum2 = xor[pre] ^ xor[pos];
                    sum3 = xor[1] ^ xor[pre];
                } else {
                    sum2 = xor[pre];
                    sum3 = xor[1] ^ sum2 ^ sum1;
                }
                ans = Math.min(ans, Math.max(sum1, Math.max(sum2, sum3)) - Math.min(sum1, Math.min(sum2, sum3)));
            }
        }
        return ans;
    }

    private void f(int[] nums, List<List<Integer>> graph, int u) {
        int i = ++dfnCnt;
        dfn[u] = i;
        size[i] = 1;
        xor[i] = nums[u];
        for (Integer v : graph.get(u)) {
            if (dfn[v] == 0) {
                f(nums, graph, v);
                size[i] += size[dfn[v]];
                xor[i] ^= xor[dfn[v]];
            }
        }
    }
}
