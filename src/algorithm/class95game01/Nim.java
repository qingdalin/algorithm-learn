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
 * @date: 2024/7/28 16:51
 * https://www.luogu.com.cn/problem/P2197
 * 尼姆博弈
 */
public class Nim {
    public static int t, n;
    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StreamTokenizer st = new StreamTokenizer(bf);
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        st.nextToken();
        t = (int) st.nval;
        for (int i = 0; i < t; i++) {
            st.nextToken();
            n = (int) st.nval;
            int eor = 0;
            for (int j = 0; j < n; j++) {
                st.nextToken();
                eor ^= (int) st.nval;
            }
            // 所有数字异步不等于0，先手赢
            // 等于0后手赢
            if (eor != 0) {
                System.out.println("Yes");
            } else {
                System.out.println("No");
            }
        }
        out.flush();
        out.close();
        bf.close();
    }
}
