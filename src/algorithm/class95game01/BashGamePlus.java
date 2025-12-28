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
 * @date: 2024/7/28 16:17
 * https://www.luogu.com.cn/problem/P4018
 * 巴什博奕
 */
public class BashGamePlus {
    public static int n, m;
    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StreamTokenizer st = new StreamTokenizer(bf);
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        while (st.nextToken() != StreamTokenizer.TT_EOF) {
            n = (int) st.nval;
            for (int i = 0; i < n; i++) {
                st.nextToken();
                m = (int) st.nval;
                // 不是6的整数倍先手赢
                // 是6的整数倍后手赢
                System.out.println(m % 6 != 0 ? "October wins!" : "Roy wins!");
            }
        }
        out.flush();
        out.close();
        bf.close();
    }
}
