package algorithm.class163;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/3/28 19:52
 * // 不同名字数量，java版
 * // 一共有n个节点，编号1~n，给定每个节点的名字和父亲节点编号
 * // 名字是string类型，如果父亲节点编号为0，说明当前节点是某棵树的头节点
 * // 注意，n个节点组成的是森林结构，可能有若干棵树
 * // 一共有m条查询，每条查询 x k，含义如下
 * // 以x为头的子树上，到x距离为k的所有节点中，打印不同名字的数量
 * // 1 <= n、m <= 10^5
 * // 测试链接 : https://www.luogu.com.cn/problem/CF246E
 * // 测试链接 : https://codeforces.com/problemset/problem/246/E
 * // 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例
 */
public class Code04_DifferntName1 {
    public static int MAXN = 100001;
    public static int n, m;
    public static Map<String, Integer> nameId = new HashMap<>();
    public static int[] id = new int[MAXN];
    public static boolean[] root = new boolean[MAXN];

    public static int[] headg = new int[MAXN];
    public static int[] nextg = new int[MAXN];
    public static int[] tog = new int[MAXN];
    public static int cntg = 0;

    public static int[] headq = new int[MAXN];
    public static int[] nextq = new int[MAXN];
    public static int[] ansiq = new int[MAXN];
    public static int[] kq = new int[MAXN];
    public static int cntq = 0;

    public static int[] fa = new int[MAXN];
    public static int[] son = new int[MAXN];
    public static int[] dep = new int[MAXN];
    public static int[] siz = new int[MAXN];


    public static int[] ans = new int[MAXN];
    public static List<Set<Integer>> depSet = new ArrayList<>();

    public static int getNameId(String name) {
        if (nameId.containsKey(name)) {
            return nameId.get(name);
        }
        nameId.put(name, nameId.size() + 1);
        return nameId.size();
    }

    public static void addId(int deep, int id) {
        depSet.get(deep).add(id);
    }

    public static void removeId(int deep, int id) {
        depSet.get(deep).remove(id);
    }

    public static int sizeOfDeep(int deep) {
        if (deep > n) {
            return 0;
        }
        return depSet.get(deep).size();
    }

    public static void addEdge(int u, int v) {
        nextg[++cntg] = headg[u];
        tog[cntg] = v;
        headg[u] = cntg;
    }

    public static void addQuestion(int u, int ansi, int k) {
        nextq[++cntq] = headq[u];
        ansiq[cntq] = ansi;
        kq[cntq] = k;
        headq[u] = cntq;
    }

    public static void dfs1(int u, int f) {
        fa[u] = f;
        dep[u] = dep[f] + 1;
        siz[u] = 1;
        for(int e = headg[u], v; e > 0; e = nextg[e]) {
            dfs1(tog[e], u);
        }
        for(int e = headg[u], v; e > 0; e = nextg[e]) {
            v = tog[e];
            siz[u] += siz[v];
            if (son[u] == 0 || siz[son[u]] < siz[v]) {
                son[u] = v;
            }
        }
    }

    public static void effect(int u) {
        addId(dep[u], id[u]);
        for(int e = headg[u]; e > 0; e = nextg[e]) {
            effect(tog[e]);
        }
    }

    public static void cancel(int u) {
        removeId(dep[u], id[u]);
        for(int e = headg[u]; e > 0; e = nextg[e]) {
            cancel(tog[e]);
        }
    }

    public static void dfs2(int u, int keep) {
        for(int e = headg[u], v; e > 0; e = nextg[e]) {
            v = tog[e];
            if (son[u] != v) {
                dfs2(v, 0);
            }
        }
        if (son[u] != 0) {
            dfs2(son[u], 1);
        }
        addId(dep[u], id[u]);
        for(int e = headg[u], v; e > 0; e = nextg[e]) {
            v = tog[e];
            if (son[u] != v) {
                effect(v);
            }
        }
        for (int i = headq[u]; i > 0; i = nextq[i]) {
            ans[ansiq[i]] = sizeOfDeep(dep[u] + kq[i]);
        }
        if (keep == 0) {
            cancel(u);
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StreamTokenizer in = new StreamTokenizer(br);
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        n = Integer.parseInt(br.readLine());
        String name;
        int father;
        for (int i = 1; i <= n; i++) {
            String[] s = br.readLine().split(" ");
            name = s[0];
            father = Integer.parseInt(s[1]);
            id[i] = getNameId(name);
            if (father == 0) {
                root[i] = true;
            } else {
                addEdge(father, i);
            }
        }
        for (int i = 1; i <= n; i++) {
            if (root[i]) {
                dfs1(i, 0);
            }
        }
        for (int i = 0; i <= n; i++) {
            depSet.add(new HashSet<>());
        }
        m = Integer.parseInt(br.readLine());
        for (int i = 1, node, k; i <= m; i++) {
            String[] s = br.readLine().split(" ");
            node = Integer.parseInt(s[0]);
            k = Integer.parseInt(s[1]);
            addQuestion(node, i, k);
        }
        for (int i = 1; i <= n; i++) {
            if (root[i]) {
                dfs2(i, 0);
            }
        }
        for (int i = 1; i <= m; i++) {
            out.println(ans[i]);
        }
        out.flush();
        out.close();
        br.close();
    }
}
