package leetcode.study.leetcode401_600;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/12/28 16:25
 * https://leetcode.cn/problems/binary-tree-tilt/
 */
public class Leetcode563FindTilt {
    public static int ans = 0;
    public static int findTilt(TreeNode root) {
        ans = 0;
        f(root);
        return ans;
    }

    private static int f(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int sumLeft = f(root.left);
        int sumRight = f(root.right);
        ans += Math.abs(sumRight - sumLeft);
        return sumLeft + sumRight + root.val;
    }

    class TreeNode {
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
