package algorithm.class145;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2024/12/29 19:48
 * // 信封问题(错排问题)
 * // 一共n个人，每个人都写了一封信
 * // 每个人必须寄出一封信，每个人必须收到一封信，并且不能自己寄给自己
 * // 返回一共有多少种寄信的方法
 * // 1 <= n <= 20
 * // 测试链接 : https://www.luogu.com.cn/problem/P1595
 * // 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例
 */
public class Code01_Derangement {
    public static int n;
    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StreamTokenizer in = new StreamTokenizer(bf);
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        in.nextToken(); n = (int) in.nval;
        out.println(ways1(n));
        out.flush();
        out.close();
        bf.close();
    }
    //
    // j=4, n=6
    // i=4 (-1)^8*(2,2)=1
    // i=5 (-1)^9*(2,1)=-2
    // i=6 (-1)^10*(2,0)=1
    // 相加等于0
    // 二项式反演的方法
    private static long ways2(int n) {
        long facn = 1; // n!
        for (int i = 2; i <= n; i++) {
            facn = facn * i;
        }
        // (-1)的i次方 * (n!/i!)
        long ans = facn; // i = 0时的项
        long faci = 1;
        for (int i = 1; i <= n; i++) {
            faci = faci * i;
            if ((i & 1) == 0) {
                // 偶数
                ans += facn / faci;
            } else {
                // 奇数
                ans -= facn / faci;
            }
        }
        return ans;
    }
    // f(1)=0
    // f(2)=1
    // f(3)=2
    // f(4)=9
    // f(5)=44
    // 0个人不在自己位置 1
    // 1个人不在自己位置 0
    // 2个人不在自己位置 10*1=10
    // 3个人不在自己位置 10*2=20
    // 4个人不在自己位置 5*9=45
    // 5个人不在自己位置 1*44=44
    // 1+0+10+20+45+44=120即5个人全排列==5!

    private static long ways1(int n) {
        long[] dp = new long[n + 1];
        for (int i = 1; i <= n; i++) {
            if (i == 1) {
                dp[i] = 0;
            } else if (i == 2) {
                dp[i] = 1;
             } else {
                dp[i] = (i - 1) * (dp[i - 2] + dp[i - 1]);
            }
        }
        return dp[n];
    }
}
