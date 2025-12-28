package algorithm.class56UnionFind;

import java.io.*;

/**
 * @author 汪大鹏
 * @version 1.0
 * @date 2024-03-16 15:48
 * https://www.nowcoder.com/practice/e7ed657974934a30b2010046536a5372
 */
public class UnionFind {
    public static int MAXN = 1000000;
    public static int[] father = new int[MAXN];
    public static int[] size = new int[MAXN];
    public static int[] stack = new int[MAXN];
    public static int n, m;
    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StreamTokenizer st = new StreamTokenizer(bf);
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        while (st.nextToken() != StreamTokenizer.TT_EOF) {
            n = (int) st.nval;
            st.nextToken();
            m = (int) st.nval;
            build();
            for (int i = 0; i < m; i++) {
                st.nextToken();
                int opt = (int) st.nval;
                st.nextToken();
                int x = (int) st.nval;
                st.nextToken();
                int y = (int) st.nval;
                if (opt == 1) {
                    out.println(isSameSet(x, y) ? "Yes" : "No");
                    // 输出是否为同一集合
                } else {
                    // 合并
                    union(x, y);
                }
            }

        }
        out.flush();
        out.close();
        bf.close();
    }

    public static int find(int i) {
        int size = 0;
        while (i != father[i]) {
            stack[size++] = i;
            i = father[i];
        }
        while (size > 0) {
            father[stack[--size]] = i;
        }
        return i;
    }

    public static boolean isSameSet(int a, int b) {
        return find(a) == find(b);
    }

    public static void union(int x, int y) {
        int fx = find(x);
        int fy = find(y);
        if (fx != fy) {
            if (size[fx] >= size[fy]) {
                size[fx] += size[fy];
                father[fy] = fx;
            } else {
                size[fy] += size[fx];
                father[fx] = fy;
            }
        }
    }

    public static void build() {
        for (int i = 0; i <= n; i++) {
            father[i] = i;
            size[i] = 1;
        }
    }
}
