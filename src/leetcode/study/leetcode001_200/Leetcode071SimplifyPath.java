package leetcode.study.leetcode001_200;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/5/23 19:52
 * https://leetcode.cn/problems/simplify-path/
 */
public class Leetcode071SimplifyPath {

    public static StringBuilder ans = new StringBuilder();
    public static Deque<String> stack = new ArrayDeque<>();
    public static String simplifyPath(String path) {
        String[] arr = path.split("/");
        ans.setLength(0);
        stack.clear();
        int n = arr.length;
        for (int i = 0; i < n; i++) {
            if (arr[i].equals("") || arr[i].equals(".")) {
                continue;
            }
            if (arr[i].equals("..")) {
                if (!stack.isEmpty()) {
                    stack.pollLast();
                }
            } else {
                stack.addLast(arr[i]);
            }
        }

        while (!stack.isEmpty()) {
            ans.append("/");
            ans.append(stack.pollFirst());

        }
        return ans.toString().equals("") ? "/" : ans.toString();
    }

    public static void main(String[] args) {
//        String s = "/.../a/../b/c/../d/./";
//        String s = "/home//foo/";
        String s = "/..";
        System.out.println(simplifyPath(s));
    }
}
