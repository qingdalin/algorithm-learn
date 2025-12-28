package algorithm.class124;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2024/10/20 10:34
 * // Morris遍历实现后序遍历
 * // 测试链接 : https://leetcode.cn/problems/binary-tree-postorder-traversal/
 */
public class Code02_MorrisPostorder {
    class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
    }

    public static List<Integer> postorderTraversal(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        morrisPostorder(root, res);
        return res;
    }

    private static void morrisPostorder(TreeNode head, List<Integer> res) {
        TreeNode cur = head;
        TreeNode mostRight = null;
        while (cur != null) {
            mostRight = cur.left;
            if (mostRight != null) { // 左树不为空
                while (mostRight.right != null && mostRight.right != cur) {
                    mostRight = mostRight.right;
                }
                if (mostRight.right == null) { // 第一次到达节点
                    mostRight.right = cur;
                    cur = cur.left;
                    continue;
                } else {
                    // 第二次到达节点，收集左树的右边界
                    mostRight.right = null;
                    collect(cur.left, res);
                }
            }
            cur = cur.right;
        }
        collect(head, res);
    }

    private static void collect(TreeNode head, List<Integer> res) {
        TreeNode tail = reverse(head);
        TreeNode cur = tail;
        while (cur != null) {
            res.add(cur.val);
            cur = cur.right;
        }
        reverse(tail);
    }

    private static TreeNode reverse(TreeNode from) {
        TreeNode pre = null;
        TreeNode next = null;
        while (from != null) {
            next = from.right;
            from.right = pre;
            pre = from;
            from = next;
        }
        return pre;
    }
}
