package leetcode.study.leetcode001_200;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/5/12 19:17
 * https://leetcode.cn/problems/multiply-strings/
 */
public class Leetcode043Multiply {
    public static String multiply(String num1, String num2) {
        // 103 456
        // 100个456是 45600
        // 0个456是       0
        // 3个456是    1368
        if (num1.equals("0") || num2.equals("0")) {
            return "0";
        }
        char[] s1 = num1.toCharArray();
        int n = s1.length;
        // s2 [4,5,6]
        // s1 [1,2,3]
        String ans = "0";
        for (int i = 0; i < n; i++) {
            String tmp = num2;
            for (int j = 0; j < n - i - 1; j++) {
                tmp = (tmp + "0");
            }
            String sum = multiplyAAndB(tmp, s1[i]);
            ans = add(ans, sum);
        }
        return ans;
    }

    public static String multiplyAAndB(String str, char m) {
        int x = m - '0';
        if (x == 0) {
            return "0";
        }
        String ans = str;
        for (int i = 0; i < x - 1; i++) {
            ans = add(ans, str);
        }
        return ans;
    }

    public static String add(String str1, String str2) {
        char[] s1 = str1.toCharArray();
        char[] s2 = str2.toCharArray();
        int n = s1.length;
        int m = s2.length;
        String s = "";
        int carry = 0;
        for (int j = m - 1, i = n - 1; j >= 0 || i >= 0;
             j = (j - 1 >= 0 ? j - 1 : -1), i = (i - 1 >= 0 ? i - 1 : -1)) {
            int a = j == -1 ? 0 : s2[j] - '0';
            int b = i == -1 ? 0 : s1[i] - '0';
            int sum = a + b + carry;
            int val = sum % 10;
            s = ((char) ((val % 10) + '0') + s);
            carry = sum / 10;
        }
        if (carry == 1) {
            s = (1 + s);
        }
        return s;
    }

    public static void main(String[] args) {
        String str1 = "9133";
        String str2 = "0";
        System.out.println(multiply(str1, str2));
        String s1 = "129";
        String s2 = "36";
        System.out.println(add(s1, s2));
        String s3 = "20";
        char m = '7';
        System.out.println(multiplyAAndB(s3, m));
    }
}
