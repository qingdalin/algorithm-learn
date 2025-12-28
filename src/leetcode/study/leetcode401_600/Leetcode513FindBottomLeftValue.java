package leetcode.study.leetcode401_600;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/8/31 17:41
 * https://leetcode.cn/problems/find-bottom-left-tree-value/
 */
public class Leetcode513FindBottomLeftValue {
    public int findBottomLeftValue(TreeNode root) {
        Deque<TreeNode> deque = new ArrayDeque<>();
        deque.addLast(root);
        // 1
        // 3 2
        // 2 6 5
        // 6 5 4
        // 5 4
        // 4 7
        // 7
        while (!deque.isEmpty()) {
            root = deque.pollFirst();
            if (root.right != null) {
                deque.addLast(root.right);
            }
            if (root.left != null) {
                deque.addLast(root.left);
            }
        }
        return root.val;
    }

    public int findBottomLeftValue1(TreeNode root) {
        Deque<TreeNode> deque = new ArrayDeque<>();
        deque.addLast(root);
        int ans = 0;
        // 1
        // 3 2
        // 2 6 5
        // 6 5 4
        // 5 4
        // 4 7
        // 7
        while (!deque.isEmpty()) {
            int size = deque.size();
            for (int i = 0; i < size; i++) {
                TreeNode cur = deque.pollFirst();
                if (i == 0) {
                    ans = cur.val;
                }
                if (cur.left != null) {
                    deque.addLast(cur.left);
                }
                if (cur.right != null) {
                    deque.addLast(cur.right);
                }
            }
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
