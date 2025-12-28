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
 * @date: 2024/12/15 14:15
 * // 机器人的移动区域
 * // 二维网格中只有x和y的值都为整数的坐标，才叫格点
 * // 某个机器人从格点(0,0)出发，每次机器人都走直线到达(x + dx, y + dy)的格点
 * // 一共移动n次，每次的(dx, dy)都给定，途中路线不会交叉，输入保证机器人最终回到(0,0)
 * // 机器人走的路线所围成的区域一定是多边形，输入保证机器人一定沿着逆时针方向行动
 * // 返回多边形的内部一共几个格点，多边形的边上一共几个格点，多边形的面积
 * // 3 <= n <= 100
 * // -100 <= dx、dy <= 100
 * // 测试链接 : http://poj.org/problem?id=1265
 * // 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例
 */
public class Code04_Area {
    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StreamTokenizer in = new StreamTokenizer(bf);
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        in.nextToken();
        int test = (int) in.nval;
        for (int t = 1; t <= test; t++) {
            in.nextToken(); int m = (int) in.nval;
            int edge = 0;
            int inner = 0;
            double area = 0.0;
            for (int i = 1, x = 0, y = 0, dx, dy; i <= m; i++) {
                in.nextToken(); dx = (int) in.nval;
                in.nextToken(); dy = (int) in.nval;
                // 一条线段上格点数量，等于(x1-x2)和(y1-y2)最大公约数+1
                edge += gcd(Math.abs(dx), Math.abs(dy));
                // 鞋带公式，顺时针：面积 = (x后*y前-x前*y后)累计除以2
                // 鞋带公式，逆时针：面积 = (x前*y后-x后*y前)累计除以2
                area += x * (y + dy) - (x + dx) * y;
                x += dx;
                y += dy;
            }
            area /= 2;
            // pick定理
            // 多边形面积 = 边界上格点数/2 + 内部格点数 - 1
            // 内部格点数 = 多边形面积 - 边界上格点数/2 + 1
            inner = (int) area + 1 - (edge / 2);
            out.println("Scenario #" + t + ":");
            out.print(inner + " ");
            out.print(edge + " ");
            out.printf("%.1f", area);
            out.println();
            out.println();
        }
        out.flush();
        out.close();
        bf.close();
    }

    private static long gcd(long a, long b) {
        return b == 0 ? a : gcd(b, a % b);
    }
}
