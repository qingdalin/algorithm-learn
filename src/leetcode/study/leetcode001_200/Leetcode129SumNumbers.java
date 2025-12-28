package leetcode.study.leetcode001_200;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/6/23 19:56
 * https://leetcode.cn/problems/sum-root-to-leaf-numbers/
 */
public class Leetcode129SumNumbers {
    public static int sumNumbers(TreeNode root) {
        if (root == null) {
            return 0;
        }
        return dfs(root, 0);
    }

    private static int dfs(TreeNode cur, int path) {
        if (cur.left == null && cur.right == null) {
            return path * 10 + cur.val;
        }
        int ans = 0;
        if (cur.left != null) {
            ans += dfs(cur.left, path * 10 + cur.val);
        }
        if (cur.right != null) {
            ans += dfs(cur.right, path * 10 + cur.val);
        }
        return ans;
    }

    static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode() {
        }

        TreeNode(int val) {
            this.val = val;
        }

        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }
}
