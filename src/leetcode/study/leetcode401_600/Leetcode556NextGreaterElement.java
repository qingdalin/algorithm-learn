package leetcode.study.leetcode401_600;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/12/28 15:42
 * https://leetcode.cn/problems/next-greater-element-iii/
 */
public class Leetcode556NextGreaterElement {
    public int nextGreaterElement(int n) {
        char[] s = String.valueOf(n).toCharArray();
        int i = s.length - 2;
        while (i >= 0 && s[i] >= s[i + 1]) {
            i--;
        }
        // 1 2 5 4 3
        if (i < 0) {
            return -1;
        }
        int j = s.length - 1;
        while (j >= 0 && s[i] >= s[j]) {
            j--;
        }
        swap(s, i, j);
        reverse(s, i + 1);
        long ans = Long.parseLong(String.valueOf(s));
        return ans > Integer.MAX_VALUE ? -1 : (int) ans;
    }

    private void reverse(char[] s, int i) {
        int j = s.length - 1;
        while (i <= j) {
            swap(s, i++, j--);
        }
    }

    private  void swap(char[] s, int i, int j) {
        char tmp = s[i];
        s[i] = s[j];
        s[j] = tmp;
    }
}
