package leetcode.study.leetcode401_600;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/9/23 20:39
 * https://leetcode.cn/problems/convert-bst-to-greater-tree/
 */
public class Leetcode538ConvertBST {
    public int val = 0;
    public TreeNode convertBST(TreeNode root) {
        f(root);
        return root;
    }

    private void f(TreeNode root) {
        if (root != null) {
            f(root.right);
            val += root.val;
            root.val = val;
            f(root.left);
        }
    }

    public class TreeNode {
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
