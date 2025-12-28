package leetcode.study.leetcode401_600;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/8/10 17:43
 */
public class Leetcode429LevelOrder {
    public static Deque<Node> deque = new ArrayDeque<>();
    public static List<List<Integer>> levelOrder(Node root) {
        List<List<Integer>> ans = new ArrayList<>();
        if (root == null) {
            return ans;
        }
        deque.addLast(root);
        while (!deque.isEmpty()) {
            int size = deque.size();
            List<Integer> list = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                Node cur = deque.pollFirst();
                list.add(cur.val);
                if (cur.children != null) {
                    for (Node child : cur.children) {
                        deque.addLast(child);
                    }
                }
            }
            ans.add(list);
        }
        return ans;
    }

    // Definition for a Node.
    class Node {
        public int val;
        public List<Node> children;

        public Node() {
        }

        public Node(int _val) {
            val = _val;
        }

        public Node(int _val, List<Node> _children) {
            val = _val;
            children = _children;
        }
    }

    ;
}
