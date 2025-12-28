package algorithm.class105strhash;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2024/8/18 17:07
 * https://www.luogu.com.cn/problem/P3763
 */
public class LikeLotusRoot {
    public static int MAXN = 100001;
    public static int base = 499;
    public static int t;
    public static long[] power = new long[MAXN];
    public static long[] hashs = new long[MAXN];
    public static long[] hashp = new long[MAXN];
    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        t = Integer.parseInt(bf.readLine());
        for (int i = 0; i < t; i++) {
            System.out.println(compute(bf.readLine().toCharArray(), bf.readLine().toCharArray(), 3));
        }
        out.flush();
        out.close();
        bf.close();
    }

    private static int compute(char[] s, char[] p, int k) {
        int n = s.length;
        int m = p.length;
        if (n < m) {
            return 0;
        }
        build(s, p, n, m);
        int ans = 0;
        for (int i = 0; i <= n - m; i++) {
            if (check(i, i + m - 1, k)) {
                ans++;
            }
        }
        return ans;
    }

    private static boolean check(int l1, int r1, int k) {
        int l2 = 0, diff = 0;
        while (l1 <= r1 && diff <= k) {
            int l = 1;
            int r = r1 - l1 + 1;
            int m, len = 0;
            while (l <= r) {
                m = (l + r) / 2;
                if (same(l1, l2, m)) {
                    len = m;
                    l = m + 1;
                } else {
                    r = m - 1;
                }
            }
            if (l1 + len <= r1) {
                diff++;
            }
            l1 += len + 1;
            l2 += len + 1;
        }
        return diff <= k;
    }

    private static boolean same(int l1, int l2, int len) {
        return hash(l1, l1 + len - 1, hashs) == hash(l2, l2 + len - 1, hashp);
    }

    private static long hash(int l, int r, long[] hash) {
        long ans = hash[r];
        ans -= l == 0 ?  0 : hash[l - 1] * power[r - l + 1];
        return ans;
    }

    private static void build(char[] s, char[] p, int n, int m) {
        power[0] = 1;
        for (int i = 1; i < MAXN; i++) {
            power[i] = power[i - 1] * base;
        }
        hashs[0] = s[0] - 'a' + 1;
        for (int i = 1; i < n; i++) {
            hashs[i] = hashs[i - 1] * base + s[i] - 'a' + 1;
        }
        hashp[0] = p[0] - 'a' + 1;
        for (int i = 1; i < m; i++) {
            hashp[i] = hashp[i - 1] * base + p[i] - 'a' + 1;
        }
    }
}
