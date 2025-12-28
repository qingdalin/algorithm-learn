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
 * @date: 2024/8/25 9:10
 * https://www.luogu.com.cn/problem/P1908
 * 树状数组的方法
 */
public class NumberOfReversePair2 {
    public static int MAXN = 500001;

    // 维护差分数组的树状数组信息
    public static int[] arr = new int[MAXN];
    public static int[] sort = new int[MAXN];
    public static int[] tree = new int[MAXN];

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
        // 将sort去重排序，m的大小就是树状数组的大小
        m = 1;
        for (int i = 2; i <= n; i++) {
            if (sort[m] != sort[i]) {
                sort[++m] = sort[i];
            }
        }
        for (int i = 1; i <= n; i++) {
            arr[i] = rank1(arr[i]);
        }
        long ans = 0;
        for (int i = n; i >= 1; i--) {
            // 词频加1
            add(arr[i], 1);
            ans += sum(arr[i] - 1);
        }
        return ans;
    }

    private static long sum(int i) {
        long ans = 0;
        while (i > 0) {
            ans += tree[i];
            i -= lowBit(i);
        }
        return ans;
    }

    private static void add(int i, int v) {
        while (i <= m) {
            tree[i] += v;
            i += lowBit(i);
        }
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

    private static int rank1(int v) {
        int l = 1, r = m, mid;
        int ans = 0;
        while (l <= r) {
            mid = (l + r) / 2;
            if (sort[mid] <= v) {
                ans = mid;
                l = mid + 1;
            } else {
                r = mid - 1;
            }
        }
        return ans;
    }


}
