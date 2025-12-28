package leetcode.study.leetcode401_600;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/8/3 9:05
 * https://leetcode.cn/problems/remove-k-digits/
 */
public class Leetcode402RemoveKdigits {
    public static int MAXN = 100001;
    public static int[] stack = new int[MAXN];
    public static int r;
    public static String removeKdigits(String num, int k) {
        char[] s = num.toCharArray();
        int n = s.length;
        r = 0;
        // 大压小
        for (int i = 0; i < n; i++) {
            while (k > 0 && r > 0 && s[i] < s[stack[r - 1]]) {
                k--;
                r--;
            }
            stack[r++] = i;
        }
        while (k > 0) {
            k--;
            r--;
        }
        StringBuilder ans = new StringBuilder();
        boolean flag = true;
        for (int i = 0; i < r; i++) {
            if (flag && s[stack[i]] == '0') {
               continue;
            }
            flag = false;
            ans.append(s[stack[i]]);
        }
        return ans.toString().equals("") ? "0" : ans.toString();
    }

    public static void main(String[] args) {
        String s = "112";
        System.out.println(removeKdigits(s, 1));
    }
}
