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
 * @date: 2025/5/10 13:46
 * // 八纵八横，java版
 * // 一共有n个点，给定m条边，每条边的边权，用01字符串表达，初始时就保证图连通
 * // 初始的m条边永不删除，接下来有q条操作，每种操作是如下三种类型中的一种
 * // 操作 Add x y z  : 加入点x到点y的边，边权是z，z为01字符串，第k条添加操作，边的编号为k
 * // 操作 Cancel k   : 删除编号为k的边
 * // 操作 Change k z : 编号为k的边，边权修改成z，z为01字符串
 * // 从1号点出发，最后回到1号点，边随便走，沿途所有边的边权异或起来
 * // 打印只有初始m条边的情况下，异或最大值为多少，每一条操作结束后，都打印异或最大值为多少
 * // 1 <= n、m <= 500    0 <= q <= 1000    1 <= 边权字符串长度 <= 1000
 * // 测试链接 : https://www.luogu.com.cn/problem/P3733
 * // 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例
 */
public class Code05_EightVerticalHorizontal1 {
    public static int MAXN = 501;
    public static int MAXQ = 1001;
    public static int MAXT = 10001;
    public static int BIT = 999;
    public static int INT_BIT = 32;
    static class BitSet {
        public int len;
        public int[] arr;

        public BitSet() {
            len = BIT / INT_BIT + 1;
            arr = new int[len];
        }

        public BitSet(String s) {
            len = BIT / INT_BIT + 1;
            arr = new int[len];
            for (int i = 0, j = s.length() - 1; i < s.length(); i++, j--) {
                set(i, s.charAt(j) - '0');
            }
        }

        public int get(int i) {
            return (arr[i / INT_BIT] >> (i % INT_BIT)) & 1;
        }

        public void set(int i, int v) {
            if (v == 0) {
                arr[i / INT_BIT] &= ~(1 << (i % INT_BIT));
            } else {
                arr[i / INT_BIT] |= 1 << (i % INT_BIT);
            }
        }

        public void eor(BitSet other) {
            for (int i = 0; i < len; i++) {
                arr[i] ^= other.arr[i];
            }
        }

        public void clear() {
            for (int i = 0; i < len; i++) {
                arr[i] = 0;
            }
        }
    }
    public static int n, m, q;
    public static int[] x = new int[MAXQ];
    public static int[] y = new int[MAXQ];
    public static BitSet[] w = new BitSet[MAXQ];

    public static int edgeCnt = 0;
    public static int[] last = new int[MAXQ];

    public static BitSet[] basic = new BitSet[BIT + 1];
    public static int[] inspos = new int[BIT + 1];
    public static int basiz = 0;

    public static int[] father = new int[MAXN];
    public static BitSet[] eor = new BitSet[MAXN];

    public static int[] head = new int[MAXQ << 2];
    public static int[] next = new int[MAXT];
    public static int[] tox = new int[MAXT];
    public static int[] toy = new int[MAXT];
    public static BitSet[] tow = new BitSet[MAXT];
    public static int cnt = 0;

    public static BitSet[] ans = new BitSet[MAXQ];

    public static boolean insert(BitSet num) {
        for (int i = BIT; i >= 0; i--) {
            if (num.get(i) == 1) {
                if (basic[i].get(i) == 0) {
                    basic[i] = num;
                    inspos[basiz++] = i;
                    return true;
                }
                num.eor(basic[i]);
            }
        }
        return false;
    }

    public static BitSet maxEor() {
        BitSet ans = new BitSet();
        for (int i = BIT; i >= 0; i--) {
            if (ans.get(i) == 0 && basic[i].get(i) == 1) {
                ans.eor(basic[i]);
            }
        }
        return ans;
    }

    public static void cancel(int oldsize) {
        while (basiz > oldsize) {
            basic[inspos[--basiz]].clear();
        }
    }

    public static int find(int i) {
        if (i != father[i]) {
            int tmp = father[i];
            father[i] = find(tmp);
            eor[i].eor(eor[tmp]);
        }
        return father[i];
    }

    public static BitSet getEor(int i) {
        find(i);
        return eor[i];
    }

    public static void union(int u, int v, BitSet w) {
        int fu = find(u);
        int fv = find(v);
        BitSet weight = new BitSet();
        weight.eor(getEor(u));
        weight.eor(getEor(v));
        weight.eor(w);
        if (fu == fv) {
            insert(weight);
        } else {
            father[fv] = fu;
            eor[fv] = weight;
        }
    }

    public static void addEdge(int i, int x, int y, BitSet w) {
        next[++cnt] = head[i];
        tox[cnt] = x;
        toy[cnt] = y;
        tow[cnt] = w;
        head[i] = cnt;
    }

    public static void add(int jobl, int jobr, int jobx, int joby, BitSet jobw, int l, int r, int i) {
        if (jobl <= l && r <= jobr) {
            addEdge(i, jobx, joby, jobw);
        } else {
            int mid = (l + r) / 2;
            if (jobl <= mid) {
                add(jobl, jobr, jobx, joby, jobw, l, mid, i << 1);
            }
            if (jobr > mid) {
                add(jobl, jobr, jobx, joby, jobw, mid + 1, r, i << 1 | 1);
            }
        }
    }

    public static void dfs(int l, int r, int i) {
        int oldsize = basiz;
        for(int e = head[i]; e > 0; e = next[e]) {
            union(tox[e], toy[e], tow[e]);
        }
        if (l == r) {
            ans[l] = maxEor();
        } else {
            int mid = (l + r) / 2;
            dfs(l, mid, i << 1);
            dfs(mid + 1, r, i << 1 | 1);
        }
        cancel(oldsize);
    }

    public static void print(BitSet bs, PrintWriter out) {
        boolean flag = false;
        for(int i = BIT, s; i >= 0; i--) {
            s = bs.get(i);
            if (s == 1) {
                flag = true;
            }
            if (flag) {
                out.print(s);
            }
        }
        if (!flag) {
            out.print(0);
        }
        out.println();
    }

//    public static void main(String[] args) throws IOException {
//        FastReader in = new FastReader();
//        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
//        n = in.nextInt();
//        m = in.nextInt();
//        q = in.nextInt();
//        for (int i = 0; i <= BIT; i++) {
//            basic[i] = new BitSet();
//        }
//        for (int i = 1; i <= n; i++) {
//            father[i] = i;
//            eor[i] = new BitSet();
//        }
//        for (int i = 1; i <= m; i++) {
//            int u = in.nextInt();
//            int v = in.nextInt();
//            BitSet w = new BitSet(in.nextString());
//            union(u, v, w);
//        }
//        ans[0] = maxEor();
//        String op;
//        for (int i = 1; i <= q; i++) {
//            op = in.nextString();
//            if (op.equals("Add")) {
//                edgeCnt++;
//                x[edgeCnt] = in.nextInt();
//                y[edgeCnt] = in.nextInt();
//                w[edgeCnt] = new BitSet(in.nextString());
//                last[edgeCnt] = i;
//            } else if (op.equals("Cancel")) {
//                int k = in.nextInt();
//                add(last[k], i - 1, x[k], y[k], w[k], 1, q, 1);
//                last[k] = 0;
//            } else {
//                int k = in.nextInt();
//                add(last[k], i - 1, x[k], y[k], w[k], 1, q, 1);
//                w[k] = new BitSet(in.nextString());
//                last[k] = i;
//            }
//        }
//        for (int i = 1; i <= edgeCnt; i++) {
//            if (last[i] != 0) {
//                add(last[i], q, x[i], y[i], w[i], 1, q, 1);
//            }
//        }
//        if (q > 0) {
//            dfs(1, q, 1);
//        }
//        for (int i = 0; i <= q; i++) {
//            print(ans[i], out);
//        }
//        out.flush();
//        out.close();
//    }
//
//    // 读写工具类
//    static class FastReader {
//        private static final int BUFFER_SIZE = 1 << 16;
//        private final InputStream in;
//        private final byte[] buffer;
//        private int ptr, len;
//
//        public FastReader() {
//            in = System.in;
//            buffer = new byte[BUFFER_SIZE];
//            ptr = len = 0;
//        }
//
//        private boolean hasNextByte() throws IOException {
//            if (ptr < len) {
//                return true;
//            }
//            ptr = 0;
//            len = in.read(buffer);
//            return len > 0;
//        }
//
//        private byte readByte() throws IOException {
//            if (!hasNextByte()) {
//                return -1;
//            }
//            return buffer[ptr++];
//        }
//
//        public int nextInt() throws IOException {
//            int num = 0;
//            byte b = readByte();
//            while (isWhitespace(b)) {
//                b = readByte();
//            }
//            boolean minus = false;
//            if (b == '-') {
//                minus = true;
//                b = readByte();
//            }
//            while (!isWhitespace(b) && b != -1) {
//                num = num * 10 + (b - '0');
//                b = readByte();
//            }
//            return minus ? -num : num;
//        }
//
//        public String nextString() throws IOException {
//            byte b = readByte();
//            while (isWhitespace(b)) {
//                b = readByte();
//            }
//            StringBuilder sb = new StringBuilder(1000);
//            while (!isWhitespace(b) && b != -1) {
//                sb.append((char) b);
//                b = readByte();
//            }
//            return sb.toString();
//        }
//
//        private boolean isWhitespace(byte b) {
//            return b == ' ' || b == '\n' || b == '\r' || b == '\t';
//        }
//    }


    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StreamTokenizer in = new StreamTokenizer(bf);
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
//        in.nextToken(); n = (int) in.nval;
//        in.nextToken(); m = (int) in.nval;
//        in.nextToken(); q = (int) in.nval;
        String[] line = bf.readLine().split(" ");
        n = Integer.parseInt(line[0]);
        m = Integer.parseInt(line[1]);
        q = Integer.parseInt(line[2]);
        for (int i = 0; i <= BIT; i++) {
            basic[i] = new BitSet();
        }
        for (int i = 1; i <= n; i++) {
            father[i] = i;
            eor[i] = new BitSet();
        }

        for (int i = 1, u, v; i <= m; i++) {
            String[] arr = bf.readLine().split(" ");
//            in.nextToken(); u = (int) in.nval;
//            in.nextToken(); v = (int) in.nval;
//            in.nextToken(); String w = in.sval;
            union(Integer.parseInt(arr[0]), Integer.parseInt(arr[1]), new BitSet(arr[2]));
        }
        ans[0] = maxEor();
        String op;
        for (int i = 1, k; i <= q; i++) {
            String[] arr = bf.readLine().split(" ");
            // in.nextToken(); op = in.sval;
            op = arr[0];
            if (op.equals("Add")) {
                edgeCnt++;
                x[edgeCnt] = Integer.parseInt(arr[1]);
                y[edgeCnt] = Integer.parseInt(arr[2]);
                w[edgeCnt] = new BitSet(arr[3]);
//                in.nextToken(); x[edgeCnt] = (int) in.nval;
//                in.nextToken(); y[edgeCnt] = (int) in.nval;
//                in.nextToken(); w[edgeCnt] = new BitSet(in.sval);
                last[edgeCnt] = i;
            } else if (op.equals("Cancel")) {
                // in.nextToken(); k = (int) in.nval;
                k = Integer.parseInt(arr[1]);
                add(last[k], i - 1, x[k], y[k], w[k], 1, q, 1);
                last[k] = 0;
            } else {
                // in.nextToken(); k = (int) in.nval;
                k = Integer.parseInt(arr[1]);
                add(last[k], i - 1, x[k], y[k], w[k], 1, q, 1);
                // in.nextToken(); w[k] = new BitSet(in.sval);
                w[k] = new BitSet(arr[2]);
                last[k] = i;
            }
        }

        for (int i = 1; i <= edgeCnt; i++) {
            if (last[i] != 0) {
                add(last[i], q, x[i], y[i], w[i], 1, q, 1);
            }
        }
        if (q > 0) {
            dfs(1, q, 1);
        }

        for (int i = 0; i <= q; i++) {
            print(ans[i], out);
        }
        out.flush();
        out.close();
        bf.close();
    }
}
