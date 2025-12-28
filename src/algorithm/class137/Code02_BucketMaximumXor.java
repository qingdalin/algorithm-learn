package algorithm.class137;

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
 * @date: 2024/12/4 20:26
 * // P哥的桶
 * // 一共有n个桶，排成一排，编号1~n，每个桶可以装下任意个数字
 * // 高效的实现如下两个操作
 * // 操作 1 k v : 把数字v放入k号桶中
 * // 操作 2 l r : 可以从l..r号桶中随意拿数字，返回异或和最大的结果
 * // 1 <= n、m <= 5 * 10^4
 * // 0 <= v <= 2^31 - 1
 * // 测试链接 : https://www.luogu.com.cn/problem/P4839
 * // 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例
 */
public class Code02_BucketMaximumXor {
    public static int MAXN = 50001;
    public static int n, m;
    public static int BIT = 30;
    public static int[][] treeBasis = new int[MAXN << 2][BIT + 1];
    public static int[] basis = new int[MAXN]; // 商品的编号
    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StreamTokenizer in = new StreamTokenizer(bf);
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        in.nextToken(); n = (int) in.nval;
        in.nextToken(); m = (int) in.nval;
        for (int i = 1, op; i <= n; i++) {
            in.nextToken(); op = (int) in.nval;
            if (op == 1) {
                in.nextToken(); int jobi = (int) in.nval;
                in.nextToken(); int jobv = (int) in.nval;
                add(jobi, jobv, 1, m, 1);
            } else {
                in.nextToken(); int jobl = (int) in.nval;
                in.nextToken(); int jobr = (int) in.nval;
                out.println(query(jobl, jobr, m));
            }
        }
        out.flush();
        out.close();
        bf.close();
    }

    private static int query(int jobl, int jobr, int m) {
        Arrays.fill(basis, 0);
        merge(jobl, jobr, 1, m, 1);
        int ans = 0;
        for (int j = BIT; j >= 0; j--) {
            ans = Math.max(ans, ans ^ basis[j]);
        }
        return ans;
    }

    private static void merge(int jobl, int jobr, int l, int r, int i) {
        if (jobl <= l && r <= jobr) {
            for (int j = BIT; j >= 0; j--) {
                if (treeBasis[i][j] != 0) {
                    inert(basis, treeBasis[i][j]);
                }
            }
        } else {
            int mid = (l + r) >> 1;
            if (jobl <= mid) {
                merge(jobl, jobr, l, mid, i << 1);
            }
            if (jobr > mid) {
                merge(jobl, jobr, mid + 1, r, i << 1 | 1);
            }
        }

    }

    private static void add(int jobi, int jobv, int l, int r, int i) {
        inert(treeBasis[i], jobv);
        if (l < r) {
            int mid = (l + r) >> 1;
            if (jobi <= mid) {
                add(jobi, jobv, l, mid, i << 1);
            } else {
                add(jobi, jobv, mid + 1, r, i << 1 | 1);
            }
        }
    }

    private static boolean inert(int[] basis, int num) {
        for (int i = BIT; i >= 0; i--) {
            if (num >> i == 1) {
                if (basis[i] == 0) {
                    basis[i] = num;
                    return true;
                }
                num ^= basis[i];
            }
        }
        return false;
    }
}
