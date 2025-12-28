package leetcode.leetcodeweek.test2025.test445;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/4/13 10:02
 * https://leetcode.cn/contest/weekly-contest-445/problems/smallest-palindromic-rearrangement-i/description/
 */
public class Code02 {
    public static String smallestPalindrome(String str) {
        char[] s = str.toCharArray();
        int n = s.length;
        int[] cnts = new int[26];
        for (int i = 0; i < n; i++) {
            cnts[s[i] - 'a']++;
        }
        StringBuilder ans = new StringBuilder();
        char mid = '1';
        for (int i = 0; i < 26; i++) {
            if (cnts[i] == 0) {
                continue;
            }
            if (cnts[i] % 2 != 0) {
                mid = (char)(i + 'a');
            }
            for (int j = 0; j < cnts[i] / 2; j++) {
                ans.append((char) (i + 'a'));
            }
        }
        String tmp = ans.toString();
        if (mid != '1') {
            ans.append(mid);
        }
        int length = tmp.length();
        for (int i = length - 1; i >= 0; i--) {
            ans.append(tmp.charAt(i));
        }
        return ans.toString();
    }

    public static void main(String[] args) {
        // String s = "daccad";
        String s = "babab";
        //String s = "z";
        System.out.println(smallestPalindrome(s));
    }
}
