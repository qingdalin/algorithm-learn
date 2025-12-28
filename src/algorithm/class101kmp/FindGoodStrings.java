package algorithm.class101kmp;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2024/8/10 11:17
 * 给你两个长度为 n 的字符串 s1 和 s2 ，以及一个字符串 evil 。请你返回 好字符串 的数目。
 *
 * 好字符串 的定义为：它的长度为 n ，字典序大于等于 s1 ，字典序小于等于 s2 ，且不包含 evil 为子字符串。
 *
 * 由于答案可能很大，请你返回答案对 10^9 + 7 取余的结果。
 * https://leetcode.cn/problems/find-all-good-strings/description/
 */
public class FindGoodStrings {
    public static int MAXN = 501;
    public static int MAXM = 51;
    public static int MOD = 1000000007;
    public static int[] next = new int[MAXM];

    public static int[][][] dp = new int[MAXN][MAXM][2];

    public static void main(String[] args) {
        FindGoodStrings findGoodStrings = new FindGoodStrings();
        int goodStrings = findGoodStrings.findGoodStrings(8, "leetcode", "leetgoes", "leet");
        System.out.println(goodStrings);
    }

    public void build(int n, int m) {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                dp[i][j][0] = -1;
                dp[i][j][1] = -1;
            }
        }
    }
    public int findGoodStrings(int n, String str1, String str2, String evil) {
        char[] s1 = str1.toCharArray();
        char[] s2 = str2.toCharArray();
        char[] e = evil.toCharArray();
        int m = e.length;
        nextArray(e, m);
        build(n, m);
        int ans = f(s2, e, n, m, 0, 0, 0);
        build(n, m);
        ans = (ans - f(s1, e, n, m, 0, 0, 0) + MOD) % MOD;
        if (kmp(s1, e, n, m) == -1) {
            // 如果s1不包含evil则加一
            ans = (ans + 1) % MOD;
        }
        return ans;
    }

    private int kmp(char[] s1, char[] e, int n, int m) {
        int x = 0, y = 0;
        while (x < n && y < m) {
            if (s1[x] == e[y]) {
                x++;
                y++;
            } else if (y == 0) {
                x++;
            } else {
                y = next[y];
            }
        }
        return y == m ? x - y : -1;
    }

    // i:表示 s字符的0 - i-1
    // j:表示 e字符的0 - j-1
    // free:0和1表示是否可以自由选
    private int f(char[] s, char[] e, int n, int m, int i, int j, int free) {
        if (j == m) {
            // 匹配完整的e说明违法
            return 0;
        }
        if (i == n) {
            // i到n说明有一个合法字符
            return 1;
        }
        if (dp[i][j][free] != -1) {
            return dp[i][j][free];
        }
        char cur = s[i];
        int ans = 0;
        if (free == 0) {
            for (char pick = 'a'; pick < cur; pick++) {
                ans = (ans + f(s, e, n, m, i + 1, jump(e, j, pick) + 1, 1)) % MOD;
            }
            ans = (ans + f(s, e, n, m, i + 1, jump(e, j, cur) + 1, 0)) % MOD;
        } else {
            for (char pick = 'a'; pick <= 'z'; pick++) {
                ans = (ans + f(s, e, n, m, i + 1, jump(e, j, pick) + 1, 1)) % MOD;
            }
        }
        dp[i][j][free] = ans;
        return ans;
    }

    private int jump(char[] e, int j, char pick) {
        while (j >= 0 && pick != e[j]) {
            j = next[j];
        }
        return j;
    }

    private void nextArray(char[] e, int m) {
        next[0] = -1;
        next[1] = 0;
        int i = 2, cn = 0;
        while (i < m) {
            if (e[i - 1] == e[cn]) {
                next[i++] = ++cn;
            } else if (cn > 0) {
                cn = next[cn];
            } else {
                next[i++] = 0;
            }
        }
    }
}
