package leetcode.leetcodeweek.test2026.test485;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2026/1/18 9:29
 * https://leetcode.cn/contest/weekly-contest-485/problems/vowel-consonant-score/
 */
public class Code485_01 {
    public static int vowelConsonantScore(String str) {
        char[] s = str.toCharArray();
        int vcnt = 0, ccnt = 0;
        for (char c : s) {
            if (Character.isLetter(c)) {
                if (c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u') {
                    vcnt++;
                } else {
                    ccnt++;
                }
            }
        }
        if (ccnt == 0) {
            return 0;
        }
        return vcnt / ccnt;
    }

    public static void main(String[] args) {
        String s = "au 123";
        System.out.println(vowelConsonantScore(s));
    }
}
