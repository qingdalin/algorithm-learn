package leetcode.study.leetcode001_200;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/6/15 16:25
 * https://leetcode.cn/problems/path-sum/
 */
public class Leetcode112HasPathSum {
    public static boolean hasPathSum(TreeNode root, int targetSum) {
        if (root == null) {
            return false;
        }
        return f(root, 0, targetSum);
    }

    private static boolean f(TreeNode cur, int sum, int targetSum) {
        if (cur == null) {
            return false;
        }
        if (cur.left == null && cur.right == null) {
            return sum + cur.val == targetSum;
        }
        boolean p1 = f(cur.left, sum + cur.val, targetSum);
        boolean p2 = f(cur.right, sum + cur.val, targetSum);
        return p1 || p2;
    }

    public static void main(String[] args) {
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        System.out.println(hasPathSum(root, 1));
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
