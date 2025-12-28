package leetcode.study.leetcode401_600;

import java.util.List;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/9/21 15:58
 * https://leetcode.cn/problems/longest-word-in-dictionary-through-deleting/
 */
public class Leetcode524FindLongestWord {
    public String findLongestWord(String s, List<String> dictionary) {
        String ans = "";
        int maxLen = 0;
        dictionary.sort((a, b) -> b.length() != a.length() ? b.length() - a.length() : a.compareTo(b));
        for (String cur : dictionary) {
            if (isSubseq(cur, s)) {
                return cur;
            }
        }
        return ans;
    }

    public String findLongestWord1(String s, List<String> dictionary) {
        String ans = "";
        int maxLen = 0;
        for (String cur : dictionary) {
            if (isSubseq(cur, s)) {
                if (cur.length() > maxLen) {
                    ans = cur;
                    maxLen = cur.length();
                } else if (cur.length() == maxLen && cur.compareTo(ans) < 0) {
                    ans = cur;
                }
            }
        }
        return ans;
    }

    public boolean isSubseq(String s, String t) {
        int i = 0, j = 0, n = s.length(), m = t.length();
        while (i < n && j < m) {
            if (s.charAt(i) == t.charAt(j)) {
                i++;
            }
            j++;
        }
        return i == n;
    }
}
