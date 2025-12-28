package algorithm.class78dp13;

/**
 * @author 汪大鹏
 * @version 1.0
 * @date 2024-06-26 19:46
 * 给你一棵以 root 为根的 二叉树 ，请你返回 任意 二叉搜索子树的最大键值和。
 * <p>
 * 二叉搜索树的定义如下：
 * <p>
 * 任意节点的左子树中的键值都 小于 此节点的键值。
 * 任意节点的右子树中的键值都 大于 此节点的键值。
 * 任意节点的左子树和右子树都是二叉搜索树。
 * https://leetcode.cn/problems/maximum-sum-bst-in-binary-tree/description/
 */
public class MaxSumBST {
    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
    }

    public static class Info {
        int max; // 左树最大
        int min; // 右树最小
        int sum; // 整棵树的和
        int maxBstSum; // 最大的二叉搜索树和
        boolean isBst; // 是否是二叉搜索树

        public Info(int max, int min, int sum, int maxBstSum, boolean isBst) {
            this.max = max;
            this.min = min;
            this.sum = sum;
            this.maxBstSum = maxBstSum;
            this.isBst = isBst;
        }
    }

    public int maxSumBST(TreeNode root) {
        return f(root).maxBstSum;
    }

    private Info f(TreeNode x) {
        if (x == null) {
            return new Info(Integer.MIN_VALUE, Integer.MAX_VALUE, 0, 0, true);
        }
        Info infol = f(x.left);
        Info infor = f(x.right);
        int max = Math.max(x.val, Math.max(infol.max, infor.max));
        int min = Math.min(x.val, Math.min(infol.min, infor.min));
        int sum = x.val + infol.sum + infor.sum;
        boolean isBst = infol.isBst && infor.isBst && infol.max < x.val && x.val < infor.min;
        int maxBstSum = Math.max(infol.maxBstSum, infor.maxBstSum);
        if (isBst) {
            maxBstSum = Math.max(maxBstSum, sum);
        }
        return new Info(max, min, sum, maxBstSum, isBst);
    }
}
