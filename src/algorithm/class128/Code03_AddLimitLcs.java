package algorithm.class128;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2024/11/2 14:10
 * // 增加限制的最长公共子序列问题
 * // 给定两个字符串s1和s2，s1长度为n，s2长度为m
 * // 返回s1和s2的最长公共子序列长度
 * // 注意：
 * // 两个字符串都只由小写字母组成
 * // 1 <= n <= 10^6
 * // 1 <= m <= 10^3
 * // 状态设计优化的经典题，对数器验证
 */
public class Code03_AddLimitLcs {
    public static int lcs1(String str1, String str2) {
        char[] s1 = str1.toCharArray();
        char[] s2 = str2.toCharArray();
        int n = s1.length;
        int m = s2.length;
        int[][] dp = new int[n + 1][m + 1];
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                if (s1[i - 1] == s2[j - 1]) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }
        return dp[n][m];
    }

    public static int MAXN = 1000001;
    public static int MAXM = 1001;
    public static int n, m;
    public static int[][] dp = new int[MAXM][MAXM];
    public static int[][] next = new int[MAXN][26];
    public static int[] right = new int[26];
    public static char[] s1;
    public static char[] s2;
    public static int NA = Integer.MAX_VALUE;

    public static int lcs2(String str1, String str2) {
        s1 = str1.toCharArray();
        s2 = str2.toCharArray();
        n = s1.length;
        m = s2.length;
        build();
        int ans = 0;
        for (int j = m; j >= 1; j--) {
            if (f(m, j) != NA) {
                ans = j;
                break;
            }
        }
        return ans;
    }

    // s2前缀为i的长度，想要与s1的公共子序列长度为j，返回s1至少的长度
    private static int f(int i, int j) {
        if (i < j) {
            return NA;
        }
        if (j == 0) {
            return 0;
        }
        if (dp[i][j] != -1) {
            return dp[i][j];
        }
        // 最后一个字符不包含
        int ans = f(i - 1, j);
        // 包含最后一个字符
        int pre = f(i - 1, j - 1);
        if (pre != NA) {
            ans = Math.min(ans, next[pre][s2[i - 1] - 'a']);
        }
        dp[i][j] = ans;
        return ans;
    }

    private static void build() {
        for (int i = 0; i < 26; i++) {
            right[i] = NA;
        }
        // right[i]: 右侧最近出现当前字符的下标
        for (int i = n; i >= 0; i--) {
            for (int j = 0; j < 26; j++) {
                next[i][j] = right[j];
            }
            if (i > 0) {
                right[s1[i - 1] - 'a'] = i;
            }
        }
        for (int i = 0; i <= m; i++) {
            for (int j = 0; j <= m; j++) {
                dp[i][j] = -1;
            }
        }
    }

    public static void main(String[] args) {
        Runtime runtime = Runtime.getRuntime();
        long totalMemory = runtime.totalMemory() / 1024 / 1024;
        long freeMemory = runtime.freeMemory() / 1024 / 1024;
        long maxMemory = runtime.maxMemory() / 1024 / 1024;
        int n = 100;
        int m = 100;
        int test = 10000;
        System.out.println("功能测试开始");
        for (int t = 0; t < test; t++) {
            int size1 = (int) (Math.random() * n) + 1;
            int size2 = (int) (Math.random() * m) + 1;
            String str1 = randomString(size1);
            String str2 = randomString(size2);
            int ans1 = lcs1(str1, str2);
            int ans2 = lcs2(str1, str2);
            if (ans1 != ans2) {
                System.out.println("出错了");
            }
        }
        System.out.println("功能测试结束");
        System.out.println();
        System.out.println("性能测试开始");
        long start, end;
        n = 1000000;
        m = 1000;
        String str1 = randomString(n);
        String str2 = randomString(m);
        System.out.println("s1长度为" + n + "，s2长度为" + m);
        start = System.currentTimeMillis();
        int ans1 = lcs1(str1, str2);
        end = System.currentTimeMillis();
        System.out.println("普通dp方法答案是" + ans1 + ",耗时" + (end - start));
        start = System.currentTimeMillis();
        int ans2 = lcs2(str1, str2);
        end = System.currentTimeMillis();
        System.out.println("普通dp方法答案是" + ans2 + ",耗时" + (end - start));
        System.out.println("性能测试结束");

    }

    private static String randomString(int n) {
        char[] str = new char[n];
        for (int i = 0; i < n; i++) {
            str[i] = (char) (Math.random() * 26 + 'a');
        }
        return String.valueOf(str);
    }
}
