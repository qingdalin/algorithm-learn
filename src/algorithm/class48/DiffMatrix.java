package algorithm.class48;

import java.io.*;

/**
 * 二维差分
 * https://www.nowcoder.com/practice/50e1a93989df42efb0b1dec386fb4ccc?tpId=230&tqId=38963&ru=/exam/oj
 * @author 汪大鹏
 * @version 1.0
 * @date 2024-02-03 17:05
 */
public class DiffMatrix {
    public static int m, n, q;
    public static long[][] matrix = new long[1005][1005];

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StreamTokenizer st = new StreamTokenizer(br);
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        while (st.nextToken() != StreamTokenizer.TT_EOF) {
            n = (int) st.nval;
            st.nextToken();
            m = (int) st.nval;
            st.nextToken();
            q = (int) st.nval;
            for (int i = 1; i <= n; i++) {
                for (int j = 1; j <= m; j++) {
                    st.nextToken();
                    add(i, j, i, j, (int) st.nval);
                }
            }
            for (int i = 1, a, b, c, d, k; i <= q; i++) {
                st.nextToken();
                a = (int) st.nval;
                st.nextToken();
                b = (int) st.nval;
                st.nextToken();
                c = (int) st.nval;
                st.nextToken();
                d = (int) st.nval;
                st.nextToken();
                k = (int) st.nval;
                add(a, b, c, d, k);
            }
            build();
            for (int i = 1; i <= n; i++) {
                out.print(matrix[i][1]);
                for (int j = 2; j <= m; j++) {
                    out.print(" " + matrix[i][j]);
                }
                out.println();
            }
            clear();
        }
        out.flush();
        out.close();
        br.close();
    }

    private static void clear() {
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                matrix[i][j] = 0;
            }
        }
    }

    private static void build() {
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                matrix[i][j] += matrix[i][j - 1] + matrix[i - 1][j] - matrix[i - 1][j - 1];
            }
        }
    }

    private static void add(int a, int b, int c, int d, int v) {
        matrix[a][b] += v;
        matrix[c + 1][b] -= v;
        matrix[a][d + 1] -= v;
        matrix[c + 1][d + 1] += v;
    }
}
