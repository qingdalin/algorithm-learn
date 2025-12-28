package leetcode.study.leetcode001_200;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/6/9 20:00
 * https://leetcode.cn/problems/recover-binary-search-tree/
 */
public class Leetcode099RecoverTree {
    public static void recoverTree(TreeNode root) {
        Deque<TreeNode> stack = new ArrayDeque<>();
        TreeNode x = null, y = null, preNode = null;
        while (!stack.isEmpty() || root != null) {
            while (root != null) {
                stack.push(root);
                root = root.left;
            }
            root = stack.pop();
            if (preNode != null && root.val < preNode.val) {
                y = root;
                if (x == null) {
                    x = preNode;
                } else {
                    break;
                }
            }
            preNode = root;
            root = root.right;
        }
        swap(x, y);
    }

    private static void swap(TreeNode x, TreeNode y) {
        int tmp = x.val;
        x.val = y.val;
        y.val = tmp;
    }

    private static void collect(TreeNode cur, List<Integer> list) {
        if (cur != null) {
            collect(cur.left, list);
            list.add(cur.val);
            collect(cur.right, list);
        }
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
