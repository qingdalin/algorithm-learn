package leetcode.study.leetcode401_600;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/9/21 15:12
 * https://leetcode.cn/problems/longest-uncommon-subsequence-ii/
 */
public class Leetcode522FindLUSlength2 {
    public int findLUSlength(String[] strs) {
        int n = strs.length;
        int ans = -1;
        next: for (int i = 0; i < n; i++) {
            if (strs[i].length() <= ans) {
                continue;
            }
            for (int j = 0; j < n; j++) {
                if (j != i && isSubSeq(strs[i], strs[j])) {
                    continue next;
                }
            }
            ans = strs[i].length();
        }
        return ans;
    }

    public boolean isSubSeq(String s, String t) {
        // 判断s是否是t的子序列
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
