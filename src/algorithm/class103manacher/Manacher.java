package algorithm.class103manacher;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2024/8/11 19:39
 * https://www.luogu.com.cn/problem/P3805
 */
public class Manacher {
    public static int MAXN = 11000001;
    public static int n;
    public static int[] p = new int[MAXN << 1];
    public static char[] ss = new char[MAXN << 1];
    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        System.out.println(compute(bf.readLine()));
        out.flush();
        out.close();
        bf.close();
    }

    private static int compute(String str) {
        manacher(str.toCharArray());
        int max = 0;
        for (int i = 0, c = 0, r = 0, len; i < n; i++) {
            // 初始最小的回文半径
            // 四种情况
            // i没有被r包括住
            // i被r包括住 但是前点i-c的扩展长度，对应在大扩展区域以内，直接确定z[i] = z[i - c]
            // i被r包括住 但是前点i-c的扩展长度，对应在大扩展区域以外，直接确定z[i] = r - r
            // i被r包括住 但是前点i-c的扩展长度，对应在大扩展区域边界，从r之外的位置进行扩展
            // 四种情况三行代码判断
            // c是匹配中心，i是当前来到的点，r是扩展到的最右侧，len是回文半径
            len = r > i ? Math.min(p[2 * c - i], r - i) : 1;
            while (len + i < n && i - len >= 0 && ss[len + i] == ss[i - len]) {
                len++;
            }
            if (len + i > r) {
                r = len + i;
                c = i;
            }
            p[i] = len;
            max = Math.max(max, len);
        }
        return max - 1;
    }

    private static void manacher(char[] s) {
        n = s.length * 2 + 1;
        for (int i = 0, j = 0; i < n; i++) {
            ss[i] = (i & 1) == 0 ? '#' : s[j++];
        }
    }
}
