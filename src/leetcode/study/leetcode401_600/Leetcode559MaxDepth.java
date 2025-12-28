package leetcode.study.leetcode401_600;

import java.util.List;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/12/28 16:14
 * https://leetcode.cn/problems/maximum-depth-of-n-ary-tree/
 */
public class Leetcode559MaxDepth {
    public int maxDepth(Node root) {
        int ans = 0;
        if (root == null) {
            return ans;
        }
        for (Node child : root.children) {
            ans = Math.max(ans, maxDepth(child));
        }
        return ans + 1;
    }

    public int maxDepth1(Node root) {
        int ans = 0;
        if (root == null) {
            return ans;
        }
        return f(root);
    }

    private int f(Node root) {
        int ans = 1;
        for (Node child : root.children) {
            ans = Math.max(ans, f(child) + 1);
        }
        return ans;
    }

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
