package algorithm.class117;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2024/9/20 21:37
 * https://www.luogu.com.cn/problem/P1890
 */
public class Code03_SparseTableGCD {
    public static int MAXN = 1001;
    // 2的15次方大于50001，
    public static int LIMIT = 10;
    public static int n;
    public static int[] arr = new int[MAXN];
    // 小于等于i的2的几次方
    public static int[] log2 = new int[MAXN];
    public static int[][] stgcd = new int[MAXN][LIMIT];
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StreamTokenizer in = new StreamTokenizer(br);
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        in.nextToken();
        n = (int) in.nval;
        in.nextToken();
        int m = (int) in.nval;
        for (int i = 1; i <= n; i++) {
            in.nextToken();
            arr[i] = (int) in.nval;
        }
        build();
        for (int i = 1, a, b; i <= m; i++) {
            in.nextToken(); a = (int) in.nval;
            in.nextToken(); b = (int) in.nval;
            System.out.println(query(a, b));
        }
        out.println();
        out.flush();
        out.close();
        br.close();
    }

    private static int query(int l, int r) {
        int p = log2[r - l + 1];
        return gcd(stgcd[l][p], stgcd[r - (1 << p) + 1][p]);
    }

    private static void build() {
        log2[0] = -1;
        for (int i = 1; i <= n; i++) {
            log2[i] = log2[i >> 1] + 1;
            stgcd[i][0] = arr[i];
        }
        for (int p = 1; p <= log2[n]; p++) {
            for (int i = 1; i + (1 << (p - 1)) <= n; i++) {
                stgcd[i][p] = gcd(stgcd[i][p - 1], stgcd[i + (1 << (p - 1))][p - 1]);
            }
        }
    }

    private static int gcd(int a, int b) {
        return b == 0 ? a : gcd(b, a % b);
    }
}
