package algorithm.class79dp14;

/**
 * @author 汪大鹏
 * @version 1.0
 * @date 2024-06-29 13:59
 * 给你一棵 二叉树 的根节点 root ，树中有 n 个节点。每个节点都可以被分配一个从 1 到 n 且互不相同的值。另给你一个长度为 m 的数组 queries 。
 *
 * 你必须在树上执行 m 个 独立 的查询，其中第 i 个查询你需要执行以下操作：
 *
 * 从树中 移除 以 queries[i] 的值作为根节点的子树。题目所用测试用例保证 queries[i] 不 等于根节点的值。
 * 返回一个长度为 m 的数组 answer ，其中 answer[i] 是执行第 i 个查询后树的高度。
 *
 * 注意：
 *
 * 查询之间是独立的，所以在每个查询执行后，树会回到其 初始 状态。
 * 树的高度是从根到树中某个节点的 最长简单路径中的边数 。
 * https://leetcode.cn/problems/height-of-binary-tree-after-subtree-removal-queries/description/
 */
public class TreeQueries {
    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
    }

    public static int MAXN = 100001;
    public static int[] dfn = new int[MAXN];
    public static int[] deep = new int[MAXN];
    public static int[] size = new int[MAXN];
    public static int[] maxl = new int[MAXN];
    public static int[] maxr = new int[MAXN];
    public static int dfnCnt;

    public int[] treeQueries(TreeNode root, int[] queries) {
        dfnCnt = 0;
        f(root, 0);
        int n = queries.length;
        for (int i = 1; i <= dfnCnt; i++) {
            maxl[i] = Math.max(maxl[i - 1], deep[i]);
        }
        maxr[dfnCnt + 1] = 0;
        for (int i = dfnCnt; i >= 1; i--) {
            maxr[i] = Math.max(maxr[i + 1], deep[i]);
        }
        int[] ans = new int[n];
        for (int i = 0; i < n; i++) {
            int leftMax = maxl[dfn[queries[i]] - 1];
            int rightMax = maxr[dfn[queries[i]] + size[dfn[queries[i]]]];
            ans[i] = Math.max(leftMax, rightMax);
        }
        return ans;
    }

    private void f(TreeNode x, int k) {
        int i = ++dfnCnt;
        dfn[x.val] = i;
        size[i] = 1;
        deep[i] = k;
        // k 从x开始经历几条边
        if (x.left != null) {
            f(x.left, k + 1);
            size[i] += size[dfn[x.left.val]];
        }
        if (x.right != null) {
            f(x.right, k + 1);
            size[i] += size[dfn[x.right.val]];
        }
    }
}
