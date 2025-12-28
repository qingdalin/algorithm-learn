package algorithm.class85dp20;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2024/7/14 14:40
 * https://www.luogu.com.cn/problem/P2602
 */
public class CountDigit {
    public static long a, b;
    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StreamTokenizer st = new StreamTokenizer(bf);
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        while (st.nextToken() != StreamTokenizer.TT_EOF) {
            a = (long) st.nval;
            st.nextToken();
            b = (long) st.nval;
            // 扩充重要
            for (int i = 0; i < 9; i++) {
                System.out.print(compute(i) + " ");
            }
            System.out.println(compute(9));
        }
        out.flush();
        out.close();
        bf.close();
    }

    private static long compute(int d) {
        return countDigit(b, d) - countDigit(a - 1, d);
    }

    private static long countDigit(long n, int d) {
        long ans = 0;
        for (long right = 1, left, tmp = n; tmp != 0;  right *= 10, tmp /= 10) {
            /*
                30583
                情况1 d != 0
                1.  1 ~ 30583 d == 5
                    cur < d
                    个位cur=3 0-3058 5
                    个位没有额外加的
                2.  cur > d
                    十位cur=8 0~304 5 0~9
                    十位额外加 0~9
                3. cur == d
                    百位cur=5 0~29 5 0~99
                    百位额外加 30 5 0~83
                情况2 d == 0
                1. 1~30583 d == 0
                   cur > d
                   个位cur=3 1~3057
                   个位额外加0
                   十位cur=8 1~304 0 0~9
                   十位额外加 305 0 0~9
                   百位cur=5 1~29 0 0~99
                   百位额外加 30 0 0~99
                   千位cur=0 1~2 0 0~999
                   千位额外加 3 0 0~583
             */
            left = tmp / 10;
            long cur = tmp % 10;
            if (d == 0) {
                left--;
            }
            ans += (right * left);
            if (cur > d) {
                ans += right;
            } else if (cur == d) {
                ans += (n % right + 1);
            }
        }
        return ans;
    }
}
