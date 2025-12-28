package leetcode.study.leetcdoe201_400;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/8/2 9:10
 * https://leetcode.cn/problems/longest-absolute-file-path/
 */
public class Leetcode388LengthLongestPath {
    public static int lengthLongestPath(String str) {
        char[] s = str.toCharArray();
        int n = s.length;
        Deque<String> deque = new ArrayDeque<>();
        int depth = 0;
        return 1;
    }

    private static int f(int i, String[] arr, int len) {
        if (i >= arr.length) {
            return len;
        }
        int ans = 0;
        return 0;
    }

    public static void main(String[] args) {
        String str = "dir\\n\\tsubdir1\\n\\t\\tfile1.ext\\n\\t\\tsubsubdir1\\n\\tsubdir2\\n\\t\\tsubsubdir2\\n\\t\\t\\tfile2.ext";
        System.out.println(lengthLongestPath(str));
    }
}
