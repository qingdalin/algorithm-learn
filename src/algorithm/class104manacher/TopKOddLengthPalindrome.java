package algorithm.class104manacher;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2024/8/17 10:09
 * https://www.luogu.com.cn/problem/P1659
 */
public class TopKOddLengthPalindrome {
    public static int MAXN = 1000001;
    public static int MOD = 19930726;
    public static long n, m, k;
    public static char[] s;
    public static int[] p = new int[MAXN << 1];
    public static int[] cnt = new int[MAXN << 1];
    public static char[] ss = new char[MAXN << 1];
    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        String[] mk = bf.readLine().split(" ");
        m = Long.parseLong(mk[0]);
        k = Long.parseLong(mk[1]);
        s = bf.readLine().toCharArray();
        manacher();
        System.out.println(compute());
        out.flush();
        out.close();
        bf.close();
    }

    private static long compute() {
        for (int i = 1; i < n; i += 2) {
            // 计算回文长度的词频，p[i] - 1半径减一是回文串的长度
            cnt[p[i] - 1]++;
        }
        long ans = 1;
        long sum = 0;
        for (int len = (int) ((m & 1) == 1 ? m : m - 1); len >= 0 && k >= 0 ; len -= 2) {
            sum += cnt[len];
            ans = (ans * power(len, Math.min(k, sum))) % MOD;
            k -= sum;
        }
        return k < 0 ? ans : -1;
    }

    private static void manacher() {
        manacherss();
        for (int i = 0, r = 0, c = 0, len; i < n; i++) {
            len = r > i ? Math.min(r - i, p[2 * c - i]) : 1;
            while (i + len < n && i - len >= 0 && ss[i + len] == ss[i - len]) {
                len++;
            }
            if (i + len > r) {
                r = i + len;
                c = i;
            }
            p[i] = len;
        }
    }

    private static void manacherss() {
        n = s.length * 2L + 1;
        for (int i = 0, j = 0; i < n; i++) {
            ss[i] = (i & 1) == 0 ? '#' : s[j++];
        }
    }

    public static long power(long x, long n) {
        long ans = 1;
        while (n > 0) {
            if ((n & 1) == 1) {
                ans = ans * x % MOD;
            }
            x = x * x % MOD;
            n >>= 1;
        }
        return ans;
    }
}
