package leetcode.leetcodeweek.test2026.test491;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2026/3/1 10:24
 * https://leetcode.cn/contest/weekly-contest-491/problems/trim-trailing-vowels/description/
 */
public class Code491_01 {
    public static String trimTrailingVowels(String str) {
        char[] s = str.toCharArray();
        int n = s.length;
        int i = n - 1;
        for (; i >= 0; i--) {
            if ("aeiou".indexOf(s[i]) == -1) {
                break;
            }
        }
        return String.valueOf(s, 0, i + 1);
    }

    public static void main(String[] args) {
        String s = "aeiou";
        System.out.println(trimTrailingVowels(s));
    }
}
