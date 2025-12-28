package algorithm.class167;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/5/9 20:31
 * // 打印所有合法数，java版
 * // 一个长度为n的序列，一开始所有值都是0
 * // 一共有q条操作，每条操作为 l r k : 序列[l..r]范围上，每个数字加k
 * // 你可以随意选择操作来执行，但是每条操作只能执行一次
 * // 如果你能让序列中的最大值正好为v，那么v就算一个合法数
 * // 打印1~n范围内有多少合法数，并且从小到大打印所有的合法数
 * // 1 <= k <= n、q <= 10^4
 * // 测试链接 : https://www.luogu.com.cn/problem/CF981E
 * // 测试链接 : https://codeforces.com/problemset/problem/981/E
 * // 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例
 */
public class Code03_AdditionOnSegments1 {
    public static int MAXN = 10001;
    public static int MAXT = 500001;
    public static int BIT = 10000;
    public static int DEEP = 20;
    public static int INT_BIT = 32;
    public static int LEN = BIT / INT_BIT + 1;
    public static int n, q;
    public static int[] head = new int[MAXN << 2];
    public static int[] next = new int[MAXT];
    public static int[] to = new int[MAXT];
    public static int cnt = 0;
    public static int[] tmp = new int[LEN];
    public static int[] dp = new int[LEN];
    public static int[] ans = new int[LEN];
    public static int[][] backup = new int[DEEP][LEN];

    public static void clear(int[] bitset) {
        for (int i = 0; i < LEN; i++) {
            bitset[i] = 0;
        }
    }

    public static void clone(int[] set1, int[] set2) {
        for (int i = 0; i < LEN; i++) {
            set1[i] = set2[i];
        }
    }

    public static int getBit(int[] bitset, int i) {
        return (bitset[i / INT_BIT] >> (i % INT_BIT)) & 1;
    }

    public static void setBit(int[] bitset, int i, int v) {
        if (v == 0) {
            bitset[i / INT_BIT] &= ~(1 << (i % INT_BIT));
        } else {
            bitset[i / INT_BIT] |= 1 << (i % INT_BIT);
        }
    }

    public static void bitOr(int[] set1, int[] set2) {
        for (int i = 0; i < LEN; i++) {
            set1[i] |= set2[i];
        }
    }

    public static void bitLeft(int[] ret, int[] bitset, int move) {
        clear(ret);
        if (move > BIT) {
            return;
        }
        if (move <= 0) {
            clone(ret, bitset);
            return;
        }
        int shift = move / INT_BIT;
        int offset = move % INT_BIT;
        if (offset == 0) {
            for (int i = LEN - 1, j = i - shift; j >= 0; i--, j--) {
                ret[i] = bitset[j];
            }
        } else {
            int carry = INT_BIT - offset, high, low;
            for (int i = LEN - 1; i > shift; i--) {
                high = bitset[i - shift] << offset;
                low = bitset[i - shift - 1] >>> carry;
                ret[i] = high | low;
            }
            ret[shift] = bitset[0] << offset;
        }
        // 最高位BIT到最低位0，一共BIT+1个有效位
        // 其他更高位信息需要清空，rest就是有多少无效的更高位
        int rest = INT_BIT * LEN - (BIT + 1);
        if (rest > 0) {
            ret[LEN - 1] &= (1 << (INT_BIT - rest)) - 1;
        }
    }

    public static void addEdge(int i, int v) {
        next[++cnt] = head[i];
        to[cnt] = v;
        head[i] = cnt;
    }

    public static void add(int jobl, int jobr, int jobv, int l, int r, int i) {
        if (jobl <= l && r <= jobr) {
            addEdge(i, jobv);
        } else {
            int mid = (l + r) / 2;
            if (jobl <= mid) {
                add(jobl, jobr, jobv, l, mid, i << 1);
            }
            if (jobr > mid) {
                add(jobl, jobr, jobv, mid + 1, r, i << 1 | 1);
            }
        }
    }

    public static void dfs(int l, int r, int i, int dep) {
        clone(backup[dep], dp);
        for(int e = head[i]; e > 0; e = next[e]) {
            bitLeft(tmp, dp, to[e]);
            bitOr(dp, tmp);
        }
        if (l == r) {
            bitOr(ans, dp);
        } else {
            int mid = (l + r) / 2;
            dfs(l, mid, i << 1, dep + 1);
            dfs(mid + 1, r, i << 1 | 1, dep + 1);
        }
        clone(dp, backup[dep]);
    }

    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StreamTokenizer in = new StreamTokenizer(bf);
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        in.nextToken(); n = (int) in.nval;
        in.nextToken(); q = (int) in.nval;
        for (int i = 1, l, r, k; i <= q; i++) {
            in.nextToken(); l = (int) in.nval;
            in.nextToken(); r = (int) in.nval;
            in.nextToken(); k = (int) in.nval;
            add(l, r, k, 1, n, 1);
        }
        setBit(dp, 0, 1);
        dfs(1, n, 1, 1);
        int ansCnt = 0;
        for (int i = 1; i <= n; i++) {
            if (getBit(ans, i) == 1) {
                ansCnt++;
            }
        }
        out.println(ansCnt);
        for (int i = 1; i <= n; i++) {
            if (getBit(ans, i) == 1) {
                out.print(i + " ");
            }
        }
        out.flush();
        out.close();
        bf.close();
    }
}
