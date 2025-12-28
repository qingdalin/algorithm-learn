package algorithm.class96game02;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2024/8/2 20:39
 * https://www.luogu.com.cn/problem/P2148
 */
public class EDGame2 {
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
            for (int j = 1, a, b; j <= n; j += 2) {
                st.nextToken();
                a = (int) st.nval;
                st.nextToken();
                b = (int) st.nval;
                eor ^= lowZero((a - 1) | (b - 1));
            }
            // 所有数字异步不等于0，先手赢
            // 等于0后手赢
            if (eor != 0) {
                System.out.println("YES");
            } else {
                System.out.println("NO");
            }
        }
        out.flush();
        out.close();
        bf.close();
    }

    public static int lowZero(int status) {
        int cnt = 0;
        while (status > 0) {
            if ((status & 1) == 0) {
                break;
            }
            status >>= 1;
            cnt++;
        }
        return cnt;
    }
}
