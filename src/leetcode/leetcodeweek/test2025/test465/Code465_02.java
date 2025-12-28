package leetcode.leetcodeweek.test2025.test465;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/8/31 10:06
 * https://leetcode.cn/contest/weekly-contest-465/problems/balanced-k-factor-decomposition/
 */
public class Code465_02 {
    public static int MAXN = 100001;
    public static boolean[] vis = new boolean[MAXN];
    static {
        for (int i = 2; i * i < MAXN; i++) {
            if (!vis[i]) {
                for (int j = i * i; j < MAXN; j += i) {
                    vis[j] = true;
                }
            }
        }
    }
    public static int[] minDifference(int n, int k) {
        // 100 =  2 * 2 * 5 * 5
        int[] ans = new int[k];
        if (!vis[n]) {
            // 是质数，只能是 1 * 1 * 1 * n的形式
            ans[0] = n;
            for (int i = 1; i < k; i++) {
                ans[i] = 1;
            }
            return ans;
        }
        List<Integer> list = new ArrayList<>();
        for (int i = (int) Math.sqrt(n) ; i * i > 1; i--) {
            if (n % i == 0) {
                while (n % i == 0) {
                    list.add(i);
                    n /= i;
                }
            }
        }
        if (n > 1) {
            list.add(n);
        }
        if (list.size() != k) {
            int m = k - list.size();
            for (int i = 0; i < m; i++) {
                list.add(1);
            }

        }
        for (int i = 0; i < list.size(); i++) {
            ans[i] = list.get(i);
        }
        return ans;
    }

    public static void main(String[] args) {
        int n = 44, k = 3;
//        int n = 100, k = 2;
        System.out.println(Arrays.toString(minDifference(n, k)));
    }


}
