package leetcode.study.leetcode001_200;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/6/15 16:04
 * https://leetcode.cn/problems/binary-tree-level-order-traversal-ii/
 */
public class Leetcode107LevelOrderBottom {
    public static int MAXN = 2001;
    public static int l, r;
    public static TreeNode[] queue = new TreeNode[MAXN];
    public static List<List<Integer>> levelOrderBottom(TreeNode root) {
        List<List<Integer>> collect = new ArrayList<>();
        if (root == null) {
            return collect;
        }
        l = r = 0;
        queue[r++] = root;
        while (l < r) {
            int size = r - l;
            List<Integer> cur = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                TreeNode curNode = queue[l++];
                cur.add(curNode.val);
                if (curNode.left != null) {
                    queue[r++] = curNode.left;
                }
                if (curNode.right != null) {
                    queue[r++] = curNode.right;
                }
            }
            collect.add(cur);
        }
        List<List<Integer>> ans = new ArrayList<>();
        for(int i = collect.size() - 1; i >= 0; i--) {
            ans.add(collect.get(i));
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
