package algorithm.class95game01;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;
import java.math.BigDecimal;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2024/7/28 18:19
 * https://www.luogu.com.cn/problem/P2252
 * 威佐夫博弈
 */
public class WythoffGame {
    public static int a, b;
    // public static double split = (Math.sqrt(5.0) + 1.0) / 2.0;
    public static BigDecimal split = new BigDecimal("1.61803398874989484");
    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StreamTokenizer st = new StreamTokenizer(bf);
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        while (st.nextToken() != StreamTokenizer.TT_EOF) {
            a = (int) st.nval;
            st.nextToken();
            b = (int) st.nval;
            System.out.println(compute());
        }
        out.flush();
        out.close();
        bf.close();
    }

    private static int compute() {
        // 小 == (大 - 小) * 黄金分割，后手赢
        // 小 != (大 - 小) * 黄金分割，先手赢
//        int min = Math.min(a, b);
//        int max = Math.max(a, b);
//        int cha = max - min;
//        double v = split * cha;
        int min = Math.min(a, b);
        int max = Math.max(a, b);
        BigDecimal cha = new BigDecimal(max - min);
        int v = split.multiply(cha).intValue();

        if (min != v) {
            return 1;
        } else {
            return 0;
        }
    }
}
