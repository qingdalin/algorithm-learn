package leetcode.study.leetcode401_600;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/8/31 17:07
 * https://leetcode.cn/problems/most-frequent-subtree-sum/
 */
public class Leetcode508FindFrequentTreeSum {
    public static List<Integer> list = new ArrayList<>();
    public static Map<Integer, Integer> map = new HashMap<>();
    public static int maxCnt;
    public static int[] findFrequentTreeSum(TreeNode root) {
        list.clear();
        map.clear();
        maxCnt = 0;
        f(root);
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            int sum = entry.getKey();
            int cnt = entry.getValue();
            if (cnt > maxCnt) {
                maxCnt = cnt;
                list.clear();
                list.add(sum);
            } else if (cnt == maxCnt) {
                list.add(sum);
            }
        }
        int[] ans = new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
            ans[i] = list.get(i);
        }
        return ans;
    }

    private static int f(TreeNode node) {
        if (node == null) {
            return 0;
        }
        int sum = node.val + f(node.left) + f(node.right);
        int cnt = map.getOrDefault(sum, 0) + 1;
        map.put(sum, cnt);
        maxCnt = Math.max(maxCnt, cnt);
        return sum;
    }

    public static void main(String[] args) {
        TreeNode root = new TreeNode(5);
        root.left = new TreeNode(2);
        root.right = new TreeNode(-3);
        System.out.println(Arrays.toString(findFrequentTreeSum(root)));
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
