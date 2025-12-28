package leetcode.leetcodeweek.test2025.test458;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/7/13 9:04
 * https://leetcode.cn/contest/weekly-contest-458/problems/process-string-with-special-operations-i/
 */
public class Code458_01 {
    public static String processStr(String str) {
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
        if (reverse) {
            String tmp = "";
            for (int i = ans.length() - 1; i >= 0; i--) {
                tmp += ans.charAt(i);
            }
            return tmp;
        }
        return ans;
    }

    public static void main(String[] args) {
        String s = "%es";
        System.out.println(processStr(s));
    }
}
