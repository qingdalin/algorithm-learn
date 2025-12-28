package algorithm.class140;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2024/12/15 11:43
 * // 青蛙的约会
 * // 有一个周长为l的环，从环的0位置开始，规定只能沿着顺时针方向不停转圈
 * // 青蛙A在环的x1位置，每秒跳m个单位，青蛙B在x2位置，每秒跳n个单位
 * // 只有在某时刻，青蛙A和青蛙B来到环的同一个位置，才算相遇
 * // 如果两只青蛙相遇不了，打印"Impossible"
 * // 如果可以相遇，打印两只青蛙至少多久才能相遇
 * // 1 <= l <= 3 * 10^9
 * // 1 <= x1、x2、m、n <= 2 * 10^9
 * // x1 != x2
 * // 测试链接 : https://www.luogu.com.cn/problem/P1516
 * // 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例
 */
public class Code02_FrogsMeeting2 {
    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StreamTokenizer in = new StreamTokenizer(bf);
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        in.nextToken(); x1 = (long) in.nval;
        in.nextToken(); x2 = (long) in.nval;
        in.nextToken(); m = (long) in.nval;
        in.nextToken(); n = (long) in.nval;
        in.nextToken(); l = (long) in.nval;
        // A青蛙位置和速度x1 m
        // B青蛙位置和速度x2 n
        // ax + by = c
        // (m - n) * x + k * l = x1 - x2
        if (m == n) {
            // 速度相等一直遇不到
            out.println("Impossible");
        } else {

            if (x1 < x2) {
                if (m < n) {
                    // a在b之后，且速度小于b，等同于，b跨一圈追a
                    long s = l - (x2 - x1);
                    long v = n - m;
                    out.println(f(s, v));
                } else {
                    // a在b之后，且速度大于b，a追b
                    long s = x2 - x1;
                    long v = m - n;
                    out.println(f(s, v));
                }
            } else {
                if (m < n) {
                    // b在a之后， 且a的速度小于b，b追a
                    long s = x1 - x2;
                    long v = n - m;
                    out.println(f(s, v));
                } else {
                    // b在a之后，且a的速度大于b，a跨一圈追b
                    long s = l - (x1 - x2);
                    long v = m - n;
                    out.println(f(s, v));
                }
            }
        }

        out.flush();
        out.close();
        bf.close();
    }

    public static long x, y, d, px, py, xd, x1, x2, m, n, l, c, a;

    // (s + nl) % v == 0;
    // (s + nl) == mv;
    // s == mv + nl;
    // av + bl = s;
    // vx + ly = s;
    public static long f(long s, long v) {
        int i = 1;
        while (s % v != 0) {
            s = s + i * l;
            i++;
        }
        return s / v;
    }
    public static long gcd(long a, long b) {
        return b == 0 ? a : gcd(b, a % b);
    }

    public static void exgcd(long a, long b) {
        if (b == 0) {
            x = 1;
            y = 0;
            d = a;
        } else {
            exgcd(b, a % b);
            px = x;
            py = y;
            x = py;
            y = px - py * (a / b);
        }
    }
}
