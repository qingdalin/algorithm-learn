package leetcode.study.leetcode401_600;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/9/23 21:55
 * https://leetcode.cn/problems/reverse-string-ii/
 */
public class Leetcode541ReverseStr {
    public String reverseStr1(String str, int k) {
        char[] s = str.toCharArray();
        int n = s.length;
        for (int i = 0, time = 1; i < n; i++) {
            if (n - i < k) {
                // a b c d e f g
                // 0 1 2 3 4 5 6
                // k = 8
                for (int j = n - 1; i <= j; j--, i++) {
                    char tmp = s[i];
                    s[i] = s[j];
                    s[j] = tmp;
                }
            } else if (n - i <  2 * k && n - i > k) {
                for (int j = i + k - 1; i <= j; j--, i++) {
                    char tmp = s[i];
                    s[i] = s[j];
                    s[j] = tmp;
                }
            } else if (i % (k * time) == 0) {
                time++;
                int j = i + k - 1;
                for (; i <= j && j < n; j--, i++) {
                    char tmp = s[i];
                    s[i] = s[j];
                    s[j] = tmp;
                }
            }
        }
        return String.valueOf(s);
    }

    public String reverseStr(String str, int k) {
        char[] s = str.toCharArray();
        int n = s.length;
        for (int i = 0; i < n; i += k * 2) {
            swap(s, i, Math.min(i + k, n) - 1);
        }
        return String.valueOf(s);
    }

    public void swap(char[] s, int l, int r) {
        while (l <= r) {
            char tmp = s[l];
            s[l++] = s[r];
            s[r--] = tmp;
        }
    }
}
