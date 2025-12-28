package algorithm.class124;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2024/10/20 10:57
 * // Morris遍历判断搜索二叉树
 * // 测试链接 : https://leetcode.cn/problems/validate-binary-search-tree/
 */
public class Code03_MorrisCheckBST {
    class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
    }

    public static boolean isValidBST(TreeNode head) {
        TreeNode cur = head;
        TreeNode mostRight = null;
        TreeNode pre = null;
        // 搜索二叉树：左侧节点都比父节点小，右侧节点都比父节点大
        boolean ans = true;
        while (cur != null) {
            mostRight = cur.left;
            if (mostRight != null) { // 左树不为空
                while (mostRight.right != null && mostRight.right != cur) {
                    mostRight = mostRight.right;
                }
                if (mostRight.right == null) { // 第一次到达节点
                    mostRight.right = cur;
                    cur = cur.left;
                    continue;
                } else {
                    // 第二次到达节点
                    mostRight.right = null;
                }
            }
            if (pre != null && pre.val >= cur.val) {
                ans = false;
            }
            pre = cur;
            cur = cur.right;
        }
        return ans;
    }
}
