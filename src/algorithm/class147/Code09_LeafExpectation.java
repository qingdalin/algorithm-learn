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
 * @date: 2025/1/11 16:00
 * // 叶子节点数的期望
 * // 一共有n个节点，认为节点之间无差别，能形成很多不同结构的二叉树
 * // 假设所有不同结构的二叉树，等概率出现一棵，返回叶子节点的期望
 * // 1 <= n <= 10^9
 * // 答案误差小于10的-9次方
 * // 测试链接 : https://www.luogu.com.cn/problem/P3978
 * // 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例
 */
public class Code09_LeafExpectation {
    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StreamTokenizer in = new StreamTokenizer(bf);
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        in.nextToken();
        double n = in.nval;
        // 卡特兰数
        // 第n项的叶子节点 g(n) = n*f(n-1)
        // f(n) = C(2*n,n)/(n+1)
        // ans = g(n)/f(n) = n*f(n-1)/f(n)
        // 带入
        //      (2n-2)!*n               1*2*3*...*(2n-2)                1
        //      -----------------------------------------------------------------
        //      (n-1)!*(n-1)!*n         1*2*3*...n-1*1*2*3*..*n-1       1                n*n*(n+1)      (2n-1)*2
        // --------------------------->---------------------------- ==>---------- ===> ----------- ==> ----------
        //      2n!                     1*2*3*...*2n                    (2n-1)*2n       (2n-1)*2n       n*(n+1)
        //      -----------------------------------------------------------------
        //      n!*n!*(n+1)             1*2*...*n*1*2*3*...n*(n+1)      n*n*(n+1)
        out.printf("%.9f", (n * (n + 1)) / ((2 * n - 1) * 2));
        out.flush();
        out.close();
        bf.close();
    }
}
