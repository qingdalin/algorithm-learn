package algorithm.class95game01;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2024/7/28 17:10
 * https://www.luogu.com.cn/problem/P4279
 * 反尼姆博弈
 */
public class AntiNim {
    public static int MAXN = 51;
    public static int t, n;
    public static int[] stones = new int[MAXN];
    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StreamTokenizer st = new StreamTokenizer(bf);
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        st.nextToken();
        t = (int) st.nval;
        for (int i = 0; i < t; i++) {
            st.nextToken();
            n = (int) st.nval;

            for (int j = 0; j < n; j++) {
                st.nextToken();
                stones[j] = (int) st.nval;
            }
            System.out.println(compute());
        }
        out.flush();
        out.close();
        bf.close();
    }

    private static String compute() {
        int eor = 0;
        int sum = 0;
        for (int i = 0; i < n; i++) {
            eor ^= stones[i];
            sum += stones[i] == 1 ? 1 : 0;
        }
        if (sum == n) {
            // 如果都是1，根据奇偶性判断
            return sum % 2 == 1 ? "Brother" : "John";
        } else {
            return eor == 0 ? "Brother" : "John";
        }
    }
}
