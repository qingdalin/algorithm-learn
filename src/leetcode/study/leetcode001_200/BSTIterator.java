package leetcode.study.leetcode001_200;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/7/7 20:07
 */
public class BSTIterator {
    List<Integer> list;
    int i;
    public BSTIterator(TreeNode root) {
        list = new ArrayList<>();
        i = -1;
        inorder(root);
    }

    public void inorder(TreeNode cur) {
        if (cur != null) {
            inorder(cur.left);
            list.add(cur.val);
            inorder(cur.right);
        }
    }

    public int next() {
        return list.get(++i);
    }

    public boolean hasNext() {
        return i + 1 < list.size();
    }
}

class TreeNode {
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
