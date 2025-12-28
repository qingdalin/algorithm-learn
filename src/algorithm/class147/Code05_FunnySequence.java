package algorithm.class147;

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
 * @date: 2025/1/11 14:46
 * // 有趣的数列(重要! 因子计数法)
 * // 求第n项卡特兰数，要求答案对p取模
 * // 1 <= n <= 10^6
 * // 1 <= p <= 10^9
 * // p可能不为质数
 * // 测试链接 : https://www.luogu.com.cn/problem/P3200
 * // 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例
 */
public class Code05_FunnySequence {
    public static int MAXN = 2000001;
    // minpf[i]==0代表是质数
    // minpf[i]!=0代表是合数，最小的质因子是minpf[i]
    public static int[] minpf = new int[MAXN];
    public static int[] prime = new int[MAXN];
    public static int[] counts = new int[MAXN];
    public static int cnt;
    public static int MOD = 100;
    public static int n, p;
    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StreamTokenizer in = new StreamTokenizer(bf);
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        in.nextToken(); n = (int) in.nval;
        in.nextToken(); p = (int) in.nval;
        out.println(compute(n, p));
        out.flush();
        out.close();
        bf.close();
    }

    private static long compute(int n, int p) {
        // 利用欧拉筛生成[2 ~ 2*n]范围上所有数的最小质因子
        // 如果x为质数，minpf[x] == 0
        // 如果x为合数，x的最小质因子为minpf[x]
        euler(2 * n);
        // 分母每个因子设置计数
        Arrays.fill(counts, 2, n + 1, -1);
        // 分子每个因子设置计数
        Arrays.fill(counts, n + 2, 2 * n + 1, 1);
        // 从大到小的每个数统计计数
        // 合数根据最小质因子来分解，变成更小数字的计数
        // 质数无法分解，计数确定，最后快速幂计算乘积
        for (int i = n * 2; i >= 2; i--) {
            if (minpf[i] != 0) {
                counts[minpf[i]] += counts[i];
                counts[i / minpf[i]] += counts[i];
                counts[i] = 0;
            }
        }
        long ans = 1;
        for (int i = 2; i <= 2 * n; i++) {
            if (counts[i] != 0) {
                ans = (ans * power(i, counts[i], p)) % p;
            }
        }
        return ans;
    }

    private static long power(long x, long p, long mod) {
        long ans = 1;
        while (p > 0) {
            if ((p & 1) == 1) {
                ans = ans * x % mod;
            }
            x = x * x % mod;
            p >>= 1;
        }
        return ans;
    }

    private static void euler(int n) {
        Arrays.fill(minpf, 2, n + 1, 0);
        cnt = 0;
        for (int i = 2; i <= n; i++) {
            if (minpf[i] == 0) {
                prime[cnt++] = i;
            }
            for (int j = 0; j < cnt; j++) {
                if (i * prime[j] > n) {
                    break;
                }
                minpf[i * prime[j]] = prime[j];
                if (i % prime[j] == 0) {
                    break;
                }
            }
        }
    }
}
