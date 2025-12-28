package leetcode.study.leetcode401_600;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/8/3 14:11
 * https://leetcode.cn/problems/add-strings/
 */
public class Leetcode415AddStrings {
    public static char[] s1, s2;
    public static int n, m;
    public static StringBuilder ans = new StringBuilder();
    public static String addStrings(String num1, String num2) {
        s1 = num1.toCharArray();
        n = s1.length;
        s2 = num2.toCharArray();
        m = s2.length;
        ans.setLength(0);
        int carry = 0;
        for (int i = n - 1, j = m - 1, num = 0; i >= 0 || j >= 0; i--, j--) {
            num = 0;
            if (i >= 0) {
                num += s1[i] - '0';
            }
            if (j >= 0) {
                num += s2[j] - '0';
            }
            num += carry;
            ans.insert(0, (num % 10));
            carry = num / 10;
        }
        if (carry == 1) {
            ans.insert(0, 1);
        }
        return ans.toString();
    }

    public static void main(String[] args) {
        String s1 = "11";
        String s2 = "123";
        System.out.println(addStrings(s1, s2));
    }
}
