package algorithm.class102ac;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2024/8/11 12:57
 * https://www.luogu.com.cn/problem/P3311
 */
public class Counting {
    public static int MAXN = 1301;
    public static int MAXS = 2001;
    public static int MOD = 1000000007;

    public static char[] num;

    // 结尾的编号
    // ac自动机
    public static int[][] tree = new int[MAXS][26];
    public static int[] fail = new int[MAXS];
    public static int cnt = 0;

    public static int m, n;
    public static boolean[] alert = new boolean[MAXS];
    public static int[] queue = new int[MAXS];

    public static int[][][][] dp = new int[MAXN][MAXS][2][2];

    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StreamTokenizer st = new StreamTokenizer(bf);
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        num = bf.readLine().toCharArray();
        n = num.length;
        m = Integer.parseInt(bf.readLine());
        for (int i = 1; i <= m; i++) {
            insert(bf.readLine());
        }
        setFail();
        clear();
        //System.out.println(f1(0, 0, 0, 0));
        System.out.println(f2(0, 0, 0, 0));
        out.flush();
        out.close();
        bf.close();
    }

    private static int f2(int i, int j, int has, int free) {
        if (alert[j]) {
            return 0;
        }
        if (i == n) {
            return has;
        }
        if (dp[i][j][has][free] != -1) {
            return dp[i][j][has][free];
        }
        int limit = free == 0 ? num[i] - '0' : 9;
        int ans = 0;
        for (int pick = 0; pick <= limit; pick++) {
            ans = (ans + f2(i + 1, has == 0 && pick == 0 ? 0 : tree[j][pick], has == 0 && pick == 0 ? 0 : 1,
                free == 0 && pick == limit ? 0 : 1)) % MOD;
        }

        dp[i][j][has][free] = ans;
        return ans;
    }

    private static int f1(int i, int j, int has, int free) {
        if (alert[j]) {
            return 0;
        }
        if (i == n) {
            return has;
        }
        if (dp[i][j][has][free] != -1) {
            return dp[i][j][has][free];
        }
        int cur = num[i] - '0';
        int ans = 0;
        if (has == 0) {
            if (free == 0) {
                ans = (ans + f1(i + 1, j, has, 1)) % MOD;
                for (int pick = 1; pick < cur; pick++) {
                    ans = (ans + f1(i + 1, tree[j][pick], 1, 1)) % MOD;
                }
                ans = (ans + f1(i + 1, tree[j][cur], 1, 0)) % MOD;
            } else {
                ans = (ans + f1(i + 1, j, has, 1)) % MOD;
                for (int pick = 1; pick <= 9; pick++) {
                    ans = (ans + f1(i + 1, tree[j][pick], 1, 1)) % MOD;
                }
            }
        } else {
            if (free == 0) {
                for (int pick = 0; pick < cur; pick++) {
                    ans = (ans + f1(i + 1, tree[j][pick], 1, 1)) % MOD;
                }
                ans = (ans + f1(i + 1, tree[j][cur], 1, 0)) % MOD;
            } else {
                for (int pick = 0; pick <= 9; pick++) {
                    ans = (ans + f1(i + 1, tree[j][pick], 1, 1)) % MOD;
                }
            }
        }
        dp[i][j][has][free] = ans;
        return ans;
    }

    private static void clear() {
        for (int i = 0; i <= n; i++) {
            for (int j = 0; j <= cnt; j++) {
                dp[i][j][0][0] = -1;
                dp[i][j][0][1] = -1;
                dp[i][j][1][0] = -1;
                dp[i][j][1][1] = -1;
            }
        }
    }

    private static void setFail() {
        int l = 0, r= 0;
        for (int i = 0; i <= 9; i++) {
            if (tree[0][i] > 0) {
                queue[r++] = tree[0][i];
            }
        }
        while (l < r) {
            int u = queue[l++];
            for (int i = 0; i <= 9; i++) {
                if (tree[u][i] == 0) {
                    tree[u][i] = tree[fail[u]][i];
                } else {
                    fail[tree[u][i]] = tree[fail[u]][i];
                    queue[r++] = tree[u][i];
                }
            }
            alert[u] |= alert[fail[u]];
        }
    }

    private static void insert(String word) {
        char[] s = word.toCharArray();
        int u = 0;
        for (int i = 0, c; i < s.length; i++) {
            c = s[i] - '0';
            if (tree[u][c] == 0) {
                tree[u][c] = ++cnt;
            }
            u = tree[u][c];
        }
        alert[u] = true;
    }
}
