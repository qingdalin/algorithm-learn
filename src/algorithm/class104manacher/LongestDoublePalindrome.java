package algorithm.class104manacher;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2024/8/17 10:48
 * https://www.luogu.com.cn/problem/P4555
 */
public class LongestDoublePalindrome {
    public static int MAXN = 100001;
    public static int n;
    public static int[] p = new int[MAXN << 1];
    public static int[] left = new int[MAXN << 1];
    public static int[] right = new int[MAXN << 1];
    public static char[] ss = new char[MAXN << 1];
    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        System.out.println(compute(bf.readLine()));
        out.flush();
        out.close();
        bf.close();
    }

    private static int compute(String s) {
        manacher(s.toCharArray());
        for (int i = 0, j = 0; i < n; i++) {
            while (i + p[i] > j) {
                left[j] = j - i;
                j += 2;
            }
        }
        for (int i = n - 1, j = n - 1; i >= 0; i--) {
            while (i - p[i] < j) {
                right[j] = i - j;
                j -= 2;
            }
        }
        int ans = 0;
        // 2 4 6 8
        //       8是n - 1
        //       6是n - 3
        for (int i = 2; i <= n - 3; i++) {
            ans = Math.max(ans, left[i] + right[i]);
        }
        return ans;
    }

    private static void manacher(char[] s) {
        manacherss(s);
        for (int i = 0, r = 0, c = 0, len; i < n; i++) {
            len = r > i ? Math.min(r - i, p[2 * c - i]) : 1;
            while (i + len < n && i - len >= 0 && ss[i + len] == ss[i - len]) {
                len++;
            }
            if (i + len > r) {
                r = i + len;
                c = i;
            }
            p[i] = len;
        }
    }

    private static void manacherss(char[] s) {
        n = 2 * s.length + 1;
        for (int i = 0, j = 0; i < n; i++) {
            ss[i] = (i & 1) == 1 ? s[j++] : '#';
        }
    }
}
