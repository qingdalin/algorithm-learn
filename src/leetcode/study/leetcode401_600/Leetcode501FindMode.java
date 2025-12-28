package leetcode.study.leetcode401_600;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/8/31 8:22
 * https://leetcode.cn/problems/find-mode-in-binary-search-tree/
 */
public class Leetcode501FindMode {
    public static int cnt = 0, base = Integer.MAX_VALUE, maxCnt = 0;
    public static Map<Integer, Integer> map = new HashMap<>();
    public static Set<Integer> set = new HashSet<>();
    public static List<Integer> list = new ArrayList<>();
    public static int[] findMode1(TreeNode root) {
        map.clear();
        set.clear();
        cnt = 0;
        dfs2(root);
        int[] ans = new int[set.size()];
        int idx = 0;
        for (int i : set) {
            ans[idx++] = i;
        }
        return ans;
    }

    private static void dfs2(TreeNode cur) {
        if (cur != null) {
            int curCnt = map.getOrDefault(cur.val, 0) + 1;
            map.put(cur.val, curCnt);
            if (curCnt > cnt) {
                cnt = curCnt;
                set.clear();
                set.add(cur.val);
            } else if (curCnt == cnt) {
                set.add(cur.val);
            }
            dfs2(cur.left);
            dfs2(cur.right);
        }
    }

    public static int[] findMode2(TreeNode root) {
        set.clear();
        cnt = maxCnt = base = 0;
        dfs1(root);
        int[] ans = new int[set.size()];
        int idx = 0;
        for (int i : set) {
            ans[idx++] = i;
        }
        return ans;
    }

    private static void dfs1(TreeNode node) {
        if (node != null) {
            dfs1(node.left);
            update(node.val);
            dfs1(node.right);
        }
    }

    public static int[] findMode(TreeNode root) {
        list.clear();
        cnt = maxCnt = base = 0;
        TreeNode cur = root, mostRight = null;
        while (cur != null) {
            mostRight = cur.left;
            if (mostRight != null) {
                // 有左树，找到左树的最右
                while (mostRight.right != null && mostRight.right != cur) {
                    mostRight = mostRight.right;
                }
                if (mostRight.right == null) {
                    // 左树最右是空，指向cur，去当前的left遍历
                    mostRight.right = cur;
                    cur = cur.left;
                    // 第一次到达
                    continue;
                } else {
                    // 左树不为空，清空，去右树遍历
                    // 第二次到达，后续遍历
                    mostRight.right = null;
                }
            }
            update(cur.val);
            // 只到达一次的节点
            // 左树为空，直接去右树
            cur = cur.right;
        }
        int[] ans = new int[list.size()];
        int idx = 0;
        for (int i : list) {
            ans[idx++] = i;
        }
        return ans;
    }

    private static void update(int x) {
        if (x == base) {
            cnt++;
        } else {
            cnt = 1;
            base = x;
        }
        if (cnt == maxCnt) {
            list.add(base);
        } else if (cnt > maxCnt) {
            maxCnt = cnt;
            list.clear();
            list.add(base);
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
