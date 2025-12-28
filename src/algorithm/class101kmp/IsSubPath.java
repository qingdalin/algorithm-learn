package algorithm.class101kmp;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2024/8/10 10:22
 * 给你一棵以 root 为根的二叉树和一个 head 为第一个节点的链表。
 * <p>
 * 如果在二叉树中，存在一条一直向下的路径，且每个点的数值恰好一一对应以 head 为首的链表中每个节点的值，那么请你返回 True ，否则返回 False 。
 * <p>
 * 一直向下的路径的意思是：从树中某个节点开始，一直连续向下的路径。
 * https://leetcode.cn/problems/linked-list-in-binary-tree/description/
 */
public class IsSubPath {
    public boolean isSubPath(ListNode head, TreeNode root) {
        int m = 0;
        ListNode tmp = head;
        while (tmp != null) {
            m++;
            tmp = tmp.next;
        }
        int[] s2 = new int[m];
        m = 0;
        while (head != null) {
            s2[m++] = head.val;
            head = head.next;
        }
        int[] next = nextArray(s2, m);
        return find(next, s2, root, 0);
    }

    // cur当前来到的节点
    // i对应s2的位置
    private boolean find(int[] next, int[] s2, TreeNode cur, int i) {
        if (i == s2.length) {
            // 来到越界位置说明找到了返回true
            return true;
        }
        if (cur == null) {
            return false;
        }
        while (i >= 0 && cur.val != s2[i]) {
            // 如果没跳到-1并且cur的值和s2不等一直往前跳
            i = next[i];
        }
        // i == -1
        // i ==  cur.val
        // i + 1往左孩子或者右孩子寻找
        return find(next, s2, cur.left, i + 1) || find(next, s2, cur.right, i + 1);
    }

    private int[] nextArray(int[] s2, int m) {
        if (m <= 1) {
            return new int[] {-1};
        }
        int[] next = new int[m];
        next[0] = -1;
        next[1] = 0;
        int i = 2, cn = 0;
        while (i < m) {
            if (s2[i - 1] == s2[cn]) {
                next[i++] = ++cn;
            } else if (cn > 0) {
                cn = next[cn];
            } else {
                next[i++] = 0;
            }
        }
        return next;
    }

    static class ListNode {
        int val;
        ListNode next;
    }

    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
    }
}
