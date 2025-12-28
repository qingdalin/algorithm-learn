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
public class Code02_FrogsMeeting {
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
        if (x1 < x2) {
            c = x2 - x1;
            a = m - n;
        } else {
            c = x1 - x2;
            a = n - m;
        }
        if (a < 0) {
            a = -a;
            c = l - c;
        }
        exgcd(a, l);
        if (c % d != 0) {
            System.out.println("Impossible");
        } else {
            x *= (c / d);
            xd = l / d;
            long x0;
            if (x < 0) {
                // x增加几次能大于等于1，(a / b)向上取整，写成 (a + b - 1) / b
                x0 = x + (1 - x + xd - 1) / xd * xd;
            } else {
                // x减几次能大于等于1
                x0 = x - xd * ((x - 1) / xd);
            }
            out.println(x0);
        }
        out.flush();
        out.close();
        bf.close();
    }

    public static long x, y, d, px, py, xd, x1, x2, m, n, l, c, a;

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
