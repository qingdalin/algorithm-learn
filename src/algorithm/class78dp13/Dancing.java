package algorithm.class78dp13;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;
import java.util.Arrays;

/**
 * @author 汪大鹏
 * @version 1.0
 * @date 2024-06-28 20:01
 * 某大学有n 个职员，编号为1…n。
 * 他们之间有从属关系，也就是说他们的关系就像一棵以校长为根的树，父结点就是子结点的直接上司。
 * 现在有个周年庆宴会，宴会每邀请来一个职员都会增加一定的快乐指数但是呢，
 * 如果某个职员的直接上司来参加舞会了，那么这个职员就无论如何也不肯来参加舞会了。
 * 所以，请你编程计算，邀请哪些职员可以使快乐指数最大，求最大的快乐指数。
 *
 * 输入格式
 * 输入的第一行是一个整数n。
 * 第 2 到第(n+1) 行，每行一个整数，第 (i+1) 行的整数表示i号职员的快乐指数
 * 第 (n+2) 到第 2n 行，每行输入一对整数l,k，代表 k 是 l 的直接上司。
 *
 * 输出格式
 * 输出一行一个整数代表最大的快乐指数。
 * https://www.luogu.com.cn/problem/P1352
 * https://leetcode.cn/problems/house-robber-iii/
 */
public class Dancing {
    public static int n, cnt, h;
    public static int MAXN = 6001;
    public static int[] nums = new int[MAXN];
    public static int[] head = new int[MAXN];
    public static int[] next = new int[MAXN];
    public static int[] to = new int[MAXN];
    public static int[] yes = new int[MAXN];
    public static int[] no = new int[MAXN];
    public static boolean[] boss = new boolean[MAXN];
    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StreamTokenizer st = new StreamTokenizer(bf);
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        while (st.nextToken() != StreamTokenizer.TT_EOF) {
            n = (int) st.nval;
            build();
            for (int i = 1; i <= n; i++) {
                st.nextToken();
                nums[i] = (int) st.nval;
            }
            for (int i = 1; i < n; i++) {
                st.nextToken();
                int low = (int) st.nval;
                st.nextToken();
                int height = (int) st.nval;
                addEdge(height, low);
                boss[low] = false;
            }
            for (int i = 1; i <= n; i++) {
                if (boss[i]) {
                    h = i;
                    break;
                }
            }
            f(h);
            System.out.println(Math.max(yes[h], no[h]));
        }
        out.flush();
        out.close();
        bf.close();
    }

    private static void f(int u) {
        no[u] = 0;
        yes[u] = nums[u];
        for (int ei = head[u], v; ei > 0; ei = next[ei]) {
            v = to[ei];
            f(v);
            yes[u] += no[v];
            no[u] += Math.max(yes[v], no[v]);
        }
    }

    private static void build() {
        Arrays.fill(head, 0, n + 1, 0);
        Arrays.fill(boss, 0, n + 1, true);
        cnt = 1;
    }

    public static void addEdge(int u, int v) {
        next[cnt] = head[u];
        to[cnt] = v;
        head[u] = cnt++;
    }

}
