package leetcode.study.leetcode001_200;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/6/15 19:08
 * https://leetcode.cn/problems/flatten-binary-tree-to-linked-list/
 */
public class Leetcode114Flatten {
    public static void flatten(TreeNode root) {
        if (root == null) {
            return;
        }
        List<Integer> collect = new ArrayList<>();
        inorder(root, collect);
        TreeNode cur;
        for (int i = 1; i < collect.size(); i++) {
            cur = new TreeNode(collect.get(i));
            root.right = cur;
            root.left = null;
            root = root.right;
        }
    }

    private static void inorder(TreeNode cur, List<Integer> collect) {
        if (cur != null) {
            collect.add(cur.val);
            inorder(cur.left, collect);
            inorder(cur.right, collect);
        }
    }

    public static void main(String[] args) {
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(5);
        root.left.left = new TreeNode(3);
        root.left.right = new TreeNode(4);
        root.right.right = new TreeNode(6);
        flatten(root);
        System.out.println(root);
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
