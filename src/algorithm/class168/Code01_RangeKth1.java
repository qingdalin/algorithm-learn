package algorithm.class168;

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
 * @date: 2025/5/17 9:48
 * // 区间内第k小，第一种写法，java版
 * // 给定一个长度为n的数组，接下来有m条查询，格式如下
 * // 查询 l r k : 打印[l..r]范围内第k小的值
 * // 1 <= n、m <= 2 * 10^5
 * // 1 <= 数组中的数字 <= 10^9
 * // 测试链接 : https://www.luogu.com.cn/problem/P3834
 * // 本题是讲解157，可持久化线段树模版题，现在作为整体二分的模版题
 * // 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例
 */
public class Code01_RangeKth1 {
    public static int MAXN = 200001;
    public static int n, m;
    public static int[][] arr = new int[MAXN][2];
    public static int[] qid = new int[MAXN];
    public static int[] l = new int[MAXN];
    public static int[] r = new int[MAXN];
    public static int[] k = new int[MAXN];
    public static int[] tree = new int[MAXN];
    public static int[] lset = new int[MAXN];
    public static int[] rset = new int[MAXN];
    public static int[] ans = new int[MAXN];

    public static int lowbit(int i) {
        return i & -i;
    }

    public static void add(int i, int v) {
        while (i <= n) {
            tree[i] += v;
            i += lowbit(i);
        }
    }

    public static int sum (int i) {
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

    public static void compute(int ql, int qr, int vl, int vr) {
        if (ql > qr) {
            return;
        }
        if (vl == vr) {
            for (int i = ql; i <= qr; i++) {
                ans[qid[i]] = arr[vl][1];
            }
        } else {
            int mid = (vl + vr) / 2;
            for (int i = vl; i <= mid; i++) {
                add(arr[i][0], 1);
            }
            int lsiz = 0, rsiz = 0;
            for (int i = ql; i <= qr; i++) {
                int id = qid[i];
                int satisfy = query(l[id], r[id]);
                if (satisfy >= k[id]) {
                    lset[++lsiz] = id;
                } else {
                    k[id] -= satisfy;
                    rset[++rsiz] = id;
                }
            }
            for (int i = 1; i <= lsiz; i++) {
                qid[ql + i - 1] = lset[i];
            }
            for (int i = 1; i <= rsiz; i++) {
                qid[ql + lsiz + i - 1] = rset[i];
            }
            for (int i = vl; i <= mid; i++) {
                add(arr[i][0], -1);
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
            arr[i][0] = i;
            in.nextToken(); arr[i][1] = (int) in.nval;
        }
        for (int i = 1; i <= m; i++) {
            qid[i] = i;
            in.nextToken(); l[i] = (int) in.nval;
            in.nextToken(); r[i] = (int) in.nval;
            in.nextToken(); k[i] = (int) in.nval;
        }
        Arrays.sort(arr, 1, n + 1, (a, b) -> a[1] - b[1]);
        compute(1, m, 1, n);
        for (int i = 1; i <= m; i++) {
            out.println(ans[i]);
        }
        out.flush();
        out.close();
        bf.close();
    }
}
