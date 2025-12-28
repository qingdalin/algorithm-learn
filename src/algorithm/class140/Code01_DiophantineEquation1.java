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
 * @date: 2024/12/15 10:55
 * // 二元一次不定方程模版
 * // 给定a、b、c，求解方程ax + by = c
 * // 如果方程无解打印-1
 * // 如果方程无正整数解，但是有整数解
 * // 打印这些整数解中，x的最小正数值，y的最小正数值
 * // 如果方程有正整数解，打印正整数解的数量，同时打印所有正整数解中，
 * // x的最小正数值，y的最小正数值，x的最大正数值，y的最大正数值
 * // 1 <= a、b、c <= 10^9
 * // 测试链接 : https://www.luogu.com.cn/problem/P5656
 * // 如下实现是正确的，但是洛谷平台对空间卡的很严，只有使用C++能全部通过
 * // java的版本就是无法完全通过的，空间会超过限制，主要是IO空间占用大
 * // 这是洛谷平台没有照顾各种语言的实现所导致的
 * // 在真正笔试、比赛时，一定是兼顾各种语言的，该实现是一定正确的
 * // C++版本就是Code01_DiophantineEquation2文件
 * // C++版本和java版本逻辑完全一样，但只有C++版本可以通过所有测试用例
 */
public class Code01_DiophantineEquation1 {
    // x xd y yd
    // 2 2  9  3
    // 4 2  6  3
    // 6 2  3  3
    // 8 2  0  3
    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StreamTokenizer in = new StreamTokenizer(bf);
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        in.nextToken(); int test = (int) in.nval;
        for (int t = 1; t <= test; t++) {
            in.nextToken(); a = (long) in.nval;
            in.nextToken(); b = (long) in.nval;
            in.nextToken(); c = (long) in.nval;
            exgcd(a, b);
            if (c % d != 0) {
                System.out.println(-1);
            } else {
                x *= (c / d);
                y *= (c / d);
                xd = b / d;
                yd = a / d;
                if (x < 0) {
                    // x增加几次能大于等于1，(a / b)向上取整，写成 (a + b - 1) / b
                    times = (1 - x + xd - 1) / xd;
                    x += xd * times;
                    y -= yd * times;
                } else {
                    // x减几次能大于等于1
                    times = (x - 1) / xd;
                    x -= xd * times;
                    y += yd * times;
                }
                if (y <= 0) {
                    // 说明没有正整数解
                    System.out.print(x + " ");
                    System.out.println(y + ((1 - y + yd - 1) / yd) * yd);
                } else {
                    // 有正整数解,数量是多少
                    System.out.print(((y - 1) / yd + 1) + " ");
                    // x的最小正整数
                    System.out.print(x + " ");
                    // y的最小正整数
                    System.out.print((y - (y - 1) / yd * yd) + " ");
                    // x的最大正整数
                    System.out.print((x + (y - 1) / yd * xd) + " ");
                    // y的最大正整数
                    System.out.println(y);
                }
            }
        }
        out.flush();
        out.close();
        bf.close();
    }

    public static long x, y, d, px, py, xd, yd, a, b, c, times;

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
