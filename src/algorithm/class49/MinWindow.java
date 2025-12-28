package algorithm.class49;

/**
 * https://leetcode.cn/problems/minimum-window-substring/
 * 最小覆盖子串
 * @author 汪大鹏
 * @version 1.0
 * @date 2024-02-05 19:46
 */
public class MinWindow {
    public String minWindow(String str, String tar) {
        if (str.length() < tar.length()) {
            return "";
        }
        char[] s = str.toCharArray();
        char[] t = tar.toCharArray();
        int[] cnts = new int[256];
        for (char c : t) {
            cnts[c]--;
        }
        int start = 0;
        int len = Integer.MAX_VALUE;
        for (int l = 0, r = 0, debt = t.length; r < s.length; r++) {
            if (cnts[s[r]]++ < 0) {
                debt--;
            }
            if (debt == 0) {
                while (cnts[s[l]] > 0) {
                    cnts[s[l++]]--;
                }
                if (r - l + 1 < len) {
                    len = r - l + 1;
                    start = l;
                }
            }
        }
        return len == Integer.MAX_VALUE ? "" : str.substring(start, start + len);
    }
}
