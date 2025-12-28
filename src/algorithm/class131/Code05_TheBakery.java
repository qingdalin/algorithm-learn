package algorithm.class131;

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
 * @date: 2024/11/10 19:27
 * // 划分k段的最大得分
 * // 给定一个长度为n的数组，最多可以分成k段不重合的子数组
 * // 每个子数组获得的分值为内部不同数字的个数
 * // 返回能获得的最大分值
 * // 1 <= n <= 35000
 * // 1 <= k <= 50
 * // k <= n
 * // 1 <= arr[i] <= n
 * // 测试链接 : https://www.luogu.com.cn/problem/CF833B
 * // 测试链接 : https://codeforces.com/problemset/problem/833/B
 * // 提交以下的code，提交时请把类名改成"Main"，可以通过所有用例
 */
public class Code05_TheBakery {
    public static int MAXN = 35001;
    public static int MAXK = 51;
    public static int n, k;
    public static int[] arr = new int[MAXN];
    public static int[] pre = new int[MAXN];
    public static int[] dp = new int[MAXN];
    public static int[] max = new int[MAXN << 2];
    public static int[] add = new int[MAXN << 2];
    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StreamTokenizer in = new StreamTokenizer(bf);
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        in.nextToken(); n = (int) in.nval;
        in.nextToken(); k = (int) in.nval;
        for (int i = 1; i <= n; i++) {
            in.nextToken();
            arr[i] = (int) in.nval;
        }
        System.out.println(compute());
        out.flush();
        out.close();
        bf.close();
    }

    private static int compute() {
        Arrays.fill(dp, 1, n + 1, 0);
        for (int t = 1; t <= k; t++) {
            build(0, n, 1);
            Arrays.fill(pre, 0, n + 1, 0);
            for (int i = 1; i <= n; i++) {
                // 从上一次出现的位置，到i-1位置统一加1
                add(pre[arr[i]], i - 1, 1, 0, n, 1);
                if (i >= t) {
                    dp[i] = query(0, i - 1, 0, n, 1);
                }
                pre[arr[i]] = i;
            }
        }
        return dp[n];
    }

    private static int query(int jobl, int jobr, int l, int r, int i) {
        if (jobl <= l && r <= jobr) {
            return max[i];
        }
        down(i);
        int mid = (l + r) / 2;
        int ans = Integer.MIN_VALUE;
        if (jobl <= mid) {
            ans = Math.max(ans, query(jobl, jobr, l, mid, i << 1));
        }
        if (jobr > mid) {
            ans = Math.max(ans, query(jobl, jobr, mid + 1, r, i << 1 | 1));
        }
        return ans;
    }

    private static void add(int jobl, int jobr, int jobv, int l, int r, int i) {
        if (jobl <= l && r <= jobr) { // 这里写错了
            lazy(i, jobv);
        } else {
            down(i);
            int mid = (l + r) / 2;
            if (jobl <= mid) {
                add(jobl, jobr, jobv, l, mid, i << 1);
            }
            if (jobr > mid) {
                add(jobl, jobr, jobv, mid + 1, r, i << 1 | 1);
            }
            up(i);
        }
    }

    public static void down(int i) {
        if (add[i] != 0) {
            lazy(i << 1, add[i]);
            lazy(i << 1 | 1, add[i]);
            add[i] = 0;
        }
    }

    private static void lazy(int i, int v) {
        max[i] += v;
        add[i] += v;
    }

    private static void build(int l, int r, int i) {
        if (l == r) {
            max[i] = dp[l];
        } else {
            int m = (l + r) / 2;
            build(l, m, i << 1);
            build(m + 1, r, i << 1 | 1);
            up(i);
        }
        add[i] = 0;
    }

    private static void up(int i) {
        max[i] = Math.max(max[i << 1], max[i << 1 | 1]);
    }
}
