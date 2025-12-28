package algorithm.class156;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/2/23 16:46
 * // 推导部分和，带权并查集模版题1
 * // 有n个数字，下标1 ~ n，但是并不知道每个数字是多少
 * // 先给出m个数字段的累加和，再查询q个数字段的累加和
 * // 给出数字段累加和的操作 l r v，代表l~r范围上的数字，累加和为v
 * // 查询数字段累加和的操作 l r，代表查询l~r范围上的数字累加和
 * // 请根据m个给定，完成q个查询，如果某个查询无法给出答案，打印"UNKNOWN"
 * // 1 <= n, m, q <= 10^5
 * // 累加和不会超过long类型范围
 * // 测试链接 : https://www.luogu.com.cn/problem/P8779
 * // 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例
 */
public class Code01_DerivePartialSums {
    public static int MAXN = 100002;
    public static int n, m, q;
    public static int[] father = new int[MAXN];
    public static long[] dist = new long[MAXN];
    public static long INF = Long.MAX_VALUE;

    public static void prepare() {
        for (int i = 0; i <= n; i++) {
            father[i] = i;
            dist[i] = 0;
        }
    }

    public static int find(int i) {
        if (father[i] != i) {
            int tmp = father[i];
            father[i] = find(tmp);
            // 错误写法 dist[i] += tmp;
            dist[i] += dist[tmp];
        }
        return father[i];
    }

    public static void union(int l, int r, long v) {
        int lf = find(l), rf = find(r);
        if (lf != rf) {
            father[lf] = rf;
            // dist[后]-dist[前]+v
            // 错误写法 dist[lf] = v + dist[rf] - dist[lf];
            dist[lf] = v + dist[r] - dist[l];
        }
    }

    public static long query(int l, int r) {
        if (find(l) != find(r)) {
            return INF;
        }
        return dist[l] - dist[r];
    }

    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StreamTokenizer in = new StreamTokenizer(bf);
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        in.nextToken(); n = (int) in.nval + 1;
        in.nextToken(); m = (int) in.nval;
        in.nextToken(); q = (int) in.nval;
        prepare();
        int l, r;
        long v;
        for (int i = 1; i <= m; i++) {
            in.nextToken(); l = (int) in.nval;
            in.nextToken(); r = (int) in.nval + 1;
            in.nextToken(); v = (long) in.nval;
            union(l, r, v);
        }
        for (int i = 1; i <= q; i++) {
            in.nextToken(); l = (int) in.nval;
            in.nextToken(); r = (int) in.nval + 1;
            long ans = query(l, r);
            if (ans == INF) {
                out.println("UNKNOWN");
            } else {
                out.println(ans);
            }
        }
        out.flush();
        out.close();
        bf.close();
    }
}
