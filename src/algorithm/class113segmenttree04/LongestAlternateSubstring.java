package algorithm.class113segmenttree04;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2024/9/11 20:04
 * https://www.luogu.com.cn/problem/P6492
 */
public class LongestAlternateSubstring {
    public static int MAXN = 200001;
    public static int[] arr = new int[MAXN];
    public static int[] len = new int[MAXN << 2];
    public static int[] pre = new int[MAXN << 2];
    public static int[] suf = new int[MAXN << 2];

    public static void up(int l, int r, int i) {
        len[i] = Math.max(len[i << 1], len[i << 1 | 1]);
        pre[i] = pre[i << 1];
        suf[i] = suf[i << 1 | 1];
        int mid = (l + r) / 2;
        int ln = mid - l + 1;
        int rn = r - mid;
        // 只有当左侧最右一个和右侧的最左不等，进行下列判断
        if (arr[mid] != arr[mid + 1]) {
            len[i] = Math.max(len[i], suf[i << 1] + pre[i << 1 | 1]);
            if (len[i << 1] == ln) {
                pre[i] = ln + pre[i << 1 | 1];
            }
            if (len[i << 1 | 1] == rn) {
                suf[i] = rn + suf[i << 1];
            }
        }
    }

    public static void build(int l, int r, int i) {
        if (l == r) {
            len[i] = 1;
            pre[i] = 1;
            suf[i] = 1;
        } else {
            int mid = (l + r) >> 1;
            build(l, mid, i << 1);
            build(mid + 1, r, i << 1 | 1);
            up(l, r, i);
        }
    }

    public static void reverse(int jobi, int l, int r, int i) {
        if (l == r) {
            arr[l] ^= 1;
        } else {
            int mid = (l + r) >> 1;
            if (jobi <= mid) {
                reverse(jobi, l, mid, i << 1);
            } else {
                reverse(jobi, mid + 1, r, i << 1 | 1);
            }
            up(l, r, i);
        }
    }
    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StreamTokenizer in = new StreamTokenizer(bf);
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        while (in.nextToken() != StreamTokenizer.TT_EOF) {
            int n = (int) in.nval;
            in.nextToken();
            int q = (int) in.nval;

            build(1, n, 1);
            for (int i = 1; i <= q; i++) {
                in.nextToken();
                int index = (int) in.nval;
                reverse(index, 1, n, 1);
                System.out.println(len[1]);
            }
        }
        out.flush();
        out.close();
        bf.close();
    }
}
