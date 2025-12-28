package algorithm.class68dp3;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 汪大鹏
 * @version 1.0
 * @date 2024-05-22 20:11
 * s1删除最少几个字符，称为s2的子串
 */
public class MinNumDeleteBecomSubstring {
    public static int m1(String s1, String s2) {
        List<String> list = new ArrayList<>();
        f(s1.toCharArray(), "", 0, list);
        list.sort((a, b) -> b.length() - a.length());
        for (String s : list) {
            if (s2.indexOf(s) != -1) {
                return s1.length() - s.length();
            }
        }
        return s1.length();
    }

    private static void f(char[] s1, String path, int i, List<String> list) {
        if (i == s1.length) {
            list.add(path);
        } else {
            f(s1, path, i +  1, list);
            f(s1, path + s1[i], i + 1, list);
        }
    }

    public static int m2(String str1, String str2) {
        char[] s1 = str1.toCharArray();
        char[] s2 = str2.toCharArray();
        int n = s1.length;
        int m = s2.length;
        // dp[i][j]：表示s1前i个字符，可以称为s2以j结尾的子串
        int[][] dp = new int[n + 1][m + 1];
        for (int i = 1; i <= n; i++) {
            dp[i][0] = i;
            for (int j = 1; j <= m; j++) {
                if (s1[i - 1] == s2[j - 1]) {
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    dp[i][j] = dp[i - 1][j] + 1;
                }
            }
        }
        int ans = Integer.MAX_VALUE;
        for (int i = 0; i <= m; i++) {
            ans = Math.min(ans, dp[n][i]);
        }
        return ans;
    }

    public static void main(String[] args) {
        int time = 2000;
        int n = 12;
        int v = 3;
        System.out.println("功能测试开始");
        for (int i = 0; i < time; i++) {
            // 生成的字符长度
            int len1 = (int) (Math.random() * n) + 1;
            int len2 = (int) (Math.random() * n) + 1;
            String s1 = randStr(len1, v);
            String s2 = randStr(len2, v);
            int ans1 = m1(s1, s2);
            int ans2 = m2(s1, s2);
            if (ans1 != ans2) {
                System.out.println("错了");
            }
        }
        System.out.println("功能测试开始");
        System.out.println("**********性能测试开始********");
        String s3 = randStr(100, 30);
        String s4 = randStr(10000, 3000);
        long start = System.currentTimeMillis();
        int ans = m2(s3, s4);
        long end = System.currentTimeMillis();
        System.out.println("运行时间：" + (end - start) + "毫秒");
        System.out.println("需要删除的字符个数：" + ans);
        System.out.println("功能测试结束");

    }

    private static String randStr(int n, int v) {
        char[] arr = new char[n];
        for (int i = 0; i < n; i++) {
            arr[i] = (char) ('a' + (int) (Math.random() * v));
        }
        return String.valueOf(arr);
    }
}
