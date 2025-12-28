package leetcode.study.leetcode401_600;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/8/30 14:54
 * https://leetcode.cn/problems/magical-string/
 */
public class Leetcode481MagicalString {
    public static int magicalString(int n) {
        // 1 22 11 2 1 22 1 22 11 2 11 22
        // 1 22 11 2 1 22 1 22 11 2 11 22
        // 1 2  2  1 1 2  1 2  2  1 2  2
        if (n < 4) {
            return 1;
        }
        int cnt = 1;
        char[] s = new char[n];
        s[0] = '1';
        s[1] = s[2] = '2';
        int i = 2, j = 3;
        while (j < n) {
            int size = s[i++] - '0';
            int num = 3 - (s[j - 1] - '0');
            while (size > 0 && j < n) {
                //s[j] = (char) ('0' + num);
                if (num == 1) {
                    cnt++;
                }
                size--;
                j++;
            }
        }
        return cnt;
    }

    public static void main(String[] args) {
        System.out.println(magicalString(6));
    }
}
