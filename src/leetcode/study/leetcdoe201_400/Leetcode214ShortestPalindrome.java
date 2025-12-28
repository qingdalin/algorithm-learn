package leetcode.study.leetcdoe201_400;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/7/12 15:25
 * https://leetcode.cn/problems/shortest-palindrome/
 */
public class Leetcode214ShortestPalindrome {
    public static int MAXN = 100001;
    public static char[] ss = new char[MAXN];
    public static int n;
    public static int[] p = new int[MAXN];
    public static String shortestPalindrome(String str) {
        if (str.isEmpty()) {
            return "";
        }
        build(str);
        return manacher(str);
    }

    public static String manacher(String str) {
        int index = 0;
        int max = 0;
        for (int i = 0, c = 0, r = 0, len; i < n; i++) {
            len = r > i ? Math.min(p[2 * c - i], r - i) : 1;
            while (i + len < n && i - len >= 0 && ss[i + len] == ss[i - len]) {
                len++;
            }
            if (i + len > r) {
                r = i + len;
                c = i;
            }
            if (len > max) {
                index = c;
                max = len;
            } else if (len == max && c < index) {
                index = c;
            }
        }
        // # a # b # b # a # c #  d  #
        // 0 1 2 3 4 5 6 7 8 9 10 11 12
        //         c
        // # a # b # b #
        // 0 1 2 3 4 5 6
        String s = "";
        for (int i = index + max; i < n; i++) {
            s = ss[i] + s;
        }
        String ans = "";
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) != '#') {
                ans += s.charAt(i);
            }
        }
        for (int i = 0; i < n; i++) {
            if (ss[i] != '#') {
                ans += ss[i];
            }
        }
        return ans;
    }

    private static void build(String str) {
        char[] s = str.toCharArray();
        n = s.length * 2 + 1;
        for (int i = 0, j = 0; i < n; i++) {
            ss[i] = (i & 1) == 0 ? '#' : s[j++];
        }
    }

    public static void main(String[] args) {
//        String s = "aacecaaa";
        String s = "abb";
//        String s = "abbacd";
        System.out.println(shortestPalindrome(s));
    }
}
