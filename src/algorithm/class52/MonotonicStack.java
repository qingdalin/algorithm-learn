package algorithm.class52;

import java.io.*;

/**
 * @author 汪大鹏
 * @version 1.0
 * @date 2024-03-09 11:27
 * https://www.nowcoder.com/practice/2a2c00e7a88a498693568cef63a4b7bb
 */
public class MonotonicStack {
    public static int MAXN = 1000001;
    public static int[] arr = new int[MAXN];
    public static int[] stack = new int[MAXN];
    public static int[][] ans = new int[MAXN][2];
    public static int n, r;
    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StreamTokenizer st = new StreamTokenizer(bf);
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        while (st.nextToken() != StreamTokenizer.TT_EOF) {
            n = (int) st.nval;
            for (int i = 0; i < n; i++) {
                st.nextToken();
                arr[i] = (int) st.nval;
            }
            compute();
            for (int i = 0; i < n; i++) {
                out.println(ans[i][0] + " " + ans[i][1]);
            }
        }
        out.flush();
        out.close();
        bf.close();
    }

    public static void compute() {
        r = 0;
        int cur;
        for (int i = 0; i < n; i++) {
            // 如果栈不为空，并且当前数字小于等于栈顶，一直弹出
            while (r > 0 && arr[stack[r - 1]] >= arr[i]) {
                cur = stack[--r];
                ans[cur][0] = r > 0 ? stack[r - 1] : -1;
                ans[cur][1] = i;
            }
            stack[r++] = i;
        }
        // 处理栈里
        while (r > 0) {
            cur = stack[--r];
            ans[cur][0] = r > 0 ? stack[r - 1] : -1;
            ans[cur][1] = -1;
        }
        // 修正结果
        for (int i = n - 2; i >= 0; i--) {
            if (ans[i][1] != -1 && arr[i] == arr[ans[i][1]]) {
                ans[i][1] = ans[ans[i][1]][1];
            }
        }
    }
}
