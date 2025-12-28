package algorithm.class85dp20;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2024/7/14 11:33
 * https://www.luogu.com.cn/problem/P3413
 */
public class MengNum {
    public static int MAXN = 1001;
    public static int[][][][] dp = new int[MAXN][11][11][2];
    public static int mod = 1000000007;
    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StreamTokenizer st = new StreamTokenizer(bf);
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        String[] str = bf.readLine().split(" ");
        System.out.println(compute(str[0].toCharArray(), str[1].toCharArray()));
        out.flush();
        out.close();
        bf.close();
    }

    private static int compute(char[] l, char[] r) {
        int ans = (cnt(r) - cnt(l) + mod) % mod;
        if (check(l)) {
            ans = (ans + 1) % mod;
        }
        return ans;
    }

    private static boolean check(char[] num) {
        for (int i = 0, pp = -2, p = -1; i < num.length; i++, pp++, p++) {
            if (pp >= 0 && num[pp] == num[i]) {
                return true;
            }
            if (p >= 0 && num[p] == num[i]) {
                return true;
            }
        }
        return false;
    }

    private static int cnt(char[] num) {
        if (num[0] == '0') {
            return 0;
        }
        // 计算num中所有不是萌数，用all减去不是萌数的
        long all = 0;
        long base = 1;
        int n = num.length;
        for (int i = n - 1; i >= 0; i--) {
            all = (all + base * ( num[i] - '0')) % mod;
            base = (base * 10) % mod;
        }
        build(n);
        return (int) ((all - f1(num, 0, 10, 10, 0) + mod) % mod);
    }

    // i 表示当前来到的位数
    // pp前两位数字
    // p 前一位数字
    // free是否可以自由选择
    private static int f1(char[] num, int i, int pp, int p, int free) {
        if (i == num.length) {
            return 1;
        }
        if (dp[i][pp][p][free] != -1) {
            return dp[i][pp][p][free];
        }
        int ans = 0;
        if (free == 0) {
            if (p == 10) {
                // 到的是最高位,不要当前位往下进行
                ans = (ans + f1(num, i + 1, 10, 10, 1)) % mod;
                for (int cur = 1; cur < num[i] - '0'; cur++) {
                    ans = (ans + f1(num, i + 1, p, cur, 1)) % mod;
                }
                ans = (ans + f1(num, i + 1, p, num[i] - '0', 0)) % mod;
            } else {
                // 不能自由选择，前边已经选择了
                for (int cur = 0; cur < num[i] - '0'; cur++) {
                    if (pp != cur && p != cur) {
                        ans = (ans + f1(num, i + 1, p, cur, 1)) % mod;
                    }
                }
                if (pp != num[i] - '0' && p != num[i] - '0') {
                    ans = (ans + f1(num, i + 1, p, num[i] - '0', 0)) % mod;
                }
            }
        } else {
            if (p == 10) {
                ans = (ans + f1(num, i + 1, 10, 10, 1)) % mod;
                for (int cur = 1; cur <= 9; cur++) {
                    ans = (ans + f1(num, i + 1, p, cur, 1)) % mod;
                }
            } else {
                for (int cur = 0; cur <= 9; cur++) {
                    if (pp != cur && p != cur) {
                        ans = (ans + f1(num, i + 1, p, cur, 1)) % mod;
                    }
                }
            }
        }
        dp[i][pp][p][free] = ans;
        return ans;
    }

    private static void build(int n) {
        for (int i = 0; i <= n; i++) {
            for (int j = 0; j <= 10; j++) {
                for (int k = 0; k <= 10; k++) {
                    dp[i][j][k][0] = -1;
                    dp[i][j][k][1] = -1;
                }
            }
        }
    }
}
