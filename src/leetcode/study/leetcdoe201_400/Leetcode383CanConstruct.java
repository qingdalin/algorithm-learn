package leetcode.study.leetcdoe201_400;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/7/30 20:44
 * https://leetcode.cn/problems/ransom-note/description/
 */
public class Leetcode383CanConstruct {
    public boolean canConstruct(String ransomNote, String magazine) {
        char[] s2 = magazine.toCharArray();
        int[] cnt = new int[26];
        int m = s2.length;
        for (int i = 0; i < m; i++) {
            cnt[s2[i] - 'a']++;
        }
        char[] s1 = ransomNote.toCharArray();
        int n = s1.length;
        for (int i = 0; i < n; i++) {
            if (--cnt[s1[i] - 'a']  < 0) {
                return false;
            }
        }
        return true;
    }
}
