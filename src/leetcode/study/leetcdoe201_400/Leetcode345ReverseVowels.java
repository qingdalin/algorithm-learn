package leetcode.study.leetcdoe201_400;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/7/27 15:06
 * https://leetcode.cn/problems/reverse-vowels-of-a-string/
 */
public class Leetcode345ReverseVowels {
    public static String reverseVowels(String str) {
        char[] s = str.toCharArray();
        int n = s.length;
        for (int l = 0, r = n - 1; l <= r;) {
            while (l < r && !isYuanYin(s[l])) {
                l++;
            }
            while (r > l && !isYuanYin(s[r])) {
                r--;
            }
            char tmp = s[l];
            s[l] = s[r];
            s[r] = tmp;
        }
        return String.valueOf(s);
    }

    public static boolean isYuanYin(char s) {
        return s == 'a' || s == 'e' || s == 'i' || s == 'o' || s == 'u'
            || s == 'A' || s == 'E' || s == 'I' || s == 'O' || s == 'U';
    }
}
