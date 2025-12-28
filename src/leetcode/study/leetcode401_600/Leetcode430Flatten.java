package leetcode.study.leetcode401_600;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/8/10 17:54
 */
public class Leetcode430Flatten {
    public Node flatten(Node head) {
        if (head == null) {
            return head;
        }
        dfs(head);
        return head;
    }

    public Node dfs(Node node) {
        Node cur = node;
        Node last = null;
        while (cur != null) {
            Node next = cur.next;
            if (cur.child != null) {
                Node childNode = dfs(cur.child);
                next = cur.next;
                cur.next = cur.child;
                cur.child.prev = cur;
                if (next != null) {
                    childNode.next = next;
                    next.prev = childNode;
                }
                cur.child = null;
                last = childNode;
            } else {
                last = cur;
            }
            cur = next;
        }
        return last;
    }

    private Node f(Node cur) {
        if (cur == null) {
            return null;
        }
        if (cur.child != null) {
            Node tmp = cur.next;
            Node f = f(cur.child);
            cur.next = f;
            if (f != null) {
                f.prev = cur;
            }
            if (cur.next != null) {
                cur.next.next = tmp;
                tmp.prev = cur.next;
            }
            cur.child = null;
        } else {
            return f(cur.next);
        }
        return cur;
    }

    // Definition for a Node.
    class Node {
        public int val;
        public Node prev;
        public Node next;
        public Node child;
    }

    ;
}
