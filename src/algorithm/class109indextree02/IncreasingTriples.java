package algorithm.class109indextree02;

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
 * @date: 2024/8/25 10:37
 * https://www.luogu.com.cn/problem/P1637
 */
public class IncreasingTriples {
    public static int MAXN = 30001;
    public static int[] arr = new int[MAXN];
    public static int[] sort = new int[MAXN];
    // 上升一元组
    public static int[] tree2 = new int[MAXN];
    // 上升二元组
    public static int[] tree1 = new int[MAXN];

    public static int n, m;

    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StreamTokenizer in = new StreamTokenizer(bf);
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        while (in.nextToken() != StreamTokenizer.TT_EOF) {
            n = (int) in.nval;
            for (int i = 1; i <= n; i++) {
                in.nextToken();
                arr[i] = (int) in.nval;
                sort[i] = arr[i];
            }
            System.out.println(compute());
        }
        out.flush();
        out.close();
        bf.close();
    }

    private static long compute() {
        Arrays.sort(sort, 0, n + 1);
        m = 1;
        for (int i = 2; i <= n; i++) {
            if (sort[m] != sort[i]) {
                sort[++m] = sort[i];
            }
        }
        for (int i = 1; i <= n; i++) {
            arr[i] = rank(arr[i]);
        }
        long ans = 0;
        for (int i = 1; i <= n; i++) {
            // 上升二元组，小于arr[i] 的前缀和就是一个答案，累计
            ans += sum(tree2, arr[i] - 1);
            // 上升一元组的词频加1
            add(tree1, arr[i], 1);
            // 上升二元组的加上一元组，小于等于arr[i]-1的和
            add(tree2, arr[i], sum(tree1, arr[i] - 1));
        }
        return ans;
    }

    private static void add(int[] tree, int i, int v) {
        while (i <= m) {
            tree[i] += v;
            i += lowBit(i);
        }
    }

    private static int sum(int[] tree, int i) {
        int ans = 0;
        while (i > 0) {
            ans += tree[i];
            i -= lowBit(i);
        }
        return ans;
    }

    private static int lowBit(int i) {
        return i & -i;
    }

    private static int rank(int v) {
        int l = 1, r = m, mid;
        int ans = 0;
        while (l <= r) {
            mid = (l + r) / 2;
            if (sort[mid] >= v) {
                ans = mid;
                r = mid - 1;
            } else {
                l = mid + 1;
            }
        }
        return ans;
    }
}
