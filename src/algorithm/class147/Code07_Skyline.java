package algorithm.class147;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/1/11 15:20
 * // 不含递增三元组的排列方法数
 * // 数字从1到n，可以形成很多排列，要求任意从左往右的三个位置，不能出现依次递增的样子
 * // 返回排列的方法数，答案对 1000000 取模
 * // 1 <= n <= 1000
 * // 测试链接 : https://www.luogu.com.cn/problem/SP7897
 * // 测试链接 : https://www.spoj.com/problems/SKYLINE
 * // 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例
 */
// TODO 没有成功，待提交
public class Code07_Skyline {
    public static int MAXN = 1001;
    public static int MOD = 1000000;
    public static long[] f = new long[MAXN + 1];
    public static int n;
    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StreamTokenizer in = new StreamTokenizer(bf);
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        compute(MAXN);
        while (in.nextToken() != StreamTokenizer.TT_EOF) {
            n = (int) in.nval;
            if (n == 0) {
                break;
            }
            System.out.println(f[n]);
        }
        out.flush();
        out.close();
        bf.close();
    }

    // f(n)=求和f(i)*f(n-1-i)
    private static long compute(int n) {
        f[0] = f[1] = 1;
        for (int i = 2; i <= n; i++) {
            for (int l = 0, r = i - 1; l < i; l++, r--) {
                f[i] = (f[i] + f[l] * f[r] % MOD) % MOD;
            }
        }
        return f[n];
    }


}
