package algorithm.class124;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2024/10/20 9:58
 * // Morris遍历实现先序和中序遍历
 * // 测试链接 : https://leetcode.cn/problems/binary-tree-preorder-traversal/
 * // 测试链接 : https://leetcode.cn/problems/binary-tree-inorder-traversal/
 */
public class Code01_MorrisPreorderInorder {
    class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
    }

    /**
     * 1、开始cur = head，如果cur为null直接结束
     * 2、来到cur，是否有左树
     *    来到左树的最右树
     *    a.最右的树的右节点是null，那么指向cur，去左树遍历
     *    b.最右树的右节点是cur，那么指向空，去右树遍历
     * 3、没有左树去cur的右树
     * 没有左树的只会到达一次节点
     * @param head
     */
    public static void morris(TreeNode head) {
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
                    // 第二次到达节点
                    mostRight.right = null;
                }
            }
            cur = cur.right;
        }
    }
    // 先序遍历
    public static List<Integer> preorderTraversal(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        morrisPreorder(root, res);
        return res;
    }

    public static void morrisPreorder(TreeNode head, List<Integer> res) {
        TreeNode cur = head;
        TreeNode mostRight = null;
        while (cur != null) {
            mostRight = cur.left;
            if (mostRight != null) { // 左树不为空
                while (mostRight.right != null && mostRight.right != cur) {
                    mostRight = mostRight.right;
                }
                if (mostRight.right == null) {
                    res.add(cur.val);
                    mostRight.right = cur;
                    cur = cur.left;
                    continue;
                } else {
                    mostRight.right = null;
                }
            } else {
                res.add(cur.val);
            }
            cur = cur.right;
        }
    }
    // 中序遍历
    public static List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        morrisInorder(root, res);
        return res;
    }
    public static void morrisInorder(TreeNode head, List<Integer> res) {
        TreeNode cur = head;
        TreeNode mostRight = null;
        while (cur != null) {
            mostRight = cur.left;
            if (mostRight != null) { // 左树不为空
                while (mostRight.right != null && mostRight.right != cur) {
                    mostRight = mostRight.right;
                }
                if (mostRight.right == null) {
                    mostRight.right = cur;
                    cur = cur.left;
                    continue;
                } else {
                    mostRight.right = null;
                }
            }
            res.add(cur.val);
            cur = cur.right;
        }
    }
    public static void morrisInorder1(TreeNode head, List<Integer> res) {
        TreeNode cur = head;
        TreeNode mostRight = null;
        while (cur != null) {
            mostRight = cur.left;
            if (mostRight != null) { // 左树不为空
                while (mostRight.right != null && mostRight.right != cur) {
                    mostRight = mostRight.right;
                }
                if (mostRight.right == null) {
                    mostRight.right = cur;
                    cur = cur.left;
                    continue;
                } else {
                    res.add(cur.val);
                    mostRight.right = null;
                }
            } else {
                res.add(cur.val);
            }
            cur = cur.right;
        }
    }
}
