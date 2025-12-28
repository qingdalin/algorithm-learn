package algorithm.class151;

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
 * @date: 2025/1/19 16:31
 * // 表格填数
 * // 给定一个长度为n的数组arr，arr[i]表示i位置上方的正方形格子数量
 * // 那么从1位置到n位置，每个位置就是一个直方图，所有的直方图紧靠在一起
 * // 在这片区域中，你要放入k个相同数字，不能有任意两个数字在同一行或者同一列
 * // 注意在这片区域中，如果某一行中间断开了，使得两个数字无法在这一行连通，则不算违规
 * // 返回填入数字的方法数，答案对 1000000007 取模
 * // 1 <= n、k <= 500    0 <= arr[i] <= 10^6
 * // 测试链接 : https://www.luogu.com.cn/problem/P6453
 * // 因为本题给定的可用空间很少，所以数组为int类型，不用long类型
 * // 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例
 */
public class Code05_Periodni {
    public static int MAXN = 501;
    public static int MAXH = 1000000;
    public static int MOD = 1000000007;
    public static int[] arr = new int[MAXN];
    public static int[] left = new int[MAXN];
    public static int[] right = new int[MAXN];
    public static int[] stack = new int[MAXN];
    public static int[] size = new int[MAXN];
    // tmp是动态规划的临时结果,tmp[i]:表示当前树头节点严格是i，形成多少种方法
    public static int[] tmp = new int[MAXN];
    public static int[][] dp = new int[MAXN][MAXN];
    public static int[] fac = new int[MAXH + 1];
    public static int[] inv = new int[MAXH + 1];
    public static int n, k;
    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StreamTokenizer in = new StreamTokenizer(bf);
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        in.nextToken(); n = (int) in.nval;
        in.nextToken(); k = (int) in.nval;
        for (int i = 1; i <= n; i++) {
            in.nextToken();
            arr[i] = (int) in.nval;
        }
        out.println(compute());
        out.flush();
        out.close();
        bf.close();
    }

    private static int compute() {
        build();
        dp[0][0] = 1;
        dfs(stack[1], 0);
        return dp[stack[1]][k];
    }

    private static void dfs(int u, int fa) {
        if (u == 0) {
            return;
        }
        dfs(left[u], u);
        dfs(right[u], u);
        size[u] = size[left[u]] + size[right[u]] + 1;
        Arrays.fill(tmp, 0, k + 1, 0);
        for (int l = 0; l <= Math.min(size[left[u]], k); l++) {
            for (int r = 0; r <= Math.min(size[right[u]], k - l); r++) {
                // 错误写法                                                               在这
                // tmp[l + r] = (int) (tmp[l + r] + (long) dp[left[u]][l] * dp[right[u]][l] % MOD) % MOD;
                tmp[l + r] = (int) (tmp[l + r] + (long) dp[left[u]][l] * dp[right[u]][r] % MOD) % MOD;
            }
        }
        for (int i = 0; i <= Math.min(size[u], k); i++) {
            for (int p = 0; p <= i; p++) {
                // 错误写法，tmp[p]后边忘记%MOD dp[u][i] = (int) (dp[u][i] + (long) c(size[u] - p, i - p) * c(arr[u] - arr[fa], i - p) % MOD
                //    * fac[i - p] % MOD * tmp[p] % MOD) % MOD;
                dp[u][i] = (int) (dp[u][i] + (long) c(size[u] - p, i - p) * c(arr[u] - arr[fa], i - p) % MOD
                    * fac[i - p] % MOD * tmp[p] % MOD) % MOD;
            }
        }
    }

    private static int c(int n, int k) {
        return k > n ? 0 : (int) ((long) fac[n] * inv[k] % MOD * inv[n - k] % MOD);
    }

    private static void build() {
        fac[0] = fac[1] = inv[0] = 1;
        for (int i = 2; i <= MAXH; i++) {
            // 错误写法，括号没包住fac[i] = (int) ((long) i * fac[i - 1]) % MOD;
            fac[i] = (int) ((long) i * fac[i - 1] % MOD);
        }
        inv[MAXH] = power(fac[MAXH], MOD - 2);
        for (int i = MAXH - 1; i >= 1; i--) {
            // 错误写法，括号没包住 inv[i] = (int) ((long) inv[i + 1] * (i + 1) % MOD);
            inv[i] = (int) ((long) inv[i + 1] * (i + 1) % MOD);
        }
        for (int i = 1, pos = 0, top = 0; i <= n; i++) {
            pos = top;
            // 小根堆，大压小
            while (pos > 0 && arr[stack[pos]] > arr[i]) {
                pos--;
            }
            if (pos > 0) {
                // 我压这谁，我就是谁的右子树
                right[stack[pos]] = i;
            }
            if (top > pos) {
                left[i] = stack[pos + 1];
            }
            stack[++pos] = i;
            top = pos;
        }
    }

    private static int power(long x, long p) {
        long ans = 1;
        while (p > 0) {
            if ((p & 1) == 1) {
                ans = ans * x % MOD;
            }
            x = x * x % MOD;
            p >>= 1;
        }
        return (int) ans;
    }
}
