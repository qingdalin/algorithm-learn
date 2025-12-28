package algorithm.class169;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/5/25 15:38
 * // 带修改的区间第k小，java版
 * // 给定一个长度为n的数组arr，接下来是m条操作，每种操作是如下两种类型的一种
 * // 操作 C x y   : 把x位置的值修改成y
 * // 操作 Q x y v : 查询arr[x..y]范围上第v小的值
 * // 1 <= n、m <= 10^5
 * // 1 <= 数组中的值 <= 10^9
 * // 测试链接 : https://www.luogu.com.cn/problem/P2617
 * // 本题是讲解160，树套树模版题，现在作为带修改的整体二分模版题
 * // 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例
 */
public class Code02_DynamicRankings1 {
    public static int MAXN = 100001;
    public static int MAXE = MAXN << 2;
    public static int INF = 1000000001;
    public static int n, m;
    public static int[] arr = new int[MAXN];
    public static int[] eid = new int[MAXE];
    // op == 1，代表修改事件，x处，值y，效果v
    // op == 2，代表查询事件，[x..y]范围上查询第v小，q表示问题的编号
    public static int[] op = new int[MAXE];
    public static int[] x = new int[MAXE];
    public static int[] y = new int[MAXE];
    public static int[] v = new int[MAXE];
    public static int[] q = new int[MAXE];
    public static int[] tree = new int[MAXN];
    public static int cnte = 0;
    public static int cntq = 0;
    public static int[] lset = new int[MAXE];
    public static int[] rset = new int[MAXE];
    public static int[] ans = new int[MAXE];

    public static int lowbit(int i) {
        return i & -i;
    }

    public static void add(int i, int v) {
        while (i <= n) {
            tree[i] += v;
            i += lowbit(i);
        }
    }

    public static int sum(int i) {
        int ret = 0;
        while (i > 0) {
            ret += tree[i];
            i -= lowbit(i);
        }
        return ret;
    }

    public static int query(int l, int r) {
        return sum(r) - sum(l - 1);
    }

    public static void compute(int el, int er, int vl, int vr) {
        if (el > er) {
            return;
        }
        if (vl == vr) {
            for (int i = el, id; i <= er; i++) {
                id = eid[i];
                if (op[id] == 2) {
                    ans[q[id]] = vl;
                }
            }
        } else {
            int mid = (vl + vr) / 2;
            int lsiz = 0, rsiz = 0;
            for (int i = el, id; i <= er; i++) {
                id = eid[i];
                if (op[id] == 1) {
                    if (y[id] <= mid) {
                        add(x[id], v[id]);
                        lset[++lsiz] = id;
                    } else {
                        rset[++rsiz] = id;
                    }
                } else {
                    int satisfy = query(x[id], y[id]);
                    if (satisfy >= v[id]) {
                        lset[++lsiz] = id;
                    } else {
                        v[id] -= satisfy;
                        rset[++rsiz] = id;
                    }
                }
            }
            for (int i = 1; i <= lsiz; i++) {
                eid[el + i - 1] = lset[i];
            }
            for (int i = 1; i <= rsiz; i++) {
                eid[el + lsiz + i - 1] = rset[i];
            }
            for (int i = 1, id; i <= lsiz; i++) {
                id = lset[i];
                if (op[id] == 1 && y[id] <= mid) {
                    add(x[id], -v[id]);
                }
            }
            compute(el, el + lsiz - 1, vl, mid);
            compute(el + lsiz, er, mid + 1, vr);
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StreamTokenizer in = new StreamTokenizer(bf);
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        in.nextToken(); n = (int) in.nval;
        in.nextToken(); m = (int) in.nval;
        for (int i = 1; i <= n; i++) {
            in.nextToken(); arr[i] = (int) in.nval;
            op[++cnte] = 1;
            x[cnte] = i;
            y[cnte] = arr[i];
            v[cnte] = 1;
        }
        String type;
        for (int i = 1, a, b, c; i <= m; i++) {
            in.nextToken(); type = in.sval;
            if (type.equals("C")) {
                in.nextToken(); a = (int) in.nval;
                in.nextToken(); b = (int) in.nval;
                op[++cnte] = 1;
                x[cnte] = a;
                y[cnte] = arr[a];
                v[cnte] = -1;
                op[++cnte] = 1;
                x[cnte] = a;
                y[cnte] = b;
                v[cnte] = 1;
                arr[a] = b;
            } else {
                op[++cnte] = 2;
                in.nextToken(); x[cnte] = (int) in.nval;
                in.nextToken(); y[cnte] = (int) in.nval;
                in.nextToken(); v[cnte] = (int) in.nval;
                q[cnte] = ++cntq;
            }
        }
        for (int i = 1; i <= cnte; i++) {
            eid[i] = i;
        }
        compute(1, cnte, 0, INF);
        for (int i = 1; i <= cntq; i++) {
            out.println(ans[i]);
        }
        out.flush();
        out.close();
        bf.close();
    }
}
