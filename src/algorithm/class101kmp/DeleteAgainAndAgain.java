package algorithm.class101kmp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2024/8/10 9:46
 * https://www.luogu.com.cn/problem/P4824
 */
public class DeleteAgainAndAgain {
    public static int MAXN = 1000001;
    public static int size;
    public static int[] next = new int[MAXN];
    public static char[] s1, s2;
    public static int[] stack1 = new int[MAXN];
    public static int[] stack2 = new int[MAXN];

    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        s1 = bf.readLine().toCharArray();
        s2 = bf.readLine().toCharArray();
        compute();
        for (int i = 0; i < size; i++) {
            System.out.print(s1[stack1[i]]);
        }
        System.out.println();
        out.flush();
        out.close();
        bf.close();
    }

    private static void compute() {
        int n = s1.length;
        int m = s2.length;
        int x = 0, y = 0;
        nextArray(m);
        while (x < n) {
            if (s1[x] == s2[y]) {
                stack1[size] = x;
                stack2[size] = y;
                size++;
                x++;
                y++;
            } else if (y == 0) {
                stack1[size] = x;
                stack2[size] = -1;
                size++;
                x++;
            } else {
                y = next[y];
            }
            if (y == m) {
                size -= m;
                y = size > 0 ? (stack2[size - 1] + 1) : 0;
            }
        }
    }

    private static void nextArray(int m) {
        next[0] = -1;
        next[1] = 0;
        int i = 2, cn = 0;
        while (i < m) {
            if (s2[i - 1] == s2[cn]) {
                next[i++] = ++cn;
            } else if (cn > 0) {
                cn = next[cn];
            } else {
                next[i++] = 0;
            }
        }
    }
}
