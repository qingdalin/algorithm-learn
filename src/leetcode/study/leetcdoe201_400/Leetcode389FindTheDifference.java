package leetcode.study.leetcdoe201_400;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/8/2 9:25
 * https://leetcode.cn/problems/find-the-difference/
 */
public class Leetcode389FindTheDifference {
    public char findTheDifference(String str1, String str2) {
        char[] s = str1.toCharArray();
        int n = s.length;
        int[] cnt = new int[26];
        for (int i = 0; i < n; i++) {
            cnt[s[i] - 'a']++;
        }
        char[] s2 = str2.toCharArray();
        for (int i = 0; i < n + 1; i++) {
            cnt[s2[i] - 'a']--;
        }
        for (int i = 0; i < 26; i++) {
            if (cnt[i] != 0) {
                return (char) (i + 'a');
            }
        }
        int i = s[1] - 'a';
        return 'a';
    }
}
