package algorithm.class103manacher;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2024/8/12 20:45
 * https://www.luogu.com.cn/problem/P5410
 */
public class ExpandKMP {
    public static int MAXN = 20000001;
    public static int n, m;
    public static int[] z = new int[MAXN];
    public static int[] e = new int[MAXN];
    public static char[] a, b;
    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        a = bf.readLine().toCharArray();
        m = a.length;
        b = bf.readLine().toCharArray();
        n = b.length;
        zArray();
        eArray();
        System.out.println(eor(z, n));
        System.out.println(eor(e, m));
        out.flush();
        out.close();
        bf.close();
    }

    private static long eor(int[] arr, int len) {
        long ans = 0;
        for (int i = 0; i < len; i++) {
            ans = (ans ^ (long) (i + 1) * (arr[i] + 1));
        }
        return ans;
    }

    private static void eArray() {
        for (int i = 0, c = 0, r = 0, len; i < m; i++) {
            len = r > i ? Math.min(r - i, z[i - c]) : 0;
            while (i + len < m && len < n && a[i + len] == b[len]) {
                len++;
            }
            if (i + len > r) {
                r = i + len;
                c = i;
            }
            e[i] = len;
        }
    }

    private static void zArray() {
        z[0] = n;
        for (int i = 1, c = 1, r = 1, len; i < n; i++) {
            len = r > i ? Math.min(r - i, z[i - c]) : 0;
            while (i + len < n && b[i + len] == b[len]) {
                len++;
            }
            if (i + len > r) {
                r = i + len;
                c = i;
            }
            z[i] = len;
        }
    }
}
