package leetcode.study.leetcode401_600;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/8/17 7:51
 * https://leetcode.cn/problems/delete-node-in-a-bst/
 */
public class Leetcode450DeleteNode {
    public TreeNode deleteNode(TreeNode root, int key) {
        boolean exist = f1(root, key);
        if (!exist) {
            return root;
        }
        return f(root, key);
    }

    private TreeNode f(TreeNode cur, int k) {
        if (cur == null) {
            return null;
        }
        if (cur.val < k) {
            cur.right = f(cur.right, k);
        } else if (cur.val > k) {
            cur.left = f(cur.left, k);
        } else {
            if (cur.left == null && cur.right == null) {
                return null;
            } else if (cur.left != null && cur.right == null) {
                cur = cur.left;
            } else if (cur.left == null && cur.right != null) {
                cur = cur.right;
            } else {
                TreeNode mostLeft = cur.right;
                while (mostLeft.left != null) {
                    mostLeft = mostLeft.left;
                }
                cur.right = f(cur.right, mostLeft.val);
                mostLeft.right = cur.right;
                mostLeft.left = cur.left;
                cur.left = cur.right = null;
                return mostLeft;
            }
        }
        return cur;
    }

    private boolean f1(TreeNode cur, int k) {
        if (cur == null) {
            return false;
        }
        if (k == cur.val) {
            return true;
        }
        return f1(cur.left, k) || f1(cur.right, k);
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
