package algorithm.class114segmenttree05;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2024/9/15 9:02
 * 对数器验证
 * // 线段树的区间最值操作
 * // 给定一个长度为n的数组arr，实现支持以下三种操作的结构
 * // 操作 0 l r x : 把arr[l..r]范围的每个数v，更新成min(v, x)
 * // 操作 1 l r   : 查询arr[l..r]范围上的最大值
 * // 操作 2 l r   : 查询arr[l..r]范围上的累加和
 * // 三种操作一共调用m次，做到时间复杂度O(n * log n + m * log n)
 * https://acm.hdu.edu.cn/showproblem.php?pid=5306
 */
public class Code03_SegmentTreeSetMinQueryMaxSum2 {
    public static int MAXN = 1000001;
    // 比题目给定的范围小的值
    public static int LOWEST = -1;
    public static int[] arr = new int[MAXN];
    // 求和
    public static long[] sum = new long[MAXN << 2];
    // 最大值
    public static int[] max = new int[MAXN << 2];
    // second max 次大值
    public static int[] sem = new int[MAXN << 2];
    // 最大值个数
    public static int[] cnt = new int[MAXN << 2];

    public static void up(int i) {
        int l = i << 1;
        int r = i << 1 | 1;
        sum[i] = sum[l] + sum[r];
        max[i] = Math.max(max[l], max[r]);
        if (max[l] > max[r]) {
            // pk出最大值个数和次大值
            cnt[i] = cnt[l];
            sem[i] = Math.max(sem[l], max[r]);
        } else if (max[l] < max[r]) {
            cnt[i] = cnt[r];
            sem[i] = Math.max(max[l], sem[r]);
        } else {
            cnt[i] = cnt[l] + cnt[r];
            sem[i] = Math.max(sem[l], sem[r]);
        }
    }

    public static void down(int i) {
        lazy(i << 1, max[i]);
        lazy(i << 1 | 1, max[i]);
    }

    private static void lazy(int i, int v) {
        if (v < max[i]) {
            sum[i] -= ((long) max[i] - v) * cnt[i];
            max[i] = v;
        }
    }

    public static void build(int l, int r, int i) throws IOException {
        if (l == r) {
			in.nextToken();
			sum[i] = max[i] = (int) in.nval;
            cnt[i] = 1;
            sem[i] = LOWEST;
        } else {
            int mid = (l + r) >> 1;
            build(l, mid, i << 1);
            build(mid + 1, r, i << 1 | 1);
            up(i);
        }
    }

    public static void setMin(int jobl, int jobr, int jobv, int l, int r, int i) {
        if (jobv >= max[i]) {
            // 更新的值比最大值大，直接剪枝
            return;
        }
        if (jobl <= l && r <= jobr && sem[i] < jobv) {
            // 全包并且大于次大值
            lazy(i, jobv);
        } else {
            // 到这说明下列两种情况
            // 1.没有全包
            // 2.小于等于次大值
            int mid = (l + r) >> 1;
            down(i);
            if (jobl <= mid) {
                setMin(jobl, jobr, jobv, l, mid, i << 1);
            }
            if (jobr > mid) {
                setMin(jobl, jobr, jobv, mid + 1, r, i << 1 | 1);
            }
            up(i);
        }
    }

    public static int queryMax(int jobl, int jobr, int l, int r, int i) {
        if (jobl <= l && r <= jobr) {
            return max[i];
        }
        int mid = (l + r) >> 1;
        down(i);
        int ans = Integer.MIN_VALUE;
        if (jobl <= mid) {
            ans = Math.max(ans, queryMax(jobl, jobr, l, mid, i << 1));
        }
        if (jobr > mid) {
            ans = Math.max(ans, queryMax(jobl, jobr, mid + 1, r, i << 1 | 1));
        }
        return ans;
    }

    public static long querySum(int jobl, int jobr, int l, int r, int i) {
        if (jobl <= l && r <= jobr) {
            return sum[i];
        }
        int mid = (l + r) >> 1;
        down(i);
        long ans = 0;
        if (jobl <= mid) {
            ans += querySum(jobl, jobr, l, mid, i << 1);
        }
        if (jobr > mid) {
            ans += querySum(jobl, jobr, mid + 1, r, i << 1 | 1);
        }
        return ans;
    }
	public static BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
	public static StreamTokenizer in = new StreamTokenizer(bf);
	public static PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
    public static void main(String[] args) throws IOException {
        in.nextToken();
        int t = (int) in.nval;
        for (int i = 0; i < t; i++) {
            in.nextToken();
            int n = (int) in.nval;
			in.nextToken();
			int m = (int) in.nval;

            build(1, n, 1);

            for (int k = 1, op, jobl, jobr, x; k <= m; k++) {
                in.nextToken(); op = (int) in.nval;
                in.nextToken(); jobl = (int) in.nval;
                in.nextToken(); jobr = (int) in.nval;
                if (op == 0) {
                    in.nextToken(); x = (int) in.nval;
                    setMin(jobl, jobr, x, 1, n, 1);
                } else if (op == 1){
                    System.out.println(queryMax(jobl, jobr, 1, n, 1));
                } else {
                    System.out.println(querySum(jobl, jobr, 1, n, 1));
                }
            }
        }
        out.flush();
        out.close();
        bf.close();
    }
}
