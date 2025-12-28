package algorithm.class75dp10;

import java.io.*;
import java.util.Arrays;

/**
 * @author 汪大鹏
 * @version 1.0
 * @date 2024-06-17 20:26
 * https://www.luogu.com.cn/problem/P1776
 * 多重背包
 * 每个物品有个数限制，二进制分组
 */
public class ManyBagByBinarySplit {
    public static int MAXN = 1001;
    public static int MAXW = 40001;
    public static int[] v = new int[MAXN];
    public static int[] w = new int[MAXN];
    public static int[] dp = new int[MAXW];
    public static int n, t, m;
    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StreamTokenizer st = new StreamTokenizer(bf);
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        while (st.nextToken() != StreamTokenizer.TT_EOF) {
            n = (int) st.nval;
            st.nextToken();
            t = (int) st.nval;
            m = 0;
            for (int i = 1, val, wight, cnt; i <= n; i++) {
                st.nextToken(); val = (int) st.nval;
                st.nextToken(); wight = (int) st.nval;
                st.nextToken(); cnt = (int) st.nval;
                for (int k = 1; k <= cnt; k <<= 1) {
                    v[++m] = val * k;
                    w[m] = wight * k;
                    cnt -= k;
                }
                if (cnt > 0) {
                    v[++m] = cnt * val;
                    w[m] = cnt * wight;
                }
            }
            System.out.println(f1());
        }
        out.flush();
        out.close();
        bf.close();
    }

    private static int f1() {
        Arrays.fill(dp, 0, t + 1, 0);
        for (int i = 1; i <= m; i++) {
            for (int j = t; j >= w[i]; j--) {
                dp[j] = Math.max(dp[j], dp[j - w[i]] + v[i]);
            }
        }
        return dp[t];
    }
}
