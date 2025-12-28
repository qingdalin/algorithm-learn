package leetcode.study.leetcode001_200;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/6/15 15:25
 * https://leetcode.cn/problems/construct-binary-tree-from-inorder-and-postorder-traversal/
 */
public class Leetcode106BuildTree {
    public static TreeNode buildTree(int[] in, int[] post) {
        Map<Integer, Integer> map = new HashMap<>();
        int n = in.length;
        for (int i = 0; i < n; i++) {
            map.put(in[i], i);
        }
        return f(in, 0, n - 1, post, 0, post.length - 1, map);
    }

    private static TreeNode f(int[] in, int l1, int r1, int[] post, int l2, int r2, Map<Integer, Integer> map) {
        // 左 头 右
        // l1 k r1
        // l2   r2
        // 左 右 头
        if (l2 > r2) {
            return null;
        }
        TreeNode head = new TreeNode(post[r2]);
        if (l2 == r2) {
            return head;
        }
        int k = map.get(post[r2]);
        head.left = f(in, l1, k - 1, post, l2, l2 + k - l1 - 1, map);
        head.right = f(in, k + 1, r1, post, r2 - (r1 - k), r2 - 1, map);
        return head;
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
