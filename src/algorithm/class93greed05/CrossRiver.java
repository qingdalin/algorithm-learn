package algorithm.class93greed05;

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
 * @date: 2024/7/28 8:19
 * https://www.luogu.com.cn/problem/P1809
 */
public class CrossRiver {
    public static int MAXN = 100001;
    public static int n;
    public static int[] nums = new int[MAXN];
    public static int[] dp = new int[MAXN];
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
            int ans = minCost();
            System.out.println(ans);
        }
        out.flush();
        out.close();
        bf.close();
    }

    private static int minCost() {
        // 升序排
        Arrays.sort(nums, 0, n);
        if (n >= 1) {
            // 只有一个人，时间就是当前人
           dp[0] = nums[0];
        }
        if (n >= 2) {
            // 只有两个人，时间就是大的
            dp[1] = nums[1];
        }
        if (n >= 3) {
            // 只有三个人，时间大的和小的先过河，时间小的把船送回来，和时间中等的一起过
            dp[2] = nums[0] + nums[2] + nums[1];
        }
        for (int i = 3; i < n; i++) {
            dp[i] = Math.min(
                // 第一种方案，时间小的和大的过河，时间小的把船送回来，加上dp[i - 1]
                dp[i - 1] + nums[0] + nums[i],
                // 最小的两个人先过河+任意一人送回来+dp[i]过河+另外小的一人送回来+dp[i-2]
                nums[1] + nums[1] + nums[i] + nums[0] + dp[i - 2]
            );
        }
        return dp[n - 1];
    }
}
