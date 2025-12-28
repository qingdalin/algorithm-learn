package algorithm.class124;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2024/10/20 13:42
 * // Morris遍历求两个节点的最低公共祖先
 * // 测试链接 : https://leetcode.cn/problems/lowest-common-ancestor-of-a-binary-tree/
 */
public class Code05_MorrisLCS {
    class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
    }

    public static TreeNode lowestCommonAncestor(TreeNode head, TreeNode o1, TreeNode o2) {
        // o1是o2的祖先
        if (preOrder(o1.left, o1, o2) != null || preOrder(o1.right, o1, o2) != null) {
            return o1;
        }
        // o2是o1的祖先
        if (preOrder(o2.left, o1, o2) != null || preOrder(o2.right, o1, o2) != null) {
            return o2;
        }
        TreeNode cur = head;
        TreeNode mostRight = null;
        TreeNode lca = null;
        // 最先找到的节点
        TreeNode left = preOrder(head, o1, o2);
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
                    if (lca == null) {
                        // 检查left是否在cur的左树的最右边界上
                        if (checkRight(cur.left, left)) {
                            // 检查left右树上是否右o2
                            if (preOrder(left.right, o1, o2) != null) {
                                lca = left;
                            }
                            left = cur;
                            // 为什么此时检查的是left而不是cur
                            // 因为cur右树上的某些右指针可能没有恢复回来
                            // 需要等右指针恢复回来之后检查才不出错
                            // 所以此时检查的是left而不是cur
                            // 课上已经重点图解了
                        }
                    }
                }
            }
            cur = cur.right;
        }
        return lca != null ? lca : left;
    }

    private static boolean checkRight(TreeNode head, TreeNode target) {
        TreeNode cur = head;
        while (cur != null) {
            if (cur == target) {
                return true;
            }
            cur = cur.right;
        }
        return false;
    }

    private static TreeNode preOrder(TreeNode head, TreeNode o1, TreeNode o2) {
        TreeNode cur = head;
        TreeNode mostRight = null;
        TreeNode ans = null;
        while (cur != null) {
            mostRight = cur.left;
            if (mostRight != null) { // 左树不为空
                while (mostRight.right != null && mostRight.right != cur) {
                    mostRight = mostRight.right;
                }
                if (mostRight.right == null) { // 第一次到达节点
                    if (ans == null && (cur == o1 || cur == o2)) {
                        ans = cur;
                    }
                    mostRight.right = cur;
                    cur = cur.left;
                    continue;
                } else {
                    // 第二次到达节点
                    mostRight.right = null;
                }
            } else {
                if (ans == null && (cur == o1 || cur == o2)) {
                    ans = cur;
                }
            }
            cur = cur.right;
        }
        return ans;
    }
}
