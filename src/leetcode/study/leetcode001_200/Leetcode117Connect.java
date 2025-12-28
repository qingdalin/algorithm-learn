package leetcode.study.leetcode001_200;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/6/15 19:33
 */
public class Leetcode117Connect {
    public static int MAXN = 6001;
    public static int l, r;
    public static Node[] queue = new Node[MAXN];
    public static Node connect(Node root) {
        if (root == null) {
            return null;
        }
        l = r = 0;
        queue[r++] = root;
        while (l < r) {
            int size = r - l;
            int tmp = r;
            for (int i = 0; i < size; i++) {
                Node cur = queue[l++];
                if (cur.left != null) {
                    queue[r++] = cur.left;
                }
                if (cur.right != null) {
                    queue[r++] = cur.right;
                }
                if (l < tmp) {
                    cur.next = queue[l];
                }
            }
        }
        return root;
    }

    static class Node {
        public int val;
        public Node left;
        public Node right;
        public Node next;

        public Node() {
        }

        public Node(int _val) {
            val = _val;
        }

        public Node(int _val, Node _left, Node _right, Node _next) {
            val = _val;
            left = _left;
            right = _right;
            next = _next;
        }
    }
}
