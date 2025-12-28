package leetcode.study.leetcdoe201_400;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/7/26 10:06
 * https://leetcode.cn/problems/additive-number/
 */
public class Leetcode306IsAdditiveNumber {
    public static boolean isAdditiveNumber(String str) {
        char[] s = str.toCharArray();
        int n = s.length;
        if (n < 3) {
            return false;
        }
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                long first = getNum(s, i, j);
                if (first == -1) {
                    continue;
                }
                for (int k = j + 1; k < n; k++) {
                    long second = getNum(s, j, k);
                    if (second == -1) {
                        continue;
                    }

                }
            }
        }
        return false;
    }

    private static long getNum(char[] s, int i, int j) {
        if (i + 1 < j && s[i] == '0') {
            return -1;
        }
        long num = 0;
        for (int k = i; k < j; k++) {
            num = num * 10 + s[k] - '0';
        }
        return num;
    }

    public static void main(String[] args) {
        System.out.println(Long.MAX_VALUE);
    }
}
