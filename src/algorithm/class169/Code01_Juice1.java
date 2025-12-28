package algorithm.class169;

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
 * @date: 2025/5/25 9:04
 * // 混合果汁，java版
 * // 一共有n种果汁，每种果汁给定，美味度d、每升价格p、添加上限l
 * // 制作混合果汁时每种果汁不能超过添加上限，其中美味度最低的果汁，决定混合果汁的美味度
 * // 一共有m个小朋友，给每位制作混合果汁时，钱数不超过money[i]，体积不少于least[i]
 * // 打印每个小朋友能得到的混合果汁最大美味度，如果无法满足，打印-1
 * // 1 <= n、m、d、p、l <= 10^5
 * // 1 <= money[i]、least[i] <= 10^18
 * // 测试链接 : https://www.luogu.com.cn/problem/P4602
 * // 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例
 */
public class Code01_Juice1 {
    public static int MAXN = 100001;
    public static int n, m;
    public static int[][] juice = new int[MAXN][3];
    public static int[] qid = new int[MAXN];
    public static long[] money = new long[MAXN];
    public static long[] least = new long[MAXN];
    public static int maxp = 0;
    public static long[] suml = new long[MAXN << 2];
    public static long[] cost = new long[MAXN << 2];
    public static int used = 0;
    public static int[] lset = new int[MAXN];
    public static int[] rset = new int[MAXN];
    public static int[] ans = new int[MAXN];

    public static void up(int i) {
        suml[i] = suml[i << 1] + suml[i << 1 | 1];
        cost[i] = cost[i << 1] + cost[i << 1 | 1];
    }

    public static void add(int jobi, int jobv, int l, int r, int i) {
        if (l == r) {
            suml[i] += jobv;
            cost[i] = l * suml[i];
        } else {
            int mid = (l + r) / 2;
            if (jobi <= mid) {
                add(jobi, jobv, l, mid, i << 1);
            } else {
                add(jobi, jobv, mid + 1, r, i << 1 | 1);
            }
            up(i);
        }
    }

    // 总体积一共volume，已知总体积一定能耗尽
    // 返回总体积耗尽的情况下，能花的最少钱数
    public static long query(long volume, int l, int r, int i) {
        if (l == r) {
            return volume * l;
        }
        int mid = (l + r) / 2;
        if (suml[i << 1] >= volume) {
            return query(volume, l, mid, i << 1);
        } else {
            return cost[i << 1] + query(volume - suml[i << 1], mid + 1, r, i << 1 | 1);
        }
    }

    public static void compute(int ql, int qr, int vl, int vr) {
        if (ql > qr) {
            return;
        }
        if (vl == vr) {
            for (int i = ql; i <= qr; i++) {
                ans[qid[i]] = vl;
            }
        } else {
            int mid = (vl + vr) / 2;
            while (used < mid) {
                used++;
                add(juice[used][1], juice[used][2], 1, maxp, 1);
            }
            while (used > mid) {
                add(juice[used][1], -juice[used][2], 1, maxp, 1);
                used--;
            }
            int lsiz = 0, rsiz = 0;
            for (int i = ql, id; i <= qr; i++) {
                id = qid[i];
                if (suml[1] >= least[id] && query(least[id], 1, maxp, 1) <= money[id]) {
                    lset[++lsiz] = id;
                } else {
                    rset[++rsiz] = id;
                }
            }
            for (int i = 1; i <= lsiz; i++) {
                qid[ql + i - 1] = lset[i];
            }
            for (int i = 1; i <= rsiz; i++) {
                qid[ql + lsiz + i - 1] = rset[i];
            }
            compute(ql, ql + lsiz - 1, vl, mid);
            compute(ql + lsiz, qr, mid + 1, vr);
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StreamTokenizer in = new StreamTokenizer(bf);
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        in.nextToken(); n = (int) in.nval;
        in.nextToken(); m = (int) in.nval;
        for (int i = 1; i <= n; i++) {
            in.nextToken(); juice[i][0] = (int) in.nval;
            in.nextToken(); juice[i][1] = (int) in.nval;
            in.nextToken(); juice[i][2] = (int) in.nval;
            maxp = Math.max(maxp, juice[i][1]);
        }
        for (int i = 1; i <= m; i++) {
            qid[i] = i;
            String[] s = bf.readLine().split(" ");
            money[i] = Long.parseLong(s[0]);
            least[i] = Long.parseLong(s[1]);
        }
        Arrays.sort(juice, 1, n + 1, (a, b) -> b[0] - a[0]);
        compute(1, m, 1, n + 1);
        for (int i = 1; i <= m; i++) {
            if (ans[i] == n + 1) {
                out.println(-1);
            } else {
                out.println(juice[ans[i]][0]);
            }
        }
        out.flush();
        out.close();
        bf.close();
    }
}
