package leetcode.study.leetcdoe201_400;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/7/14 19:34
 * https://leetcode.cn/problems/different-ways-to-add-parentheses/
 */
public class Leetcode241DiffWaysToCompute {
    public static int add = -1;
    public static int sub = -1;
    public static int mul = -1;
    public static List<Integer> diffWaysToCompute(String str) {
        char[] s = str.toCharArray();
        int n = s.length;
        List<Integer> op = getOpList(s, n);
        int size = op.size();
        List[][] dp = new List[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                dp[i][j] = new ArrayList();
            }
        }
        return dfs(dp, 0, size - 1, op);
    }

    private static List<Integer> dfs(List[][] dp, int l, int r, List<Integer> op) {
        if (!dp[l][r].isEmpty()) {
            return dp[l][r];
        }
        if (l == r) {
            dp[l][r].add(op.get(l));
        } else {
            for (int i = l; i < r; i += 2) {
                List<Integer> left = dfs(dp, l, i, op);
                List<Integer> right = dfs(dp, i + 2, r, op);
                for (int lv : left) {
                    for (int rv : right) {
                        if (op.get(i + 1) == add) {
                            dp[l][r].add(lv + rv);
                        } else if (op.get(i + 1) == sub){
                            dp[l][r].add(lv - rv);
                        } else {
                            dp[l][r].add(lv * rv);
                        }
                    }
                }
            }
        }
        return dp[l][r];
    }

    private static List<Integer> getOpList(char[] s, int n) {
        List<Integer> op = new ArrayList<>();
        for (int i = 0; i < n;) {
            if (!Character.isDigit(s[i])) {
                if (s[i] == '+') {
                    op.add(add);
                } else if (s[i] == '-') {
                    op.add(sub);
                } else {
                    op.add(mul);
                }
                i++;
            } else {
                int num = 0;
                while (i < n && Character.isDigit(s[i])) {
                    num = num * 10 + s[i++] - '0';
                }
                op.add(num);
            }
        }
        return op;
    }
}
