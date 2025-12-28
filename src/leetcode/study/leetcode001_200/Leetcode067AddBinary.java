package leetcode.study.leetcode001_200;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/5/21 19:27
 * https://leetcode.cn/problems/add-binary/
 */
public class Leetcode067AddBinary {
    public static String addBinary(String str1, String str2) {
        char[] s1 = str1.toCharArray();
        char[] s2 = str2.toCharArray();
        int n = s1.length;
        int m = s2.length;
        StringBuilder ans = new StringBuilder();
        int carry = 0;
        for (int i = n - 1, j = m - 1, a, b; i >= 0 || j >= 0;
             i = (i - 1 >= 0 ? i - 1 : -1), j = (j - 1 >= 0 ? j - 1 : -1)) {
            a = i == -1 ? 0 : s1[i] - '0';
            b = j == -1 ? 0 : s2[j] - '0';
            int sum = a + b + carry;
            carry = sum / 2;
            ans.append(sum % 2);
        }
        if (carry == 1) {
            ans.append(carry);
        }
        return ans.reverse().toString();
    }

    public static void main(String[] args) {
        String a = "1010";
        String b = "1011";
        System.out.println(addBinary(a, b));
    }
}
