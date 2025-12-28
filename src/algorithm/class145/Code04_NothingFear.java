package algorithm.class145;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;
import java.util.Arrays;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/1/5 11:43
 * // 已经没有什么好害怕的了
 * // 给定两个长度为n的数组，a[i]表示第i个糖果的能量，b[i]表示第i个药片的能量
 * // 所有能量数值都不相同，每一个糖果要选一个药片进行配对
 * // 如果配对之后，糖果能量 > 药片能量，称为糖果大的配对
 * // 如果配对之后，糖果能量 < 药片能量，称为药片大的配对
 * // 希望做到，糖果大的配对数量 = 药片大的配对数量 + k
 * // 返回配对方法数，答案对 1000000009 取模
 * // 举例，a = [5, 35, 15, 45]，b = [40, 20, 10, 30]，k = 2，返回4，因为有4种配对方法
 * // (5-40，35-20，15-10，45-30)、(5-40，35-30，15-10，45-20)
 * // (5-20，35-30，15-10，45-40)、(5-30，35-20，15-10，45-40)
 * // 1 <= n <= 2000
 * // 0 <= k <= n
 * // 测试链接 : https://www.luogu.com.cn/problem/P4859
 * // 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例
 */
public class Code04_NothingFear {
    public static int MAXN = 2001;
    public static int MOD = 1000000009;
    public static long[][] c = new long[MAXN][MAXN];
    public static long[][] dp = new long[MAXN][MAXN];
    public static int[] a = new int[MAXN];
    public static int[] b = new int[MAXN];
    public static int[] small = new int[MAXN];
    public static long[] g = new long[MAXN];
    public static long[] fac = new long[MAXN];
    public static int n, k;
    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StreamTokenizer in = new StreamTokenizer(bf);
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        in.nextToken(); n = (int) in.nval;
        in.nextToken(); k = (int) in.nval;
        for (int i = 1; i <= n; i++) {
            in.nextToken();
            a[i] = (int) in.nval;
        }
        for (int i = 1; i <= n; i++) {
            in.nextToken();
            b[i] = (int) in.nval;
        }
        // a:糖果
        // b:药片
        // 糖果刚好比药品大的对数是k
        // a - b = k
        // a + b = n
        // a = (n+k)/2
        if (((n + k) & 1) == 0) {
            k = (n + k) / 2;
            out.println(compute());
        } else {
            out.println(0);
        }

        out.flush();
        out.close();
        bf.close();
    }

    private static long compute() {
        build();
        Arrays.sort(a, 1, n + 1);
        Arrays.sort(b, 1, n + 1);
        // 排序，预处理每个糖果大于药片的个数
        for (int i = 1, cnt = 0; i <= n; i++) {
            while (cnt + 1 <= n && b[cnt + 1] < a[i]) {
                // 不越界并且糖果大于药片，计数++
                cnt++;
            }
            small[i] = cnt;
        }
        // dp[i][j]:1...i个糖果中一定选j个糖果作为钦定，一定形成糖果大，不关心非钦定糖果的配对，一共有多少方法
        dp[0][0] = 1;
        for (int i = 1; i <= n; i++) {
            dp[i][0] = dp[i - 1][0];
            for (int j = 1; j <= i; j++) {
                // 不选当前的糖果数量: 去前i-1个糖果选j个 dp[i-1][j]
                // 选当前的糖果数量: 去前i-1个糖果选j-1个 dp[i-1][j-1] * (比当前小的药片数再减去已经选的j-1个)
                dp[i][j] = (dp[i - 1][j] + dp[i - 1][j - 1] * (small[i] - (j - 1))) % MOD;
            }
        }
        // g(i):所有糖果中一定选i个糖果作为钦定，一定形成糖果大，关心非钦定糖果的配对，一共有多少方法
        // g(i) = (n-i)!*dp[n][i]
        // todo i从1和0开始都能过
        for (int i = 0; i <= n; i++) {
            g[i] = fac[n - i] * dp[n][i] % MOD;
        }
        long ans = 0;
        for (int i = k; i <= n; i++) {
            if (((i - k) & 1) == 0) {
                ans = (ans + g[i] * c[i][k] % MOD) % MOD;
            } else {
                // 错误写法，少mod一次，long*long溢出
                // ans = (ans + g[i] * c[i][k] * (MOD - 1) % MOD) % MOD;
                ans = (ans + g[i] * c[i][k] % MOD * (MOD - 1) % MOD) % MOD;
            }
        }
        return ans;
    }

    private static void build() {
        fac[0] = 1;
        for (int i = 1; i <= n; i++) {
            fac[i] = ((long) i * fac[i - 1]) % MOD;
        }
        for (int i = 0; i <= n; i++) {
            c[i][0] = 1;
            for (int j = 1; j <= i; j++) {
                c[i][j] = (c[i - 1][j] + c[i - 1][j - 1]) % MOD;
            }
        }
    }
}
