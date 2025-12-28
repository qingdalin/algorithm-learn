package leetcode.study.leetcode401_600;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/9/21 15:07
 * https://leetcode.cn/problems/longest-uncommon-subsequence-i/
 *
 */
public class Leetcode521FindLUSlength {
    public int findLUSlength1(String a, String b) {
        int n = a.length(), m = b.length();
        if (n > m) {
            String tmp = a;
            a = b;
            b = tmp;
        }
        if (a.equals(b)) {
            return -1;
        }
        return b.length();
    }

    public int findLUSlength(String a, String b) {
        return a.equals(b) ? -1 : Math.max(a.length(), b.length());
    }
}
