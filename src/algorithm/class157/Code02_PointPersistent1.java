package algorithm.class157;

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
 * @date: 2025/3/1 10:37
 * // 单点修改的可持久化线段树模版题2，java版
 * // 给定一个长度为n的数组arr，下标1~n，一共有m条查询
 * // 每条查询 l r k : 打印arr[l..r]中第k小的数字
 * // 1 <= n、m <= 2 * 10^5
 * // 0 <= arr[i] <= 10^9
 * // 测试链接 : https://www.luogu.com.cn/problem/P3834
 * // 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例
 */
public class Code02_PointPersistent1 {
    public static int MAXN = 200001;
    public static int MAXT = MAXN * 22;
    public static int s;
    public static int[] arr = new int[MAXN];
    public static int[] sorted = new int[MAXN];
    public static int[] root = new int[MAXN];
    public static int[] left = new int[MAXT];
    public static int[] right = new int[MAXT];
    public static int[] size = new int[MAXT];
    public static int cnt = 0;
    public static int n, m;

    public static int kth(int num) {
        int left = 1, right = s, mid, ans = 0;
        while (left <= right) {
            mid = (left + right) >> 1;
            if (sorted[mid] <= num) {
                ans = mid;
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return ans;
    }

    public static int build(int l, int r) {
        int rt = ++cnt;
        size[rt] = 0;
        if (l < r) {
            int mid = (l + r) >> 1;
            left[rt] = build(l, mid);
            right[rt] = build(mid + 1, r);
        }
        return rt;
    }

    public static int insert(int jobi, int l, int r, int i) {
        int rt = ++ cnt;
        left[rt] = left[i];
        right[rt] = right[i];
        size[rt] = size[i] + 1;
        if (l < r) {
            int mid = (l + r) >> 1;
            if (jobi <= mid) {
                left[rt] = insert(jobi, l, mid, left[rt]);
            } else {
                right[rt] = insert(jobi, mid + 1, r, right[rt]);
            }
        }
        return rt;
    }

    public static int query(int jobk, int l, int r, int u, int v) {
        if (l == r) {
            return l;
        }
        int lsize = size[left[v]] - size[left[u]];
        int mid = (l + r) >> 1;
        if (lsize >= jobk) {
            return query(jobk, l, mid, left[u], left[v]);
        } else {
            return query(jobk - lsize, mid + 1, r, right[u], right[v]);
        }
    }

    public static void prepare() {
        cnt = 0;
        for (int i = 1; i <= n; i++) {
            sorted[i] = arr[i];
        }
        Arrays.sort(sorted, 1, n + 1);
        s = 1;
        for (int i = 2; i <= n; i++) {
            if (sorted[s] != sorted[i]) {
                sorted[++s] = sorted[i];
            }
        }
        build(1, s);
        for (int i = 1, x; i <= n; i++) {
            x = kth(arr[i]);
            root[i] = insert(x, 1, s, root[i - 1]);
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
        }
        prepare();
        int l, r, k, rank;
        for (int i = 1; i <= m; i++) {
            in.nextToken(); l = (int) in.nval;
            in.nextToken(); r = (int) in.nval;
            in.nextToken(); k = (int) in.nval;
            rank = query(k, 1, s, root[l - 1], root[r]);
            out.println(sorted[rank]);
        }

        out.flush();
        out.close();
        bf.close();
    }
}
