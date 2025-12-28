package leetcode.leetcodeweek.test2025.test443;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/3/30 9:52
 * https://leetcode.cn/contest/weekly-contest-443/problems/longest-palindrome-after-substring-concatenation-ii/description/
 * ?slug=longest-palindrome-after-substring-concatenation-ii&region=local_v2
 */
public class Code_03_Three {
    public static int n, m;
    public static int longestPalindrome(String s, String t) {
        StringBuilder sb = new StringBuilder(t);
        StringBuilder builder = sb.reverse();
        String tmp = builder.toString();
        int ans = 0;
        for (int i = 0; i < tmp.length(); i++) {
            for (int j = 0; j < s.length(); j++) {
                if (s.charAt(j) == tmp.charAt(i)) {
                    ans++;
                }
            }
        }
        return ans;
    }



    public static void main(String[] args) {
        String s = "abcde";
        String t = "ecdba";
        System.out.println(longestPalindrome(s, t));
    }
}
