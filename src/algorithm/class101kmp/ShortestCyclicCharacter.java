package algorithm.class101kmp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2024/8/10 9:10
 * https://www.luogu.com.cn/problem/P4391
 */
public class ShortestCyclicCharacter {
    public static int MAXN = 1000001;
    public static int n;
    public static int[] next = new int[MAXN];
    public static char[] s = new char[MAXN];

    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StreamTokenizer st = new StreamTokenizer(bf);
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        st.nextToken();
        n = (int) st.nval;
        s = bf.readLine().toCharArray();
        System.out.println(compute());
        out.flush();
        out.close();
        bf.close();
    }

    private static int compute() {
        nextArray();
        return n - next[n];
    }

    private static void nextArray() {
        next[0] = -1;
        next[1] = 0;
        int i = 2, cn = 0;
        while (i <= n) {
            if (s[i - 1] == s[cn]) {
                next[i++] = ++cn;
            } else if (cn > 0) {
                cn = next[cn];
            } else {
                next[i++] = 0;
            }
        }
    }
}
