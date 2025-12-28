package algorithm.class131;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2024/11/10 16:12
 * // 方伯伯的玉米田
 * // 给定一个长度为n的数组arr
 * // 每次可以选择一个区间[l,r]，区间内的数字都+1，最多执行k次
 * // 返回执行完成后，最长的不下降子序列长度
 * // 1 <= n <= 10^4
 * // 1 <= arr[i] <= 5000
 * // 2 <= k <= 500
 * // 测试链接 : https://www.luogu.com.cn/problem/P3287
 * // 提交以下的code，提交时请把类名改成"Main"，可以通过所有用例
 */
public class Code03_CornField {
    public static int MAXN = 10001;
    public static int MAXK = 501;
    public static int MAXH = 5501;
    public static int[][] tree = new int[MAXH + 1][MAXK + 1];
    public static int[] arr = new int[MAXN];
    public static int n, k;
    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StreamTokenizer in = new StreamTokenizer(bf);
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        in.nextToken(); n = (int) in.nval;
        in.nextToken(); k = (int) in.nval;
        for (int i = 1; i <= n; i++) {
            in.nextToken(); arr[i] = (int) in.nval;
        }
        System.out.println(compute());
        out.flush();
        out.close();
        bf.close();
    }

    private static int compute() {
        // dp[v][j]:表示当前数值v已经获得过j次+1操作了，子序列以v值结尾的情况下，最长不下降子序列的长度是多少
        int v, dp;
        for (int i = 1; i <= n; i++) {
            for (int j = k; j >= 0; j--) {
                // j一定要从k开始
                v = arr[i] + j;
                // 修改次数j对应树状数组下标j+1
                dp = max(v, j + 1) + 1;
                update(v, j + 1, dp);
            }
        }
        return max(MAXH, k + 1);
    }

    private static void update(int x, int y, int v) {
        for (int i = x; i <= MAXH; i += lowbit(i)) {
            for (int j = y; j <= k + 1; j += lowbit(j)) {
                tree[i][j] = Math.max(tree[i][j], v);
            }
        }
    }

    private static int max(int x, int y) {
        int ans = Integer.MIN_VALUE;
        for (int i = x; i > 0; i -= lowbit(i)) {
            for (int j = y; j > 0; j -= lowbit(j)) {
                ans = Math.max(ans, tree[i][j]);
            }
        }
        return ans;
    }

    private static int lowbit(int i) {
        return i & -i;
    }
}
