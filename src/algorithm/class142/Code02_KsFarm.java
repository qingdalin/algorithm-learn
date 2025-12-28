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
 * @date: 2024/12/23 20:26
 * // 小k的农场
 * // 一共有n个农场，编号1~n，给定m条关系，每条关系是如下三种形式中的一种
 * // 关系1 a b c : 表示农场a比农场b至少多种植了c个作物
 * // 关系2 a b c : 表示农场a比农场b至多多种植了c个作物
 * // 关系3 a b   : 表示农场a和农场b种植了一样多的作物
 * // 如果关系之间能推出矛盾，打印"No"，不存在矛盾，打印"Yes"
 * // 1 <= n、m <= 5 * 10^3
 * // 1 <= c <= 5 * 10^3
 * // 测试链接 : https://www.luogu.com.cn/problem/P1993
 * // 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例
 */
public class Code02_KsFarm {
    public static int MAXN = 5001;
    public static int MAXM = 20001;
    public static int[] head = new int[MAXN];
    public static int[] next = new int[MAXM];
    public static int[] to = new int[MAXM];
    public static int[] weight = new int[MAXM];
    public static int cnt;
    public static int MAXQ = 20000001;
    public static int[] dist = new int[MAXN];
    public static int[] update = new int[MAXN];
    public static int[] queue = new int[MAXQ];
    public static boolean[] enter = new boolean[MAXN];
    public static int h, t;
    public static int n, m;
    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StreamTokenizer in = new StreamTokenizer(bf);
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        in.nextToken(); n = (int) in.nval;
        in.nextToken(); m = (int) in.nval;
        prepare();
        for (int i = 1; i <= n; i++) {
            addEdge(0, i, 0);
        }
        for (int i = 1, u, v, w, type; i <= m; i++) {
            // u - v <= w ==> u <= v + w
            in.nextToken(); type = (int) in.nval;
            in.nextToken(); u = (int) in.nval;
            in.nextToken(); v = (int) in.nval;
            if (type == 1) {
                // u - v >= w ==> v <= u - w
                in.nextToken(); w = (int) in.nval;
                addEdge(u, v, -w);
            } else if (type == 2) {
                // u - v <= w ==> u <= v + w
                in.nextToken(); w = (int) in.nval;
                addEdge(v, u, w);
            } else {
                addEdge(u, v, 0);
                addEdge(v, u, 0);
            }
        }
        if (spfa(0)) {
            out.println("No");
        } else {
            out.println("Yes");
        }
        out.flush();
        out.close();
        bf.close();
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
                int w = weight[e];
                if (dist[v] > dist[u] + w) {
                    dist[v] = dist[u] + w;
                    if (!enter[v]) {
                        // 注意判断逻辑和讲解065的代码不一样
                        // 因为节点0是额外增加的超级源点
                        // 所以节点数量增加了1个，所以这么判断
                        if (++update[v] > n) {
                            return true;
                        }
                        queue[t++] = v;
                        enter[v] = true;
                    }
                }
            }
        }
        return false;
    }

    private static void addEdge(int u, int v, int w) {
        next[cnt] = head[u];
        to[cnt] = v;
        weight[cnt] = w;
        head[u] = cnt++;
    }

    private static void prepare() {
        cnt = 1;
        h = t = 0;
        Arrays.fill(head, 0, n + 1, 0);
        Arrays.fill(dist, 0, n + 1, Integer.MAX_VALUE);
        Arrays.fill(update, 0, n + 1, 0);
        Arrays.fill(enter, 0, n + 1, false);
    }
}
