package algorithm.class78dp13;

/**
 * @author 汪大鹏
 * @version 1.0
 * @date 2024-06-26 20:19
 * 给你一个有 n 个结点的二叉树的根结点 root ，其中树中每个结点 node 都对应有 node.val 枚硬币。整棵树上一共有 n 枚硬币。
 *
 * 在一次移动中，我们可以选择两个相邻的结点，然后将一枚硬币从其中一个结点移动到另一个结点。移动可以是从父结点到子结点，或者从子结点移动到父结点。
 *
 * 返回使每个结点上 只有 一枚硬币所需的 最少 移动次数。
 * https://leetcode.cn/problems/distribute-coins-in-binary-tree/description/
 */
public class DistributeCoins {
    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
    }
    public static class Info {
        int cnt; // 节点数
        int sum; // 硬币数
        int move; // 移动次数

        public Info(int cnt, int sum, int move) {
            this.cnt = cnt;
            this.sum = sum;
            this.move = move;
        }
    }
    public int distributeCoins(TreeNode root) {
        return f(root).move;
    }

    private Info f(TreeNode x) {
        if (x == null) {
            return new Info(0, 0, 0);
        }
        Info infol = f(x.left);
        Info infor = f(x.right);
        int cnt = infol.cnt + infor.cnt + 1;
        int sum = infol.sum + infor.sum + x.val;
        int move = infol.move + infor.move + Math.abs(infol.cnt - infol.sum) + Math.abs(infor.cnt - infor.sum);
        return new Info(cnt, sum, move);
    }
}
