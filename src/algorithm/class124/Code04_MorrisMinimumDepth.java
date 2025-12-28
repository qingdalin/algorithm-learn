package algorithm.class124;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2024/10/20 11:35
 * // Morris遍历求二叉树最小高度
 * // 测试链接 : https://leetcode.cn/problems/minimum-depth-of-binary-tree/
 */
public class Code04_MorrisMinimumDepth {
    class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
    }

    public static int minDepth(TreeNode head) {
        if (head == null) {
            return 0;
        }
        int ans = Integer.MAX_VALUE;
        int rightLength = 0;
        int preLevel = 0;
        TreeNode cur = head;
        TreeNode mostRight = null;
        while (cur != null) {
            mostRight = cur.left;
            if (mostRight != null) { // 左树不为空
                rightLength = 1;
                while (mostRight.right != null && mostRight.right != cur) {
                    rightLength++;
                    mostRight = mostRight.right;
                }
                if (mostRight.right == null) { // 第一次到达节点
                    // 右树是空只能是从上到下
                    preLevel++;
                    mostRight.right = cur;
                    cur = cur.left;
                    continue;
                } else {
                    if (mostRight.left == null) {
                        ans = Math.min(ans, preLevel);
                    }
                    preLevel -= rightLength;
                    // 第二次到达节点
                    mostRight.right = null;
                }
            } else {
                // 左树是空，那么只能是从上到下
                preLevel++;
            }
            cur = cur.right;
        }
        // 最后遍历根节点的最右侧树
        rightLength = 1;
        cur = head;
        while (cur.right != null) {
            rightLength++;
            cur = cur.right;
        }
        if (cur.left == null) {
            ans = Math.min(ans, rightLength);
        }
        return ans;
    }
}
