package leetcode.leetcodeweek.test2025.test458;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/7/13 9:05
 * https://leetcode.cn/contest/weekly-contest-458/problems/process-string-with-special-operations-ii/description/
 */
public class Code458_03 {
    public static char processStr(String str, long k) {
        char[] s = str.toCharArray();
        int n = s.length;
        String ans = "";
        boolean reverse = false;
        for (int i = 0; i < n; i++) {
            if (s[i] == '*' ) {
                if (ans.length() > 0) {
                    if (reverse) {
                        ans = ans.substring(1, ans.length());
                    } else {
                        ans = ans.substring(0, ans.length() - 1);
                    }
                }
            } else if (s[i] == '#') {
                ans += ans;
            } else if (s[i] == '%') {
                reverse = !reverse;
            } else {
                if (reverse) {
                    ans = (s[i] + ans);
                } else {
                    ans += s[i];
                }
            }
        }
        if (k >= ans.length()) {
            return '.';
        }
        if (reverse) {

            return ans.charAt((int) (ans.length() - k - 1));
        }
        return ans.charAt((int) k);
    }

    public static void main(String[] args) {
        String s = "a#b%*";
        System.out.println(processStr(s, 1));
    }
}
