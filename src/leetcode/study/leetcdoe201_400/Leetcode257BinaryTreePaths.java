package leetcode.study.leetcdoe201_400;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/7/14 19:58
 */
public class Leetcode257BinaryTreePaths {
    public List<String> binaryTreePaths(TreeNode root) {
        List<String> ans = new ArrayList<>();
        f2(ans, root, "");
        return ans;
    }



    private void f2(List<String> ans, TreeNode cur, String path) {
        if (cur == null) {
            return;
        }
        path += cur.val;
        if (cur.left == null && cur.right == null) {
            ans.add(path);
            return;
        }
        path += "->";
        f2(ans, cur.left, path);
        f2(ans, cur.right, path);
    }


    private void f1(List<String> ans, TreeNode cur, List<Integer> path) {
        if (cur == null) {
            return;
        }
        if (cur.left == null && cur.right == null) {
            StringBuilder s = new StringBuilder();
            for (Integer integer : path) {
                s.append(integer);
                s.append("->");
            }
            s.append(cur.val);
            ans.add(s.toString());
            return;
        }
        path.add(cur.val);
        if (cur.left != null) {
            f1(ans, cur.left, path);
        }
        if (cur.right != null) {
            f1(ans, cur.right, path);
        }
        path.remove(path.size() - 1);
    }


    public class TreeNode {
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
}
