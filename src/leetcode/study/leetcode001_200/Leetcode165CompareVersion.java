package leetcode.study.leetcode001_200;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/7/2 20:13
 * https://leetcode.cn/problems/compare-version-numbers/
 */
public class Leetcode165CompareVersion {
    public int compareVersion(String str1, String str2) {
        String[] s1 = str1.split("\\.");
        int n = s1.length;
        String[] s2 = str2.split("\\.");
        int m = s2.length;
        int i = 0, j = 0;
        int a = 0, b = 0;
        for (; i < n && j < m; i++, j++) {
            a = Integer.parseInt(s1[i]);
            b = Integer.parseInt(s2[j]);
            if (a < b) {
                return -1;
            } else if (a > b) {
                return 1;
            }
        }
        while (i < n) {
            a = Integer.parseInt(s1[i++]);
            if (a > 0) {
                return 1;
            }
        }
        while (j < m) {
            b = Integer.parseInt(s2[j++]);
            if (b > 0) {
                return -1;
            }
        }
        return 0;
    }
}
