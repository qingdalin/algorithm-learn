package algorithm.class73dp8;

import java.io.*;
import java.util.Arrays;

/**
 * @author 汪大鹏
 * @version 1.0
 * @date 2024-06-03 20:16
 * 某公司游戏平台的夏季特惠开始了，你决定入手一些游戏。现在你一共有X元的预算，该平台上所有的 n 个游戏均有折扣，标号为 i 的游戏的原价ai元，现价只要
 * bi元（也就是说该游戏可以优惠ai−bi元）并且你购买该游戏能获得快乐值为
 * wi。由于优惠的存在，你可能做出一些冲动消费导致最终买游戏的总费用超过预算，
 * 但只要满足获得的总优惠金额不低于超过预算的总金额，那在心理上就不会觉得吃亏。
 * 现在你希望在心理上不觉得吃亏的前提下，获得尽可能多的快乐值。
 * https://leetcode.cn/problems/tjau2o/description/
 * 输入：
 * - 第一行包含两个数 n 和 X 。
 * - 接下来 n 行包含每个游戏的信息，原价 ai,现价 bi，能获得的快乐值为 wi 。
 * 输出：
 * - 输出一个数字，表示你能获得的最大快乐值
 */
public class BuyGoods {
    public static int MAXN = 501;
    public static int MAXX = 10001;
    public static int n, x, m;
    // 花费数组
    public static int[] cost = new int[MAXN];
    // 每个物品价值数组
    public static long[] val = new long[MAXN];
    public static long[] dp = new long[MAXX];
    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StreamTokenizer st = new StreamTokenizer(bf);
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        while (st.nextToken() != StreamTokenizer.TT_EOF) {
            n = (int) st.nval;
            st.nextToken(); x = (int) st.nval;
            m = 1;
            long ans = 0;
            long happy = 0;
            for (int i = 0, pre, cur, well; i < n; i++) {
                st.nextToken(); pre = (int) st.nval;
                st.nextToken(); cur = (int) st.nval;
                st.nextToken(); happy = (long) st.nval;
                // 原件 - 现价 - 现价
                // 如果大于等于0，直接加
                // 如果小于0需要进行背包考虑
                well = pre - cur - cur;
                if (well >= 0) {
                    // 将获取到的纯收入，增加在x基础上
                    x += well;
                    ans += happy;
                } else {
                    cost[m] = -well;
                    val[m++] = happy;
                }
            }
            ans += f();
            out.println(ans);
        }
        out.flush();
        out.close();
        bf.close();
    }

    private static long f() {
        Arrays.fill(dp, 1, x + 1, 0);
        for (int i = 1; i <= m; i++) {
            for (int j = x; j >= cost[i]; j--) {
                dp[j] = Math.max(dp[j], dp[j - cost[i]] + val[i]);
            }
        }
        return  dp[x];
    }
}
