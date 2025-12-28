package algorithm.class109indextree02;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2024/8/25 9:10
 * https://www.luogu.com.cn/problem/P1908
 * 归并分治的方法
 */
public class NumberOfReversePair1 {
    public static int MAXN = 500001;

    // 维护差分数组的树状数组信息
    public static int[] arr = new int[MAXN];
    public static int[] help = new int[MAXN];

    public static int n;

    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StreamTokenizer in = new StreamTokenizer(bf);
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        while (in.nextToken() != StreamTokenizer.TT_EOF) {
            n = (int) in.nval;
            for (int i = 1; i <= n; i++) {
                in.nextToken();
                arr[i] = (int) in.nval;
            }
            System.out.println(f(1, n));
        }
        out.flush();
        out.close();
        bf.close();
    }

    private static long f(int l, int r) {
        if (l == r) {
            return 0;
        }
        int m = (l + r) / 2;
        return f(l, m) + f(m + 1, r) + merge(l, m, r);
    }

    private static long merge(int l, int m, int r) {
        long ans = 0;
        // l.. ...m
        // m+1....r
        for (int i = m, j = r; i >= l; i--) {
            while (j >= m + 1 && arr[i] <= arr[j]) {
                // arr[i] <= arr[j] j--
                j--;
            }
            ans += j - m;
        }
        int i = l;
        int a = l;
        int b = m + 1;
        while (a <= m && b <= r) {
            help[i++] = arr[a] <= arr[b] ? arr[a++] : arr[b++];
        }
        while (a <= m) {
            help[i++] = arr[a++];
        }
        while (b <= r) {
            help[i++] = arr[b++];
        }
        for (int j = l; j <= r; j++) {
            arr[j] = help[j];
        }
        return ans;
    }
}
