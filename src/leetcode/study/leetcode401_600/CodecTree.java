package leetcode.study.leetcode401_600;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/8/16 17:11
 * https://leetcode.cn/problems/serialize-and-deserialize-bst/
 */
public class CodecTree {
    // Encodes a tree to a single string.
    public String serialize(TreeNode root) {
        StringBuilder sb = new StringBuilder();
        dfs1(root, sb);
        return sb.toString();
    }

    private void dfs1(TreeNode cur, StringBuilder sb) {
        if (cur == null) {
            sb.append("#,");
        } else {
            sb.append(cur.val).append(",");
            dfs1(cur.left, sb);
            dfs1(cur.right, sb);
        }
    }

    public TreeNode deserialize(String data) {
        String[] arr = data.split(",");
        return dfs2(arr, 0);
    }
    private TreeNode dfs2(String[] arr, int i) {
        String cur = arr[i];
        if (cur.equals("#")) {
            return null;
        }
        TreeNode root = new TreeNode(Integer.parseInt(cur));
        root.left = dfs2(arr, i + 1);
        root.right = dfs2(arr, i + 1);
        return root;
    }

    // Decodes your encoded data to tree.
//    public TreeNode deserialize(String data) {
//        String[] arr = data.split(",");
//        cnt = 0;
//        return dfs2(arr);
//    }
//    public int cnt = 0;
//    private TreeNode dfs2(String[] arr) {
//        String cur = arr[cnt++];
//        if (cur.equals("#")) {
//            return null;
//        }
//        TreeNode root = new TreeNode(Integer.parseInt(cur));
//        root.left = dfs2(arr);
//        root.right = dfs2(arr);
//        return root;
//    }


    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }
}
