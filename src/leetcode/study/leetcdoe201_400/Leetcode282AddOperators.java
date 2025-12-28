package leetcode.study.leetcdoe201_400;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/7/23 19:47
 * https://leetcode.cn/problems/expression-add-operators/
 */
public class Leetcode282AddOperators {
    public static int n, t;
    public static char[] s;
    public static List<Character> opList = Arrays.asList('+', '-', '*');
    public static List<String> addOperators(String num, int target) {
        s = num.toCharArray();
        n = s.length;
        t = target;
        List<String> ans = new ArrayList<>();
        StringBuilder expr = new StringBuilder();
        f(0, 0, 0, ans, expr);
        return ans;
    }

    private static void f(int i, long res, long mul, List<String> ans, StringBuilder expr) {
        if (i == n) {
            if (res == t) {
                ans.add(expr.toString());
            }
        } else {
            int signIndex = expr.length();
            if (i > 0) {
                expr.append(0);
            }
            long val = 0;
            for (int j = i; j < n && (j == i || s[i] != '0'); j++) {
                val = val * 10 + s[j] - '0';
                expr.append(s[j]);
                if (i == 0) {
                    f(j + 1, val, val, ans, expr);
                } else {
                    expr.setCharAt(signIndex, '+');
                    f(j + 1, res + val, val, ans, expr);
                    expr.setCharAt(signIndex, '-');
                    f(j + 1, res - val, -val, ans, expr);
                    expr.setCharAt(signIndex, '*');
                    f(j + 1, res - mul + val * mul, val * mul, ans, expr);
                }
            }
            expr.setLength(signIndex);
        }
    }

    public static void main(String[] args) {
        String num = "105";
        int target = 5;
        System.out.println(addOperators(num, target));
    }
}
