package algorithm.class73dp8;

import java.io.*;

/**
 * @author 汪大鹏
 * @version 1.0
 * @date 2024-06-14 20:33
 * 有依赖的背包
 * https://www.nowcoder.com/practice/f9c6f980eeec43ef85be20755ddbeaf4
 * 输入的第 1 行，为两个正整数N，m，用一个空格隔开：
 * （其中 N （ N<32000 ）表示总钱数， m （m <60 ）为可购买的物品的个数。）
 * 从第 2 行到第 m+1 行，第 j 行给出了编号为 j-1 的物品的基本数据，每行有 3 个非负整数 v p q
 * （其中 v 表示该物品的价格（ v<10000 ）， p 表示该物品的重要度（ 1 ~ 5 ）， q 表示该物品是主件还是附件。
 * 如果 q=0 ，表示该物品为主件，如果 q>0 ，表示该物品为附件， q 是所属主件的编号）
 */
public class DependentBags {
    public static int MAXN = 32001;
    public static int MAXM = 61;
    public static int n, m, v, p, q;
    public static int[] costs = new int[MAXM];
    public static int[] vals = new int[MAXM];
    public static boolean[] kings = new boolean[MAXM];
    public static int[] fans = new int[MAXM];
    public static int[][] follows = new int[MAXM][2];
    public static void clean() {
        for (int i = 0; i <= m; i++) {
            fans[i] = 0;
        }
    }
    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StreamTokenizer st = new StreamTokenizer(bf);
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        while (st.nextToken() != StreamTokenizer.TT_EOF) {
            n = (int) st.nval;
            st.nextToken();
            m = (int) st.nval;
            clean();
            for (int i = 1; i <= m; i++) {
                st.nextToken(); v = (int) st.nval;
                st.nextToken(); p = (int) st.nval;
                st.nextToken(); q = (int) st.nval;
                costs[i] = v;
                vals[i] = v * p;
                kings[i] = q == 0;
                if (q != 0) {
                    follows[q][fans[q]++] = i;
                }
            }
            out.println(f1());
        }
        out.flush();
        out.close();
        bf.close();
    }
    private static int f2() {
        int[] dp = new int[n + 1];
        // dp[0][..] 没有一个物品价值都是0
        // dp[i][j] 表示第i个物品的最大收益
        int p = 0; // 表示上一个主件的编号
        for (int i = 1, fan1, fan2; i <= m; i++) {
            if (kings[i]) {
                for (int j = n; j >= costs[i]; j--) {
                    // i号物品是主物品才进行计算
                    // 1.不要当前主物品
                    // 2.只要当前主物品
                    dp[j] = Math.max(dp[j], dp[j - costs[i]] + vals[i]);
                    // fan1 如果有一个附件，赋值为附件1编号，否则-1
                    // fan2 如果有二个附件，赋值为附件2编号，否则-1
                    fan1 = fans[i] >= 1 ? follows[i][0] : -1;
                    fan2 = fans[i] >= 2 ? follows[i][1] : -1;
                    // 3.要主件和附件1
                    if (fan1 != -1 && j - costs[i] - costs[fan1] >= 0) {
                        dp[j] = Math.max(dp[j], dp[j - costs[i] - costs[fan1]] + vals[i] + vals[fan1]);
                    }
                    // 4.要主件和附件2
                    if (fan2 != -1 && j - costs[i] - costs[fan2] >= 0) {
                        dp[j] = Math.max(dp[j], dp[j - costs[i] - costs[fan2]] + vals[i] + vals[fan2]);
                    }
                    // 5.要主件和附件1和附件2
                    if (fan1 != -1 && fan2 != -1 && j - costs[i] - costs[fan1] - costs[fan2] >= 0) {
                        dp[j] = Math.max(dp[j],
                            dp[j - costs[i] - costs[fan1] - costs[fan2]] + vals[i] + vals[fan1] + vals[fan2]);
                    }
                }
                p = i;
            }
        }
        return dp[n];
    }

    private static int f1() {
        int[][] dp = new int[m + 1][n + 1];
        // dp[0][..] 没有一个物品价值都是0
        // dp[i][j] 表示第i个物品的最大收益
        int p = 0; // 表示上一个主件的编号
        for (int i = 1, fan1, fan2; i <= m; i++) {
            if (kings[i]) {
                for (int j = 0; j <= n; j++) {
                    // i号物品是主物品才进行计算
                    // 1.不要当前主物品
                    dp[i][j] = dp[p][j];
                    // 2.只要当前主物品
                    if (j - costs[i] >= 0) {
                        dp[i][j] = Math.max(dp[i][j], dp[p][j - costs[i]] + vals[i]);
                    }
                    // fan1 如果有一个附件，赋值为附件1编号，否则-1
                    // fan2 如果有二个附件，赋值为附件2编号，否则-1
                    fan1 = fans[i] >= 1 ? follows[i][0] : -1;
                    fan2 = fans[i] >= 2 ? follows[i][1] : -1;
                    // 3.要主件和附件1
                    if (fan1 != -1 && j - costs[i] - costs[fan1] >= 0) {
                        dp[i][j] = Math.max(dp[i][j], dp[p][j - costs[i] - costs[fan1]] + vals[i] + vals[fan1]);
                    }
                    // 4.要主件和附件2
                    if (fan2 != -1 && j - costs[i] - costs[fan2] >= 0) {
                        dp[i][j] = Math.max(dp[i][j], dp[p][j - costs[i] - costs[fan2]] + vals[i] + vals[fan2]);
                    }
                    // 5.要主件和附件1和附件2
                    if (fan1 != -1 && fan2 != -1 && j - costs[i] - costs[fan1] - costs[fan2] >= 0) {
                        dp[i][j] = Math.max(dp[i][j],
                            dp[p][j - costs[i] - costs[fan1] - costs[fan2]] + vals[i] + vals[fan1] + vals[fan2]);
                    }
                }
                p = i;
            }
        }
        return dp[p][n];
    }
}
