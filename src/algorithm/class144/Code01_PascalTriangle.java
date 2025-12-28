package algorithm.class144;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2024/12/29 9:59
 * // 杨辉三角
 * // 给定数字n，打印杨辉三角的前n行
 * // 1 <= n <= 20
 * // 测试链接 : https://www.luogu.com.cn/problem/P5732
 * // 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例
 */
public class Code01_PascalTriangle {
    public static int MAXN = 20;
    public static long[][] tri = new long[MAXN][MAXN];
    public static int n;
    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StreamTokenizer in = new StreamTokenizer(bf);
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        in.nextToken(); n = (int) in.nval;
        for (int i = 0; i < n; i++) {
            tri[i][0] = tri[i][i] = 1;
        }
        //   0 1 2 3 4 5
        // 0 1
        // 1 1 1
        // 2 1 2 1
        // 3 1 3 3 1
        // 4 1 4 6 4 1
        // 5 1 5 10 10 5 1
        for (int i = 1; i < n; i++) {
            for (int j = 1; j < i; j++) {
                tri[i][j] = tri[i - 1][j] + tri[i - 1][j - 1];
            }
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j <= i; j++) {
                out.print(tri[i][j] + " ");
            }
            out.println();
        }
        out.flush();
        out.close();
        bf.close();
    }
}
