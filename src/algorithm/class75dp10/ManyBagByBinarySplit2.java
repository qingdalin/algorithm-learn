package algorithm.class75dp10;

import java.io.*;
import java.util.Arrays;

/**
 * @author 汪大鹏
 * @version 1.0
 * @date 2024-06-17 20:26
 * https://www.luogu.com.cn/problem/P1833
 * 多重背包，赏樱花
 * 每个物品有个数限制，二进制分组，如果没限制默认为背包大小
 */
public class ManyBagByBinarySplit2 {
    public static int MAXN = 100001;
    public static int MAXW = 1001;
    public static int[] v = new int[MAXN];
    public static int[] w = new int[MAXN];
    public static int[] dp = new int[MAXW];
    public static int n, t, m;
    public static int h1, m1, h2, m2;
    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StreamTokenizer st = new StreamTokenizer(bf);
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        while (st.nextToken() != StreamTokenizer.TT_EOF) {
            h1 = (int) st.nval;
            st.nextToken();
            st.nextToken();
            m1 = (int) st.nval;
            st.nextToken();
            h2 = (int) st.nval;
            st.nextToken();
            st.nextToken();
            m2 = (int) st.nval;
            st.nextToken();
            n = (int) st.nval;
            if (m1 > m2) {
                h2--;
                m2 += 60;
            }
            // 背包大小
            t = (h2 - h1) * 60 + (m2 - m1);
            m = 0;
            for (int i = 1, val, wight, cnt; i <= n; i++) {
                st.nextToken(); wight = (int) st.nval;
                st.nextToken(); val = (int) st.nval;
                st.nextToken(); cnt = (int) st.nval;
                if (cnt == 0) {
                    cnt = MAXW;
                }
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
