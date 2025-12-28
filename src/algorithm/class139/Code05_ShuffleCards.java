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
 * @date: 2024/12/14 13:48
 * // 洗牌
 * // 一共有n张牌，n一定是偶数，每张牌的牌面从1到n，洗牌规则如下
 * // 比如n = 6，牌面最初排列为1 2 3 4 5 6
 * // 先分成左堆1 2 3，右堆4 5 6，然后按照右堆第i张在前，左堆第i张在后的方式依次放置
 * // 所以洗一次后，得到 4 1 5 2 6 3
 * // 如果再洗一次，得到 2 4 6 1 3 5
 * // 如果再洗一次，得到 1 2 3 4 5 6
 * // 想知道n张牌洗m次的之后，第l张牌，是什么牌面
 * // 1 <= n <= 10^10，n为偶数
 * // 0 <= m <= 10^10
 * // 测试链接 : https://www.luogu.com.cn/problem/P2054
 * // 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例
 */
public class Code05_ShuffleCards {
    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StreamTokenizer in = new StreamTokenizer(bf);
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        in.nextToken(); long n = (long) in.nval;
        in.nextToken(); long m = (long) in.nval;
        in.nextToken(); long l = (long) in.nval;
        out.println(compute(n, m, l));
        out.flush();
        out.close();
        bf.close();
    }

    private static long compute(long n, long m, long l) {
        long mod = n + 1;
        exgcd(power(2, m, mod), mod);
        long x0 = (x % mod + mod) % mod;
        return multiply(x0, l, mod);
    }

    private static long power(long a, long b, long mod) {
        long ans = 1;
        while (b > 0) {
            if ((b & 1) == 1) {
                ans = multiply(ans, a, mod);
            }
            a = multiply(a, a, mod);
            b >>= 1;
        }
        return ans;
    }

    private static long multiply(long a, long b, long mod) {
        long ans = 0;
        while (b > 0) {
            if ((b & 1) == 1) {
                ans = (ans + a) % mod;
            }
            a = (a + a) % mod;
            b >>= 1;
        }
        return ans;
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
