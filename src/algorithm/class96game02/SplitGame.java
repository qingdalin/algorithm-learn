package algorithm.class96game02;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;
import java.util.Arrays;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2024/8/3 8:35
 * https://www.luogu.com.cn/problem/P3185
 */
public class SplitGame {
    public static int MAXN = 21;
    public static int MAXV = 101;
    public static int t, n;
    public static int[] nums = new int[MAXN];
    public static int[] sg = new int[MAXN];
    public static boolean[] appear = new boolean[MAXV];
    public static void main(String[] args) throws IOException {
        build();
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StreamTokenizer st = new StreamTokenizer(bf);
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        st.nextToken();
        t = (int) st.nval;
        for (int i = 0; i < t; i++) {
            st.nextToken();
            n = (int) st.nval;
            for (int j = n - 1; j >= 0; j--) {
                st.nextToken();
                nums[j] = (int) st.nval;
            }
            System.out.println(compute());
        }
        out.flush();
        out.close();
        bf.close();
    }

    public static void build() {
        for (int i = 1; i < MAXN; i++) {
            Arrays.fill(appear, false);
            for (int j = i - 1; j >= 0; j--) {
                for (int k = j; k >= 0 ; k--) {
                    appear[sg[j] ^ sg[k]] = true;
                }
            }
            for (int s = 0; s < MAXV; s++) {
                if (!appear[s]) {
                    sg[i] = s;
                    break;
                }
            }
        }
    }

    private static String compute() {

        int eor = 0;
        for (int i = n - 1; i >= 0; i--) {
            if (nums[i] % 2 == 1) {
                eor ^= sg[i];
            }
        }
        if (eor == 0) {
            // 先手不能必胜，输出
            return "-1 -1 -1\n" + "0";
        }
        int cnt = 0, a = -1, b = -1, c = -1, pos;
        for (int i =  n - 1; i >= 0; i--) {
            if (nums[i] > 0) {
                for (int j = i - 1; j >= 0; j--) {
                    for (int k = j; k >= 0; k--) {
                        pos = eor ^ sg[i] ^ sg[j] ^ sg[k];
                        if (pos == 0) {
                            cnt++;
                            if (a == -1) {
                                a = i;
                                b = j;
                                c = k;
                            }
                        }
                    }
                }
            }
        }
        return (n - 1 - a) + " " + (n - 1 - b) + " " + (n - 1 - c) + "\n" + cnt;
    }
}
