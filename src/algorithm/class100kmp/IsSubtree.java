package algorithm.class100kmp;

import java.util.ArrayList;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2024/8/7 20:08
 * https://leetcode.cn/problems/subtree-of-another-tree/description/
 * 给你两棵二叉树 root 和 subRoot 。检验 root 中是否包含和 subRoot 具有相同结构和节点值的子树。如果存在，返回 true ；否则，返回 false 。
 * <p>
 * 二叉树 tree 的一棵子树包括 tree 的某个节点和这个节点的所有后代节点。tree 也可以看做它自身的一棵子树。
 */
public class IsSubtree {

    public boolean isSubtree(TreeNode t1, TreeNode t2) {
        if (t1 != null && t2 != null) {
            ArrayList<String> s1 = new ArrayList<>();
            ArrayList<String> s2 = new ArrayList<>();
            serial1(t1, s1);
            serial1(t2, s2);
            return kmp(s1, s2) != -1;
        }

        return t2 == null;
    }

    public static int kmp(ArrayList<String> s1, ArrayList<String> s2) {
        int n = s1.size(), m = s2.size(), x = 0, y = 0;
        int[] next = nextArray(s2, m);
        while (x < n && y < m) {
            if (isEqual(s1.get(x), s2.get(y))) {
                x++;
                y++;
            } else if (y == 0) {
                x++;
            } else {
                y = next[y];
            }
        }
        return y == m ? x - y : -1;
    }

    public static int[] nextArray(ArrayList<String> s2, int m) {
        if (m == 1) {
            return new int[] {-1};
        }
        int[] next = new int[m];
        next[0] = -1;
        next[1] = 0;
        int i = 2, cn = 0;
        while (i < m) {
            if (isEqual(s2.get(i - 1), s2.get(cn))) {
                next[i++] = ++cn;
            } else if (cn > 0) {
                cn = next[cn];
            } else {
                next[i++] = 0;
            }
        }
        return next;
    }

    private static boolean isEqual(String s1, String s2) {
        if (s1 == null && s2 == null) {
            return true;
        }
        if (s1 != null && s2 != null) {
            return s1.equals(s2);
        }
        return false;
    }

    private void serial1(TreeNode head, ArrayList<String> s1) {
        if (head == null) {
            s1.add(null);
        } else {
            s1.add(String.valueOf(head.val));
            serial1(head.left, s1);
            serial1(head.right, s1);
        }
    }

    public boolean isSubtree2(TreeNode t1, TreeNode t2) {
        StringBuilder s1 = new StringBuilder();
        StringBuilder s2 = new StringBuilder();
        serial(t1, s1);
        serial(t2, s2);
        return kmp(s1.toString().toCharArray(), s2.toString().toCharArray()) != -1;
    }

    private void serial(TreeNode head, StringBuilder stringBuilder) {
        if (head == null) {
            stringBuilder.append("#");
            return;
        }
        stringBuilder.append("*").append(head.val);
        serial(head.left, stringBuilder);
        serial(head.right, stringBuilder);
    }
    public static int kmp(char[] s1, char[] s2) {
        int n = s1.length;
        int m = s2.length;
        // x和y表示s1和s2的位置
        int x = 0, y = 0;
        int[] next = nextArray(s2, m);
        while (x < n && y < m) {
            if (s1[x] == s2[y]) {
                x++;
                y++;
            } else if (y == 0) {
                x++;
            } else {
                y = next[y];
            }
        }
        // 要么x越界，要么y越界
        return y == m ? x - y : -1;
    }

    private static int[] nextArray(char[] s2, int m) {
        if (m == 1) {
            return new int[] {-1};
        }
        int[] next = new int[m];
        next[0] = -1;
        next[1] = 0;
        // cn表示前一个需要比对的位置
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

    public boolean isSubtree1(TreeNode t1, TreeNode t2) {
        if (t1 != null && t2 != null) {
            return isSame(t1, t2) || isSubtree(t1.left, t2) || isSubtree(t1.right, t2);
        }
        return t2 == null;
    }

    private boolean isSame(TreeNode t1, TreeNode t2) {
        if (t1 == null && t2 == null) {
            return true;
        }
        if (t1 != null && t2 != null) {
            return t1.val == t2.val && isSame(t1.left, t2.left) && isSame(t1.right, t2.right);
        }
        return false;
    }

    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
    }
}
