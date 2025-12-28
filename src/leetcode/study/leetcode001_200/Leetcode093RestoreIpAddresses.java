package leetcode.study.leetcode001_200;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/6/7 17:50
 * https://leetcode.cn/problems/restore-ip-addresses/
 *
 */
public class Leetcode093RestoreIpAddresses {
    public static int len = 4;
    public static int[] arr = new int[len];
    public static List<String> restoreIpAddresses(String str) {
        List<String> ans = new ArrayList<>();
        char[] s = str.toCharArray();
        f(ans, 0, 0, s);
        return ans;
    }

    private static void f(List<String> ans, int i, int j, char[] s) {
        if (i == len) {
            if (j == s.length) {
                StringBuilder str = new StringBuilder();
                for (int k = 0; k < len; k++) {
                    str.append(arr[k]);
                    if (k != len - 1) {
                        str.append(".");
                    }
                }
                ans.add(str.toString());
            }
            return;
        }
        if (j == s.length) {
            return;
        }
        if (s[j] == '0') {
            arr[i] = 0;
            f(ans, i + 1, j + 1, s);
            return;
        }
        int num = 0;
        for (int k = j; k < s.length; k++) {
            num = num * 10 + s[k] - '0';
            if (num > 0 && num <= 255) {
                arr[i] = num;
                f(ans, i + 1, k + 1, s);
            } else {
                break;
            }
        }
    }

    public static List<String> restoreIpAddresses2(String str) {
        List<String> ans = new ArrayList<>();
        char[] s = str.toCharArray();
        List<String> path = new ArrayList<>();
        for (int i = 0; i < len; i++) {
            path.add("");
        }
        f2(ans, 0, 0, s, path);
        return ans;
    }

    private static void f2(List<String> ans, int i, int j, char[] s, List<String> path) {
        if (i == len) {
            if (j == s.length) {
                ans.add(String.join(".", path));
            }
            return;
        }
        if (j == s.length) {
            return;
        }
        if (s[j] == '0') {
            path.set(i, String.valueOf(s[j]));
            f2(ans, i + 1, j + 1, s, path);
            return;
        }
        int num = 0;
        for (int k = j; k < s.length; k++) {
            num = num * 10 + s[k] - '0';
            if (num > 0 && num <= 255) {
                path.set(i, String.valueOf(num));
                f2(ans, i + 1, j + 1, s, path);
            } else {
                break;
            }
        }
    }

    public static void main(String[] args) {
        String s = "25525511135";
        System.out.println(restoreIpAddresses(s));
    }
}
