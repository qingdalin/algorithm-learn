package algorithm.class146;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/1/5 16:09
 * // 康托展开
 * // 数字从1到n，可以有很多排列，给出具体的一个排列，求该排列的名次，答案对 998244353 取模
 * // 1 <= n <= 10^6
 * // 测试链接 : https://www.luogu.com.cn/problem/P5367
 * // 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例
 */
public class Code01_CantorExpansion {
    public static int MAXN = 1000001;
    public static int MOD = 998244353;
    public static long[] fac = new long[MAXN];
    public static int[] arr = new int[MAXN];
    public static int[] tree = new int[MAXN];
    public static int n;
    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StreamTokenizer in = new StreamTokenizer(bf);
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        in.nextToken(); n = (int) in.nval;
        for (int i = 1; i <= n; i++) {
            in.nextToken(); arr[i] = (int) in.nval;
        }
        out.println(compute());
        out.flush();
        out.close();
        bf.close();
    }

    private static long compute() {
        fac[0] = 1;
        for (int i = 1; i <= n; i++) {
            fac[i] = fac[i - 1] * i % MOD;
        }
        for (int i = 1; i <= n; i++) {
            add(arr[i], 1);
        }
        long ans = 0;
        for (int i = 1; i <= n; i++) {
            ans = (ans + sum(arr[i] - 1) * fac[n - i] % MOD) % MOD;
            add(arr[i], -1);
        }
        // 康托展开是从0开始排名，题目要求从1，最后ans+1
        return (ans + 1) % MOD;
    }

    private static long sum(int i) {
        long ans = 0;
        while (i > 0) {
            ans += tree[i];
            i -= lowbit(i);
        }
        return ans;
    }

    private static void add(int i, int v) {
        while (i <= n) {
            tree[i] += v;
            i += lowbit(i);
        }
    }

    private static int lowbit(int i) {
        return i & -i;
    }
}
