package leetcode.leetcodeweek.test2026.test483;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2026/1/5 21:22
 * https://leetcode.cn/problems/largest-even-number/
 */
public class LargestEven {
    public String largestEven(String str) {
        int n = str.length();
        while (n > 0 && str.charAt(n - 1) == 1) {
            n--;
        }
        return str.substring(0, n);
    }

    public String largestEven1(String str) {
        char[] s = str.toCharArray();
        StringBuilder sb = new StringBuilder();
        boolean flag = true;
        for (int i = s.length - 1; i >= 0; i--) {
            if (flag && s[i] == '1') {
                continue;
            }
            flag = false;
            sb.insert(0, s[i]);
        }
        return sb.toString();
    }
}
