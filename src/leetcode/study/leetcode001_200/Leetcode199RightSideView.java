package leetcode.study.leetcode001_200;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/7/12 10:16
 */
public class Leetcode199RightSideView {

    public List<Integer> rightSideView(TreeNode root) {
        List<Integer> ans = new ArrayList<>();
        if (root == null) {
            return ans;
        }
        List<List<Integer>> collect = new ArrayList<>();
        Deque<TreeNode> deque = new ArrayDeque<>();
        deque.addFirst(root);
        while (!deque.isEmpty()) {
            int size = deque.size();
            List<Integer> curLevel = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                TreeNode cur = deque.pollLast();
                curLevel.add(cur.val);
                if (cur.left != null) {
                    deque.addFirst(cur.left);
                }
                if (cur.right != null) {
                    deque.addFirst(cur.right);
                }
            }
            collect.add(curLevel);
        }
        for (List<Integer> cur : collect) {
            ans.add(cur.get(cur.size() - 1));
        }
        return ans;
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
