package algorithm.class167;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;
import java.util.ArrayDeque;
import java.util.Deque;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/5/9 19:42
 * // 贪玩蓝月，java版
 * // 每件装备都有特征值w和战斗力v，放装备的背包是一个双端队列，只有背包中的装备是可选的
 * // 给定数值p，接下来有m条操作，每种操作是如下五种类型中的一种
 * // 操作 IF x y : 背包前端加入一件特征值x、战斗力y的装备
 * // 操作 IG x y : 背包后端加入一件特征值x、战斗力y的装备
 * // 操作 DF     : 删除背包前端的装备
 * // 操作 DG     : 删除背包后端的装备
 * // 操作 QU x y : 选择装备的特征值累加和 % p，必须在[x, y]范围，打印最大战斗力，无方案打印-1
 * // 1 <= m <= 5 * 10^4    1 <= p <= 500
 * // 0 <= 每件装备特征值、每件装备战斗力 <= 10^9
 * // 测试链接 : https://loj.ac/p/6515
 * // 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例
 */
public class Code02_BlueMoon1 {
    public static int MAXM = 50001;
    public static int DEEP = 20;
    public static int MAXT = 1000001;
    public static int m, p;
    public static int MAXP = 501;
    public static int[] op = new int[MAXM];
    public static int[] x = new int[MAXM];
    public static int[] y = new int[MAXM];
    // 背包<装备特征值%p、装备战斗力、装备出现时间点>
    public static Deque<int[]> knapsack = new ArrayDeque<>();

    public static int[] head = new int[MAXM << 2];
    public static int[] next = new int[MAXT];
    public static int[] tov = new int[MAXT];
    public static int[] tow = new int[MAXT];
    public static int cnt = 0;

    public static long[] pre = new long[MAXP];
    public static long[] dp = new long[MAXP];
    public static long[][] backup = new long[DEEP][MAXP];
    public static long[] ans = new long[MAXM];

    public static void clone(long[] a, long[] b) {
        for (int i = 0; i < p; i++) {
            a[i] = b[i];
        }
    }

    public static void addEdge(int i, int w, int v) {
        next[++cnt] = head[i];
        tov[cnt] = v;
        tow[cnt] = w;
        head[i] = cnt;
    }

    public static void add(int jobl, int jobr, int jobw, int jobv, int l, int r, int i) {
        if (jobl <= l && r <= jobr) {
            addEdge(i, jobw, jobv);
        } else {
            int mid = (l + r) / 2;
            if (jobl <= mid) {
                add(jobl, jobr, jobw, jobv, l, mid, i << 1);
            }
            if (jobr > mid) {
                add(jobl, jobr, jobw, jobv, mid + 1, r, i << 1 | 1);
            }
        }
    }

    public static void dfs(int l, int r, int i, int dep) {
        clone(backup[dep], dp);
        for(int e = head[i], v, w; e > 0; e = next[e]) {
            v = tov[e];
            w = tow[e];
            clone(pre, dp);
            for (int j = 0; j < p; j++) {
                if (pre[j] != -1) {
                    dp[(j + w) % p] = Math.max(dp[(j + w) % p], pre[j] + v);
                }
            }
        }
        if (l == r) {
            if (op[l] == 5) {
                long ret = -1;
                for (int j = x[l]; j <= y[l]; j++) {
                    ret = Math.max(ret, dp[j]);
                }
                ans[l] = ret;
            }
        } else {
            int mid = (l + r) / 2;
            dfs(l, mid, i << 1, dep + 1);
            dfs(mid + 1, r, i << 1 | 1, dep + 1);
        }
        clone(dp, backup[dep]);
    }

    public static void prepare() {
        int[] equip;
        for (int i = 1; i <= m; i++) {
            if (op[i] == 1) {
                knapsack.addFirst(new int[] {x[i] % p, y[i], i});
            } else if (op[i] == 2) {
                knapsack.addLast(new int[] {x[i] % p, y[i], i});
            } else if (op[i] == 3) {
                equip = knapsack.pollFirst();
                add(equip[2], i - 1, equip[0], equip[1], 1, m, 1);
            } else if (op[i] == 4) {
                equip = knapsack.pollLast();
                add(equip[2], i - 1, equip[0], equip[1], 1, m, 1);
            }
        }
        while (!knapsack.isEmpty()) {
            equip = knapsack.pollFirst();
            add(equip[2], m, equip[0], equip[1], 1, m, 1);
        }
        for (int i = 0; i < p; i++) {
            dp[i] = -1;
        }
        dp[0] = 0;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StreamTokenizer in = new StreamTokenizer(bf);
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        in.nextToken(); m = (int) in.nval;
        in.nextToken(); m = (int) in.nval;
        in.nextToken(); p = (int) in.nval;
        String t;
        for (int i = 1, a, b; i <= m; i++) {
            in.nextToken(); t = in.sval;
            if (t.equals("IF")) {
                op[i] = 1;
                in.nextToken(); x[i] = (int) in.nval;
                in.nextToken(); y[i] = (int) in.nval;
            } else if (t.equals("IG")) {
                op[i] = 2;
                in.nextToken(); x[i] = (int) in.nval;
                in.nextToken(); y[i] = (int) in.nval;
            } else if (t.equals("DF")) {
                op[i] = 3;
            } else if (t.equals("DG")) {
                op[i] = 4;
            } else {
                op[i] = 5;
                in.nextToken(); x[i] = (int) in.nval;
                in.nextToken(); y[i] = (int) in.nval;
            }
        }
        prepare();
        dfs(1, m, 1, 1);
        for (int i = 1; i <= m; i++) {
            if (op[i] == 5) {
                out.println(ans[i]);
            }
        }
        out.flush();
        out.close();
        bf.close();
    }
}
