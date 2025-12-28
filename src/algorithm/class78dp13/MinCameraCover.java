package algorithm.class78dp13;

/**
 * @author 汪大鹏
 * @version 1.0
 * @date 2024-06-28 20:57
 * 给定一个二叉树，我们在树的节点上安装摄像头。
 *
 * 节点上的每个摄影头都可以监视其父对象、自身及其直接子对象。
 *
 * 计算监控树的所有节点所需的最小摄像头数量。
 * https://leetcode.cn/problems/binary-tree-cameras/description/
 */
public class MinCameraCover {
    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
    }
    public static int ans;
    public int minCameraCover(TreeNode root) {
        ans = 0;
        // 如果最后返回0，证明根节点无覆盖，最后摄像头+1，放在根节点
        if (f(root) == 0) {
            ans++;
        }
        return ans;
    }
    /*
        递归含义，假设x一定有父亲
        x为整棵树的头，并且想全部覆盖
        使用最少的摄像头
        0：x无覆盖，x的下方节点全部被覆盖
        1：x有覆盖，x上无摄像机，x的下方节点全部被覆盖
        2：x有覆盖，x上有摄像机，x的下方节点全部被覆盖
     */
    public static int f(TreeNode x) {
        if (x == null) {
            // 空树默认有覆盖，并且无摄像机
            return 1;
        }
        int left = f(x.left);
        int right = f(x.right);
        if (left == 0 || right == 0) {
            // left right
            //   0    1
            //   0    2
            //   1    0
            //   2    0
            // 左右只要有一个0，返回2，一共四种情况，x必需有摄像机
            ans++;
            return 2;
        }
        if (left == 1 && right == 1) {
            // 左右节点都被覆盖，x的状态交给父亲决策
            return 0;
        }
        // left  right
        //  1      2
        //  2      2
        //  2      1
        // 这三种情况返回1，左右有一个2，证明x被覆盖，且无摄像机
        return 1;
    }
}
