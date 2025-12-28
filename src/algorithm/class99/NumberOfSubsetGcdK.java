package algorithm.class99;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2024/8/4 16:01
 * https://www.luogu.com.cn/problem/CF803F
 */
public class NumberOfSubsetGcdK {
    public static int limit = 100001;
    public static int mod = 1000000007;
    public static int n;
    public static long[] cnts = new long[limit + 1];
    public static long[] dp = new long[limit + 1];
    public static long[] power2 = new long[limit + 1];


    public static void main(String[] args) throws IOException {
        build();
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StreamTokenizer st = new StreamTokenizer(bf);
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        while (st.nextToken() != StreamTokenizer.TT_EOF) {
            n = (int) st.nval;
            for (int i = 0; i < n; i++) {
                st.nextToken();
                cnts[(int) st.nval]++;
            }
            System.out.println(compute());
        }
        out.flush();
        out.close();
        bf.close();
    }
    public static void build() {
        power2[0] = 1;
        for (int i = 1; i <= limit; i++) {
            power2[i] = (power2[i - 1] * 2) % mod;
        }
    }

    private static int compute() {
        for (int i = limit; i >= 1; i--) {
            long counts = 0;
            for (int j = i; j <= limit; j+=i) {
                // 3 6 9 n倍的i的个数
                counts += cnts[j];
            }
            dp[i] = (power2[(int) counts] - 1 + mod) % mod;
            for (int j = 2 * i; j <= limit; j+=i) {
                dp[i] = (dp[i] - dp[j] + mod) % mod;
            }
        }
        return (int) dp[1];
    }
}
