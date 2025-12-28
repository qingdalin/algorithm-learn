package algorithm.class78dp13;

import java.util.HashMap;

/**
 * @author 汪大鹏
 * @version 1.0
 * @date 2024-06-29 10:44
 * 给定一个二叉树的根节点 root ，和一个整数 targetSum ，求该二叉树里节点值之和等于 targetSum 的 路径 的数目。
 *
 * 路径 不需要从根节点开始，也不需要在叶子节点结束，但是路径方向必须是向下的（只能从父节点到子节点）。
 * https://leetcode.cn/problems/path-sum-iii/description/
 */
public class PathSum {
    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
    }

    public static int ans;

    public int pathSum(TreeNode root, int targetSum) {
        // 前缀和信息，key是前缀和大小，val是出现次数
        HashMap<Long, Integer> preSum = new HashMap<>();
        preSum.put(0L, 1);
        ans = 0;
        f(root, targetSum, 0, preSum);
        return ans;
    }

    // sum: 表示当前节点前边的前缀和
    // 必须以x节点结尾，路径和为target的路径数量，结果累加在全局变量ans
    private void f(TreeNode x, int target, long sum, HashMap<Long, Integer> preSum) {
        if (x != null) {
            sum += x.val;
            ans += preSum.getOrDefault(sum - target, 0);
            preSum.put(sum, preSum.getOrDefault(sum, 0) + 1);
            f(x.left, target, sum, preSum);
            f(x.right, target, sum, preSum);
            preSum.put(sum, preSum.get(sum) - 1);
        }
    }


}
