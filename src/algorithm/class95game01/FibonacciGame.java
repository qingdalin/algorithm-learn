package algorithm.class95game01;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2024/7/28 17:55
 * https://www.luogu.com.cn/problem/P6487
 * 斐波那契博弈
 */
public class FibonacciGame {
    public static long MAXN = 1000000000000001L;
    public static int MAXM = 101;
    public static long[] fib = new long[MAXM];
    public static int size;
    public static long n;
    public static void main(String[] args) throws IOException {
        build();
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StreamTokenizer st = new StreamTokenizer(bf);
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        while (st.nextToken() != StreamTokenizer.TT_EOF) {
            n = (long) st.nval;
            System.out.println(compute((long) n));
        }
        out.flush();
        out.close();
        bf.close();
    }

    private static void build() {
        fib[0] = 1;
        fib[1] = 2;
        size = 1;
        // 形成斐波那契数列
        while (fib[size] <= MAXN) {
            fib[size + 1] = fib[size] + fib[size - 1];
            size++;
        }
    }

    private static long compute(long n) {
        // 如果一个数是斐波纳契数，必须全部取走才能获取，不是斐波那契，可以由不相邻的斐波那契数相加得到，取走最小的分解数
        long ans = -1L;
        long find = 0;
        while (n != 1 && n != 2) {
            // 找到小于n的斐波那契数
            find = bs(n);
            if (n == find) {
                ans = find;
                break;
            } else {
                n -= find;
            }
        }
        if (ans != -1) {
            return ans;
        } else {
            return n;
        }
    }

    private static long bs(long n) {
        int l = 0, r = size, m = 0;
        long ans = -1;
        while (l <= r) {
            m = (l + r) / 2;
            if (fib[m] <= n) {
                ans = fib[m];
                l = m + 1;
            } else {
                r = m - 1;
            }
        }
        return ans;
    }
}
