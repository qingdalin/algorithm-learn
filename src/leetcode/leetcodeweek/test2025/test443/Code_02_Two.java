package leetcode.leetcodeweek.test2025.test443;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/3/30 9:52
 */
public class Code_02_Two {
    public static int longestPalindrome(String s, String t) {
        int n = s.length();
        int m = t.length();
        int ans = Integer.MIN_VALUE;
        for (int i = 0; i < n; i++) {
            for (int j = i; j <= n; j++) {
                String a = s.substring(i, j);
                for (int k = 0; k < m; k++) {
                    for (int l = k; l <= m; l++) {
                        String b = t.substring(k, l);
                           if (isHuiwen(a + b)) {
                            ans = Math.max(ans, a.length() + b.length());
                        }
                    }
                }
            }
        }
        return ans;
    }

    public static boolean isHuiwen(String s) {
        char[] arr = s.toCharArray();
        int n = arr.length;
        for (int l = 0, r = n - 1; l <= r; l++, r--) {
            if (arr[l] != arr[r]) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        String s = "abcde";
        String t = "ecdba";
        System.out.println(longestPalindrome(s, t));
    }
}
