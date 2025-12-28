package leetcode.study.leetcdoe201_400;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/8/2 10:36
 * https://leetcode.cn/problems/is-subsequence/
 */
public class Leetcode392IsSubsequence {
    public static int n, m;
    public static char[] s1, s2;
    public static boolean isSubsequence(String str1, String str2) {
        s1 = str1.toCharArray();
        s2 = str2.toCharArray();
        n = s1.length;
        m = s2.length;
        int i = 0;
        for (int j = 0; i < n && j < m;) {
            if (s1[i] == s2[j]) {
                i++;
            }
            j++;
        }
        return i == n;
    }

    public static boolean isSubsequence1(String str1, String str2) {
        s1 = str1.toCharArray();
        s2 = str2.toCharArray();
        n = s1.length;
        m = s2.length;
        return f(n, m);
    }

    private static boolean f(int i, int j) {
        if (i == 0) {
            return true;
        }
        if (j == 0) {
            return false;
        }
        if (s1[i - 1] == s2[j - 1]) {
            return f(i - 1, j - 1);
        } else {
            return f(i, j - 1);
        }
    }


}
