package algorithm.class87dp22;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2024/7/20 13:48
 * https://www.luogu.com.cn/problem/P1439
 * 最长递增子序列
 */
public class MaxLongCommonSubsequence {
    public static int MAXN = 100001;
    public static int[] a = new int[MAXN];
    public static int[] b = new int[MAXN];
    public static int[] where = new int[MAXN];
    public static int[] ends = new int[MAXN];
    public static int n;
    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StreamTokenizer st = new StreamTokenizer(bf);
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        while (st.nextToken() != StreamTokenizer.TT_EOF) {
            n = (int) st.nval;
            for (int i = 0; i < n; i++) {
                st.nextToken();
                a[i] = (int) st.nval;
            }
            for (int i = 0; i < n; i++) {
                st.nextToken();
                b[i] = (int) st.nval;
            }
            int ans = compute();
            System.out.println(ans);
        }
        out.flush();
        out.close();
        bf.close();
    }

    private static int compute() {
        for (int i = 0; i < n; i++) {
            // a的下标
            where[a[i]] = i;
        }
        for (int i = 0; i < n; i++) {
            b[i] = where[b[i]];
        }
        return lis();
    }

    private static int lis() {
        int len = 0;
        for (int i = 0, find; i < n; i++) {
            find = bs(b[i], len);
            if (find == -1) {
                ends[len++] = b[i];
            } else {
                ends[find] = b[i];
            }
        }
        return len;
    }

    private static int bs(int num, int len) {
        int l = 0, r = len - 1, ans = -1;
        int m = 0;
        while (l <= r) {
            m = (l + r) / 2;
            if (num <= ends[m]) {
                ans = m;
                r = m - 1;
            } else {
                l = m + 1;
            }
        }
        return ans;
    }
}
