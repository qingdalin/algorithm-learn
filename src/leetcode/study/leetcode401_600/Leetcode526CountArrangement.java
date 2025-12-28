package leetcode.study.leetcode401_600;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/9/21 16:53
 * https://leetcode.cn/problems/beautiful-arrangement/
 * interesting
 */
public class Leetcode526CountArrangement {
    public static int MAXN = 16;
    public static List<Integer>[] match = new List[MAXN];
    public static boolean[] vis = new boolean[MAXN];
    public static int ans;
    public static int countArrangement(int n) {
        ans = 0;
        for (int i = 0; i <= n; i++) {
            match[i] = new ArrayList<>();
            vis[i] = false;
        }
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                if (i % j == 0 || j % i == 0) {
                    match[i].add(j);
                }
            }
        }
        return f(1, n);
    }

    private static int f(int i, int n) {
        if (i > n) {
            return 1;
        }
        int ans = 0;
        for (Integer num : match[i]) {
            if (!vis[num]) {
                vis[num] = true;
                ans += f(i + 1, n);
                vis[num] = false;
            }
        }
        return ans;
    }
}
