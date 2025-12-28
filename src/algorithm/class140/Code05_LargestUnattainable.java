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
 * @date: 2024/12/15 14:37
 * // 无法组成的最大值
 * // 一共有a、b两种面值的硬币，a和b一定互质，每种硬币都有无限个
 * // 返回a和b无法组成的钱数中，最大值是多少
 * // 题目的输入保证存在最大的无法组成的钱数
 * // 1 <= a、b <= 10^9
 * // 测试链接 : https://www.luogu.com.cn/problem/P3951
 * // 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例
 */
public class Code05_LargestUnattainable {
    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StreamTokenizer in = new StreamTokenizer(bf);
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        in.nextToken(); long a = (int) in.nval;
        in.nextToken(); long b = (int) in.nval;
        // ax + by = c
        // c最大，且无法组成，那么x和y都要是整数，x=-1或y=-1就不能组成
        // 如果x=-1，-a + by = c
        // y 最大取到a-1,如果y取到a，就变成 b*a - a = c ==> a(b-1) + b*0 = c即不选b，只选a，c就是可以达到的
        // y=a-1 ==> b(a-1) - a = c
        // ab - a - b取不到的最大值
        // 如果y=-1，ax - b = c
        // x最大取到b-1，如果x取到b，就变成 b*a - b = c ==> b(a-1) + a*0 = c即不选a，只选b，c就是可以达到的
        // x=b-1, ==> a(b-1) - b = c
        // ab - a - b取不到的最大值
        out.println(a * b - a - b);
        out.flush();
        out.close();
        bf.close();
    }
}
