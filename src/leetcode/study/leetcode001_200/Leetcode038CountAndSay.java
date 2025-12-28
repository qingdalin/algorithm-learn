package leetcode.study.leetcode001_200;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/4/21 19:25
 * https://leetcode.cn/problems/count-and-say/
 */
public class Leetcode038CountAndSay {
    public static String countAndSay(int n) {
        if (n == 1) {
            return "1";
        }
        String s = countAndSay(n - 1);
        String ans = "";
        int len = 1;
        char c = s.charAt(0);
        for (int i = 1; i < s.length(); i++) {
            if (s.charAt(i) != s.charAt(i - 1)) {
                ans = (ans + len + c);
                c = s.charAt(i);
                len = 1;
            } else {
                len++;
            }
        }
        ans = (ans + len + c);
        return ans;
    }

    public static String countAndSay1(int n) {
        if (n == 1) {
            return "1";
        }
        String s = countAndSay(n - 1);
        StringBuilder ans = new StringBuilder();
        int len = 1;
        char c = s.charAt(0);
        for (int i = 1; i < s.length(); i++) {
            if (s.charAt(i) != s.charAt(i - 1)) {
                ans.append(len).append(c);
                c = s.charAt(i);
                len = 1;
            } else {
                len++;
            }
        }
        ans.append(len).append(c);
        return ans.toString();
    }



    public static void main(String[] args) {
        System.out.println(countAndSay(4));
    }
}
