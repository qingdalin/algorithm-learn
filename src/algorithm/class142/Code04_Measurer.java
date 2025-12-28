package algorithm.class142;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;
import java.util.Arrays;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2024/12/25 20:30
 * // 倍杀测量者
 * // 如果 A的分数 >= B的分数 * k，k是正实数，就称 A k倍杀 B，或称 B被A k倍杀了
 * // 一场比赛中，一共有n个选手，有m1条誓言记录，有m2条选手得分记录，得分只可能是正实数
 * // 类型1的誓言 u v k : 选手u 没有k倍杀 选手v，那么选手u就穿女装
 * // 类型2的誓言 u v k : 选手u 被选手v k倍杀了，那么选手u就穿女装
 * // 选手的得分    u w : 选手u得了w分，如果某选手没有得分记录，按照尽量不穿女装的情况推测
 * // 你希望看到比赛后有人穿女装，但不想看到很多人穿女装，于是想制定正实数ans，效果如下
 * // 类型1的誓言，比例调整成(k-ans)，类型2的誓言，比例调整成(k+ans)，即提高了穿女装的条件
 * // 计算ans最大多少，依然有人穿女装，保留小数点后4位，如果不干预也没人穿女装，返回-1
 * // 1 <= n, m1, m2 <= 1000
 * // 1 <= k <= 10
 * // 1 <= w <= 10^9
 * // 测试链接 : https://www.luogu.com.cn/problem/P4926
 * // 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例
 */
public class Code04_Measurer {
    public static int MAXN = 1002;
    public static int MAXM = 3002;

    public static double INF = 1e10;
    public static double sml = 1e-6;

    public static int[][] vow = new int[MAXN][4];
    public static int[][] score = new int[MAXN][2];

    public static int[] head = new int[MAXN];
    public static int[] next = new int[MAXM];
    public static int[] to = new int[MAXM];
    public static double[] weight = new double[MAXM];
    public static int cnt;
    public static int MAXQ = 3000001;
    public static double[] dist = new double[MAXN];
    public static int[] update = new int[MAXN];
    public static int[] queue = new int[MAXQ];
    public static boolean[] enter = new boolean[MAXN];
    public static int h, t;
    public static int n, m1, m2;
    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StreamTokenizer in = new StreamTokenizer(bf);
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        in.nextToken(); n = (int) in.nval;
        in.nextToken(); m1 = (int) in.nval;
        in.nextToken(); m2 = (int) in.nval;
        for (int i = 1; i <= m1; i++) {
            in.nextToken(); vow[i][0] = (int) in.nval;
            in.nextToken(); vow[i][1] = (int) in.nval;
            in.nextToken(); vow[i][2] = (int) in.nval;
            in.nextToken(); vow[i][3] = (int) in.nval;
        }
        for (int i = 1; i <= m2; i++) {
            in.nextToken(); score[i][0] = (int) in.nval;
            in.nextToken(); score[i][1] = (int) in.nval;
        }
        double ans = compute();
        if (ans == 0) {
            out.println(-1);
        } else {
            out.println(ans);
        }
        out.flush();
        out.close();
        bf.close();
    }

    private static double compute() {
        double l = 0, r = INF, m, ans = 0;
        while (r - l >= sml) {
            m = (l + r) / 2;
            if (check(m)) {
                ans = m;
                l = m + sml;
            } else {
                r = m - sml;
            }
        }
        return ans;
    }

    private static boolean check(double limit) {
        prepare();
        for (int i = 1; i <= n; i++) {
            addEdge(0, i, 0);
        }
        // 根据誓言建立边
        for (int i = 1; i <= m1; i++) {
            if (vow[i][0] == 1) {
                // u >= v * k 不女装
                // u/v >= k
                // log(u) - log(v) >= log(k-limit)
                // log(v) <= log(u)-log(k-limit)
                addEdge(vow[i][1], vow[i][2], -Math.log(vow[i][3] - limit));
            } else {
                // v >= u * k 女装
                // v < u * k 不女装
                // v/u < k
                // log(v) - log(u) < log(limit+k)
                // log(v) < log(u) + log(limit+k)
                // log(v) < log(u) + log(limit+k) - sml
                addEdge(vow[i][1], vow[i][2], Math.log(vow[i][3] + limit - sml));
            }
        }
        for (int i = 1; i <= m2; i++) {
            // a = 4, y = 0
            // a <= 4 + y
            // a >= 4 + y ==> y <= a - 4
            addEdge(score[i][0], n + 1, -Math.log(score[i][1]));
            addEdge(n + 1, score[i][0], Math.log(score[i][1]));
        }
        return spfa(0);
    }

    private static boolean spfa(int s) {
        dist[s] = 0;
        update[s] = 1;
        enter[s] = true;
        queue[t++] = s;
        while (h < t) {
            int u = queue[h++];
            enter[u] = false;
            for (int e = head[u]; e != 0; e = next[e]) {
                int v = to[e];
                double w = weight[e];
                if (dist[v] > dist[u] + w) {
                    dist[v] = dist[u] + w;
                    if (!enter[v]) {
                        if (++update[v] > n + 1) {
                            // 建立的是不女装的式子，如果形成负环，说明有女装，记录答案
                            return true;
                        }
                        enter[v] = true;
                        queue[t++] = v;
                    }
                }
            }
        }
        return false;
    }

    private static void prepare() {
        cnt = 1;
        h = t = 0;
        Arrays.fill(head, 0, n + 2, 0);
        Arrays.fill(update, 0, n + 2, 0);
        Arrays.fill(dist, 0, n + 2, INF);
        Arrays.fill(enter, 0, n + 2, false);
    }

    private static void addEdge(int u, int v, double w) {
        next[cnt] = head[u];
        to[cnt] = v;
        weight[cnt] = w;
        head[u] = cnt++;
    }
}
