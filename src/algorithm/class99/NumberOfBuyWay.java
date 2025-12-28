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
 * @date: 2024/8/4 16:49
 * https://www.luogu.com.cn/problem/P1450
 */
public class NumberOfBuyWay {
    public static int limit = 100001;
    public static int n, s;
    public static int[] cnts = new int[4];
    public static int[] vals = new int[4];
    public static long[] dp = new long[limit + 1];


    public static void main(String[] args) throws IOException {

        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StreamTokenizer st = new StreamTokenizer(bf);
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        while (st.nextToken() != StreamTokenizer.TT_EOF) {
            vals[0] = (int) st.nval;
            st.nextToken(); vals[1] = (int) st.nval;
            st.nextToken(); vals[2] = (int) st.nval;
            st.nextToken(); vals[3] = (int) st.nval;
            st.nextToken();
            n = (int) st.nval;
            build();
            for (int i = 0; i < n; i++) {
                st.nextToken(); cnts[0] = (int) st.nval;
                st.nextToken(); cnts[1] = (int) st.nval;
                st.nextToken(); cnts[2] = (int) st.nval;
                st.nextToken(); cnts[3] = (int) st.nval;
                st.nextToken(); s = (int) st.nval;
                System.out.println(query());
            }
        }
        out.flush();
        out.close();
        bf.close();
    }

    private static long query() {
        long illegal = 0;
        // 状态中为1的个数是偶数就是-1减去
        // 状态中为1的个数是奇数就是+1加上
        for (int status = 1; status <= 15; status++) {
            long t = s;
            int sign = -1;
            for (int i = 0; i <= 3; i++) {
                if ((status >> i & 1) == 1) {
                    t -= ((long) vals[i] * (cnts[i] + 1));
                    sign = -sign;
                }
            }
            if (t >= 0) {
                illegal += dp[(int)t] * sign;
            }
        }
        return dp[s] - illegal;
    }

    private static void build() {
        // 完全背包
        // dp[i][j]: 0-i硬币随便选必须是j元
        // dp[i][j]=dp[i-1][j],i-1个硬币随便选j元
        // dp[i][j]=dp[i][j-val[i]],i个硬币随便选j-val[i]元
        dp[0] = 1;
        for (int i = 0; i <= 3; i++) {
            for (int j = vals[i]; j <= limit; j++) {
                dp[j] += dp[j - vals[i]];
            }
        }
    }
}
