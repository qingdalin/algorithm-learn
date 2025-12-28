package algorithm.class98power;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2024/8/3 14:30
 * https://www.luogu.com.cn/problem/P1226
 */
public class ApowerB {
    public static int a, b, p;
    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StreamTokenizer st = new StreamTokenizer(bf);
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        while (st.nextToken() != StreamTokenizer.TT_EOF) {
            a = (int) st.nval;
            st.nextToken(); b = (int) st.nval;
            st.nextToken(); p = (int) st.nval;
            System.out.println(a + "^" + b + " mod " + p + "=" + power(a, b, p));
        }
        out.flush();
        out.close();
        bf.close();
    }

    private static long power(long x, long n, int mod) {
        long ans = 1;
        while (n > 0) {
            if ((n & 1) == 1) {
                ans = (ans * x) % mod;
            }
            x = (x * x) % mod;
            n >>= 1;
        }
        return ans;
    }
}
