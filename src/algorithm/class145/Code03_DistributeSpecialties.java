package algorithm.class145;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/1/5 10:48
 * // 分特产
 * // 一共有m种特产，arr[i]表示i种特产有几个
 * // 一共有n个同学，每个同学至少要得到一个特产
 * // 返回分配特产的方法数，答案对 1000000007 取模
 * // 0 <= n、m <= 1000
 * // 0 <= arr[i] <= 1000
 * // 测试链接 : https://www.luogu.com.cn/problem/P5505
 * // 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例
 */
public class Code03_DistributeSpecialties {
    public static int MAXN = 1001;
    public static int MAXK = 2 * MAXN;
    public static int MOD = 1000000007;
    public static long[][] c = new long[MAXK][MAXK];
    public static int[] arr = new int[MAXN];
    public static long[] g = new long[MAXN];
    public static int n, k, m;
    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StreamTokenizer in = new StreamTokenizer(bf);
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        in.nextToken(); n = (int) in.nval;
        in.nextToken(); m = (int) in.nval;
        for (int i = 1; i <= m; i++) {
            in.nextToken();
            arr[i] = (int) in.nval;
        }
        k = 2 * n;
        out.println(compute());
        out.flush();
        out.close();
        bf.close();
    }

    private static long compute() {
        // 组合公式从n里选k个，杨辉三角
        for (int i = 0; i <= k; i++) {
            c[i][0] = 1;
            for (int j = 1; j <= i; j++) {
                c[i][j] = (c[i - 1][j] + c[i - 1][j - 1]) % MOD;
            }
        }
        // g(i):特产一定分完的情况下钦定至少i个人没有特产
        for (int i = 0; i < n; i++) {
            g[i] = c[n][i]; // 先选出哪i个人没有得到特产
            for (int j = 1; j <= m; j++) {
                // m个相同的物品分给n个不同的人，隔板法，C(n+m-1,n-1)
                g[i] = (int) ((g[i] * c[arr[j] + n - i - 1][n - i - 1]) % MOD);
            }
        }
        g[n] = 0; // 特产一定分完，n个人没有特产的数量是0
        long ans = 0;
        for (int i = 0; i <= n; i++) {
            if ((i & 1) == 0) {
                ans = (ans + g[i]) % MOD;
            } else {
                // -1和mod-1同余
                ans = (ans + g[i] * (MOD - 1) % MOD) % MOD;
            }
        }
        return ans;
    }
}
