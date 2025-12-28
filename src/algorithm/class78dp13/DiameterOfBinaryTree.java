package algorithm.class78dp13;

/**
 * @author 汪大鹏
 * @version 1.0
 * @date 2024-06-26 20:08
 * 给你一棵二叉树的根节点，返回该树的 直径 。
 *
 * 二叉树的 直径 是指树中任意两个节点之间最长路径的 长度 。这条路径可能经过也可能不经过根节点 root 。
 *
 * 两节点之间路径的 长度 由它们之间边数表示。
 * https://leetcode.cn/problems/diameter-of-binary-tree/description/
 */
public class DiameterOfBinaryTree {
    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
    }

    public static class Info {
        int height; // 树高
        int diameter; // 直径

        public Info(int height, int diameter) {
            this.height = height;
            this.diameter = diameter;
        }
    }
    public int diameterOfBinaryTree(TreeNode root) {
        return f(root).diameter;
    }

    private Info f(TreeNode x) {
        if (x == null) {
            return new Info(0, 0);
        }
        Info infol = f(x.left);
        Info infor = f(x.right);
        int height = Math.max(infor.height, infol.height) + 1;
        int diameter = Math.max(infol.diameter, infor.diameter);
        diameter = Math.max(diameter, infol.height +infor.height);
        return new Info(height, diameter);
    }
}
