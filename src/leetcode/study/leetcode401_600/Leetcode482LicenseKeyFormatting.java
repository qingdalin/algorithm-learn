package leetcode.study.leetcode401_600;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/8/30 15:42
 * https://leetcode.cn/problems/license-key-formatting/
 */
public class Leetcode482LicenseKeyFormatting {
    public static String licenseKeyFormatting(String str, int k) {
        char[] s = str.toCharArray();
        int n = s.length;
        StringBuilder ans = new StringBuilder();
        for (int i = n - 1; i >= 0;) {
            int len = k;
            while (i >= 0 && len > 0) {
                if (s[i] == '-') {
                    i--;
                } else if (Character.isDigit(s[i])) {
                    len--;
                    ans.insert(0, s[i--]);
                } else {
                    len--;
                    ans.insert(0, Character.toUpperCase(s[i--]));
                }
            }
            ans.insert(0, '-');
        }
        boolean f = true;
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < ans.length(); i++) {
            if (f && ans.charAt(i) == '-') {
                continue;
            }
            f = false;
            res.append(ans.charAt(i));
        }
        return res.toString();
    }

    public static void main(String[] args) {
//        String s = "5F3Z-2e-9-w";
        String s = "--a-a-a-a--";
        System.out.println(licenseKeyFormatting(s, 2));
    }
}
