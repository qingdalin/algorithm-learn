package leetcode.study.leetcode401_600;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/9/21 19:31
 * https://leetcode.cn/problems/minimum-absolute-difference-in-bst/description/
 */
public class Leetcode530GetMinimumDifference {
    public int getMinimumDifference1(TreeNode root) {
        List<Integer> list = new ArrayList<>();
        f(root, list);
        list.sort((a, b) -> a - b);
        int size = list.size();
        int ans = Integer.MAX_VALUE;
        for (int i = 0; i < size - 1; i++) {
            ans = Math.min(ans, list.get(i + 1) - list.get(i));
        }
        return ans;
    }

    private void f(TreeNode root, List<Integer> list) {
        if (root != null) {
            list.add(root.val);
            f(root.left, list);
            f(root.right, list);
        }
    }

    public static int ans = Integer.MAX_VALUE;
    public static int pre = Integer.MIN_VALUE / 2;
    public static int getMinimumDifference(TreeNode root) {
        dfs(root);
        return ans;
    }

    private static void dfs(TreeNode root) {
        if (root != null) {
            dfs(root.left);
            ans = Math.min(ans, root.val - pre);
            pre = root.val;
            dfs(root.right);
        }
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
