package algorithm.class105strhash;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;
import java.util.Arrays;
import java.util.HashSet;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2024/8/17 15:06
 * https://www.luogu.com.cn/problem/P3370
 */
public class StringHash {
    public static int MAXN = 100001;
    public static int n;
    public static int base = 499;
    public static int[] cnt = new int[MAXN];
    public static long[] nums = new long[MAXN];
    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StreamTokenizer st = new StreamTokenizer(bf);
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        n = Integer.parseInt(bf.readLine());
        HashSet<String> set = new HashSet<>();
        for (int i = 0; i < n; i++) {
            nums[i] = compute(bf.readLine().toCharArray());
        }
        System.out.println(cnt());
        out.flush();
        out.close();
        bf.close();
    }

    private static int cnt() {
        Arrays.sort(nums, 0, n);
        int cnt = 1;
        for (int i = 1; i < n; i++) {
            if (nums[i] != nums[i - 1]) {
                cnt++;
            }
        }
        return cnt;
    }

    private static long compute(char[] s) {
        long ans = v(s[0]);
        for (int i = 0; i < s.length; i++) {
            ans = ans * base + v(s[i]);
        }
        return ans;
    }

    private static long v(char c) {
        // 0 -> 1
        // 1 -> 2
        // 2 -> 3
        // 9 -> 10
        // A -> 11
        // B -> 12
        // Z -> 36
        // a -> 37
        // ......
        if (c >= '0' && c <= '9') {
            return c -  '0' + 1;
        } else if (c >= 'A' && c <= 'Z') {
            return c - 'A' + 11;
        } else {
            return c - 'a' + 37;
        }
    }
}
