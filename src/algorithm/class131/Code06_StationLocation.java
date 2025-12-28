package algorithm.class131;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2024/11/11 21:04
 * // 基站选址
 * // 一共有n个村庄排成一排，从左往右依次出现1号、2号、3号..n号村庄
 * // dist[i]表示i号村庄到1号村庄的距离，该数组一定有序且无重复值
 * // fix[i]表示i号村庄建立基站的安装费用
 * // range[i]表示i号村庄的接收范围，任何基站和i号村庄的距离不超过这个数字，i号村庄就能得到服务
 * // warranty[i]表示如果i号村庄最终没有得到任何基站的服务，需要给多少赔偿费用
 * // 最多可以选择k个村庄安装基站，返回总花费最少是多少，总花费包括安装费用和赔偿费用
 * // 1 <= n <= 20000
 * // 1 <= k <= 100
 * // k <= n
 * // 1 <= dist[i] <= 10^9
 * // 1 <= fix[i] <= 10^4
 * // 1 <= range[i] <= 10^9
 * // 1 <= warranty[i] <= 10^4
 * // 测试链接 : https://www.luogu.com.cn/problem/P2605
 * // 提交以下的code，提交时请把类名改成"Main"，可以通过所有用例
 */
public class Code06_StationLocation {
    // 需要补一个无穷远地方，n是20002
    public static int MAXN = 20002;
    public static int MAXK = 51;
    public static int n, k;
    // 距离第一个村庄距离
    public static int[] dist = new int[MAXN];
    // 修建基站费用
    public static int[] fix = new int[MAXN];
    // i号存在范围多远能接受到信号
    public static int[] range = new int[MAXN];
    // i号村庄接受不到信号需要补偿多少钱
    public static int[] warranty = new int[MAXN];
    // left[i]表示最左在第几号村庄建基站，i号村庄依然能获得服务
    public static int[] left = new int[MAXN];
    // right[i]表示最右在第几号村庄建基站，i号村庄依然能获得服务
    public static int[] right = new int[MAXN];
    // 链式前向星
    // 保存每个村庄的预警列表，i号村庄的预警列表是指
    // 如果只有一个基站建在i号村庄，现在这个基站要移动到i+1号村庄
    // 哪些村庄会从有服务变成无服务的状态
    public static int[] head = new int[MAXN];
    public static int[] next = new int[MAXN];
    public static int[] to = new int[MAXN];
    public static int cnt;
    // 动态规划表
    // dp[t][i]表示最多建t个基站，并且最右的基站一定要建在i号村庄，1..i号村庄的最少花费
    // 因为dp[t][i]，只依赖dp[t-1][..]，所以能空间压缩变成一维数组
    public static int[] dp = new int[MAXN];
    // 线段树维护最小值信息
    public static int[] min = new int[MAXN << 2];
    // 线段树懒更新
    public static int[] add = new int[MAXN << 2];
    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StreamTokenizer in = new StreamTokenizer(bf);
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        in.nextToken(); n = (int) in.nval;
        in.nextToken(); k = (int) in.nval;
        for (int i = 2; i <= n; i++) {
            in.nextToken(); dist[i] = (int) in.nval;
        }
        for (int i = 1; i <= n; i++) {
            in.nextToken(); fix[i] = (int) in.nval;
        }
        for (int i = 1; i <= n; i++) {
            in.nextToken(); range[i] = (int) in.nval;
        }
        for (int i = 1; i <= n; i++) {
            in.nextToken(); warranty[i] = (int) in.nval;
        }
        dist[++n] = Integer.MAX_VALUE;
        fix[n] = range[n] = warranty[n] = 0;
        prepare();
        System.out.println(compute());
        out.flush();
        out.close();
        bf.close();
    }

    private static int compute() {
        // 最多建t=1个基站的情况
        for (int i = 1, w = 0; i <= n; i++) {
            dp[i] = w + fix[i];
            for (int ei = head[i]; ei != 0; ei = next[ei]) {
                w += warranty[ei];
            }
        }
        for (int t = 2; t <= k + 1; t++) {
            build(1, n, 1);
            for (int i = 1; i <= n; i++) {
                if (i >= t) {
                    dp[i] = Math.min(dp[i], query(1, i - 1, 1, n, 1) + fix[i]);
                }
                for (int ei = head[i], uncover; ei != 0; ei = next[ei]) {
                    uncover = to[ei];
                    if (left[uncover] > 1) {
                        add(1, left[uncover] - 1, warranty[uncover], 1, n, 1);
                    }
                }
            }
        }
        return dp[n];
    }

    private static void add(int jobl, int jobr, int jobv, int l, int r, int i) {
        if (jobl <= l && r <= jobr) {
            lazy(i, jobv);
        } else {
            down(i);
            int m = (l + r) >> 1;
            if (jobl <= m) {
                add(jobl, jobr, jobv, l, m, i << 1);
            }
            if (jobr > m) {
                add(jobl, jobr, jobv, m + 1, r, i << 1 | 1);
            }
            up(i);
        }
    }

    private static int query(int jobl, int jobr, int l, int r, int i) {
        if (jobl <= l && r <= jobr) {
            return min[i];
        }
        down(i);
        int m = (l + r) >> 1;
        int ans = Integer.MAX_VALUE;
        if (jobl <= m) {
            ans = Math.min(ans, query(jobl, jobr, l, m, i << 1));
        }
        if (jobr > m) {
            ans = Math.min(ans, query(jobl, jobr, m + 1, r, i << 1 | 1));
        }
        return ans;
    }

    private static void down(int i) {
        if (add[i] != 0) {
            lazy(i << 1, add[i]);
            lazy(i << 1 | 1, add[i]);
            add[i] = 0;
        }
    }

    private static void lazy(int i, int v) {
        min[i] += v;
        add[i] += v;
    }

    private static void build(int l, int r, int i) {
        if (l == r) {
            min[i] = dp[l];
        } else {
            int m = (l + r) >> 1;
            build(l, m, i << 1);
            build(m + 1, r, i << 1 | 1);
            up(i);
        }
        add[i] = 0;
    }

    private static void up(int i) {
        min[i] = Math.min(min[i << 1], min[i << 1 | 1]);
    }

    private static void prepare() {
        cnt = 1;
        for (int i = 1; i <= n; i++) {
            left[i] = search(dist[i] - range[i]);
            right[i] = search(dist[i] + range[i]);
            if (dist[right[i]] > dist[i] + range[i]) {
                // 如果if命中，此时在right[i]建基站，
                // i是收不到信号，减一
                right[i]--;
            }
            // 生成预警列表
            // right[3] = 17
            // 17的列表里有3，从17 -> 3
            addEdge(right[i], i);
        }
    }

    private static void addEdge(int u, int v) {
        next[cnt] = head[u];
        to[cnt] = v;
        head[u] = cnt++;
    }

    // 二分查询大于等于d的最左位置
    private static int search(int d) {
        int l = 1, r = n, m = 0;
        int ans = 0;
        while (l <= r) {
            m = (l + r) / 2;
            if (dist[m] >= d) {
                ans = m;
                r = m - 1;
            } else {
                l = m + 1;
            }
        }
        return ans;
    }
}
