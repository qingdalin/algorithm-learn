package leetcode.study.leetcode001_200;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/4/8 19:18
 * https://leetcode.cn/problems/string-to-integer-atoi/
 */
public class LeetCode008MyAtoi {
    public static int myAtoi(String str) {
        char[] s = str.toCharArray();
        int n = s.length;
        List<Character> list = new ArrayList<>();
        int sign = -2;
        boolean zero = false;
        sign = getNum(s, n, list, sign, zero);
        if (list.isEmpty()) {
            return 0;
        }
        long num, cur, ans = 0, offset = 1;
        for (int i = list.size() - 1; i >= 0; i--) {
            cur = (long) (list.get(i) - '0') * offset;
            offset *= 10;
            num = ans + (cur == 0 ? offset : cur);
            if (sign == 1 && num > Integer.MAX_VALUE) {
                return Integer.MAX_VALUE;
            }
            if (sign == -1 && -num < Integer.MIN_VALUE) {
                return Integer.MIN_VALUE;
            }
            ans += cur;
        }
        return sign == 1 ? (int) ans : (int) -ans;
    }

    private static int getNum(char[] s, int n, List<Character> list, int sign, boolean zero) {
        for (int i = 0; i < n; i++) {
            if (list.isEmpty() && s[i] == ' ') {
                if (zero || sign == 1 || sign == -1) {
                    break;
                }
                continue;
            }
            if (list.isEmpty() && sign == -2 && (s[i] == '+' || s[i] == '-')) {
                sign = s[i] == '+' ? 1 : -1;
                continue;
            }
            if (s[i] >= '0' && s[i] <= '9') {
                if (sign == -2) {
                    sign = 1;
                }
                if (!zero && s[i] == '0') {
                    zero = true;
                }
                if (list.isEmpty() && s[i] == '0') {
                    continue;
                }
                list.add(s[i]);
            } else {
                break;
            }
        }
        return sign;
    }

    public static void main(String[] args) {
        System.out.println(myAtoi("10000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000522545459"));
        System.out.println(myAtoi("2147483646"));
        System.out.println(myAtoi("  +  413"));
        System.out.println(myAtoi("20000000000000000000"));
        System.out.println(myAtoi("   +0 123"));
        System.out.println(myAtoi("-91283472332"));
        System.out.println(myAtoi("42"));
        System.out.println(myAtoi("1337c0d3"));
        System.out.println(myAtoi("0-1"));
        System.out.println(myAtoi("words and 987"));
    }
}
