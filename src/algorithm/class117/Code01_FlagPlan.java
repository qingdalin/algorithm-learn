package algorithm.class117;

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
 * @date: 2024/9/18 21:06
 * https://www.luogu.com.cn/problem/P4155
 * 倍增和st表
 */
public class Code01_FlagPlan {
//    public static int MAXN = 200001;
//
//    public static int LIMIT = 18;
//
//    public static int power;
//
//    // 每条线段3个信息 : 线段编号、线段左边界、线段右边界
//    public static int[][] line = new int[MAXN << 1][3];
//
//    // stjump[i][p] : 从i号线段出发，跳的次数是2的p次方，能到达的最右线段的编号
//    public static int[][] stjump = new int[MAXN << 1][LIMIT];
//
//    public static int[] ans = new int[MAXN];
//
//    public static int n, m;
    public static int MAXN = 200001;
    // 0 - 点的编号
    // 1 - 开始起点编号
    // 2 - 结束编号
    public static int[][] line = new int[MAXN << 1][3];
    public static int[] ans = new int[MAXN];
    public static int n, m, power;
    public static int[][] stjump = new int[MAXN << 1][18];

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StreamTokenizer in = new StreamTokenizer(br);
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        in.nextToken();
        n = (int) in.nval;
        in.nextToken();
        m = (int) in.nval;
        for (int i = 1; i <= n; i++) {
            line[i][0] = i;
            in.nextToken();
            line[i][1] = (int) in.nval;
            in.nextToken();
            line[i][2] = (int) in.nval;
        }
        compute();
        out.print(ans[1]);
        for (int i = 2; i <= n; i++) {
            out.print(" " + ans[i]);
        }
        out.println();
        out.flush();
        out.close();
        br.close();
    }

    private static void compute() {
        // 小于等于n的最大的2的多少次方
        power = log2(n);
        build();
        for (int i = 1; i <= n; i++) {
            ans[line[i][0]] = jump(i);
        }
    }

    private static int jump(int i) {
        // 目标点
        int aim = line[i][1] + m;
        int next = 0, cur = i;
        int ans = 1; // 跳一步
        for (int p = power; p >= 0; p--) {
            next = stjump[cur][p]; // 跳2的p次方步到哪
            if (next != 0 && line[next][2] < aim) {
                // 下一步到达的点还在目标之内
                ans += 1 << p;
                cur = next;
            }
        }
        return ans + 1; // 最后加1步，包含自己
    }

    private static void build() {
        for (int i = 1; i <= n; i++) {
            // 先将开始编号大于结束编号的增加m
            if (line[i][1] > line[i][2]) {
                line[i][2] += m;
            }
        }
        // 按照开始编号排序
        Arrays.sort(line, 1, n + 1, (a, b) -> a[1] - b[1]);
        // 扩充一段出来

        for (int i = 1; i <= n; i++) {
            line[i + n][0] = line[i][0];
            line[i + n][1] = line[i][1] + m;
            line[i + n][2] = line[i][2] + m;
        }
        int e = n << 1;
        // 跳2的0次方步最远到哪，也就是跳一步
        for (int i = 1, arrive = 1; i <= e; i++) {
            // 下一步不越界，并且下一步的开始在这一步的结束之内
            while (arrive + 1 <= e && line[arrive + 1][1] <= line[i][2]) {
                arrive++;
            }
            stjump[i][0] = arrive;
        }
        for (int p = 1; p <= power; p++) {
            for (int i = 1; i <= e; i++) {
                stjump[i][p] = stjump[stjump[i][p - 1]][p - 1];
            }
        }
    }

    private static int log2(int n) {
        int ans = 0;
        while ((1 << ans) <= (n >> 1)) {
            ans++;
        }
        return ans;
    }
}
