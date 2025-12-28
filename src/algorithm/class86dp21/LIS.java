package algorithm.class86dp21;

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
 * @date: 2024/7/17 20:20
 *  https://www.luogu.com.cn/problem/T386911
 *  https://www.nowcoder.com/practice/30fb9b3cab9742ecae9acda1c75bf927
 */
public class LIS {
    public static int MAXN = 100000;
    public static int[] dp = new int[MAXN];
    public static int[] nums = new int[MAXN];
    public static int[] ends = new int[MAXN];
    public static int[] ans = new int[MAXN];
    public static int n, k;
    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StreamTokenizer st = new StreamTokenizer(bf);
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        while (st.nextToken() != StreamTokenizer.TT_EOF) {
            n = (int) st.nval;
            for (int i = 0; i < n; i++) {
                st.nextToken();
                nums[i] = (int) st.nval;
            }
            int[] ans = compute();
            for (int i = 0; i < k - 1; i++) {
                System.out.print(ans[i] + " ");
            }
            System.out.println(ans[k - 1]);
        }
        out.flush();
        out.close();
        bf.close();
    }

    private static int[] compute() {
        k = dp();
        Arrays.fill(ans, 0, k, Integer.MAX_VALUE);
        for (int i = 0; i < n; i++) {
            if (dp[i] == k) {
                ans[0] = nums[i];
            } else {
                if (ans[k - dp[i] - 1] < nums[i]) {
                    // 只要比前一位大，在更新
                    ans[k - dp[i]] = nums[i];
                }
            }
        }
        return ans;
    }

    private static int dp() {
        int len = 0;
        for (int i = n - 1, find; i >= 0; i--) {
            find = bs(nums[i], len);
            if (find == -1) {
                ends[len++] = nums[i];
                dp[i] = len;
            } else {
                ends[find] = nums[i];
                dp[i] = find + 1;
            }
        }
        return len;
    }
    // ends[有效区]从大到小的
    // 二分的方式找<=num的最左位置
    private static int bs(int num, int len) {
        int l = 0, r = len - 1, ans = -1;
        int m = 0;
        while (l <= r) {
            m = (l + r) / 2;
            if (num >= ends[m]) {
                ans = m;
                r = m - 1;
            } else {
                l = m + 1;
            }
        }
        return ans;
    }
}
