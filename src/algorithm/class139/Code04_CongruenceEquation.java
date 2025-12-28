package algorithm.class139;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2024/12/14 13:23
 * // 同余方程
 * // 求关于x的同余方程 ax ≡ 1(mod b) 的最小正整数解
 * // 题目保证一定有解，也就是a和b互质
 * // 2 <= a、b <= 2 * 10^9
 * // 测试链接 : https://www.luogu.com.cn/problem/P1082
 * // 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例
 */
public class Code04_CongruenceEquation {
    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StreamTokenizer in = new StreamTokenizer(bf);
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        in.nextToken(); int a = (int) in.nval;
        in.nextToken(); int b = (int) in.nval;
        exgcd(a, b);
        out.println((x % b + b) % b);
        out.flush();
        out.close();
        bf.close();
    }
    public static long x, y, d, px, py;
    private static void exgcd(long a, long b) {
        if (b == 0) {
            d = a;
            x = 1;
            y = 0;
        } else {
            exgcd(b, a % b);
            px = x;
            py = y;
            x = py;
            y = px - py * (a / b);
        }
    }
}
