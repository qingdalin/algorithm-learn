package leetcode.study.leetcode001_200;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/6/9 19:39
 * https://leetcode.cn/problems/unique-binary-search-trees-ii/
 */
public class Leetcode095GenerateTrees {
    public static List<TreeNode> generateTrees(int n) {
        List<TreeNode> ans = f(1, n);
        return ans;
    }

    private static List<TreeNode> f(int start, int end) {
        List<TreeNode> ans = new ArrayList<>();
        if (start > end) {
            ans.add(null);
            return ans;
        }
        for (int i = start; i <= end; i++) {
            List<TreeNode> leftTree = f(start, i - 1);
            List<TreeNode> rightTree = f(i + 1, end);
            for (TreeNode left : leftTree) {
                for (TreeNode right : rightTree) {
                    TreeNode cur = new TreeNode(i);
                    cur.left = left;
                    cur.right = right;
                    ans.add(cur);
                }
            }
        }
        return ans;
    }


    static class TreeNode {
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
