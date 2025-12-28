package leetcode.study.leetcode401_600;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/8/10 16:26
 * https://leetcode.cn/problems/reordered-power-of-2/?envType=daily-question&envId=2025-08-10
 */
public class ReorderedPowerOf2 {
    public static boolean[] vis = new boolean[11];
    public static boolean reorderedPowerOf2(int n) {
        Arrays.fill(vis, false);
        char[] s = String.valueOf(n).toCharArray();
        Arrays.sort(s);
        return f(0, 0, s);
    }

    private static boolean f(int i, int num, char[] s) {
        if (i == s.length) {
            return Integer.bitCount(num) == 1;
        } else {
            for (int j = 0; j < s.length; j++) {
                if ((i == 0 && s[i] == '0') || vis[j] || (i > 0 && !vis[i - 1] && s[i] == s[i - 1])) {
                    continue;
                }
                vis[j] = true;
                if (f(j + 1, num * 10 + s[j] - '0', s)) {
                    return true;
                }
                vis[j] = false;
            }
            return false;
        }
    }


    public static Set<String> set = new HashSet<>();
    public static int MAXN = 1000000000;
    static {
        for (int i = 1; i <= MAXN; i <<= 1) {
            String str = getString(i);
            set.add(str);
        }
    }

    private static String getString(int i) {
        char[] s = String.valueOf(i).toCharArray();
        Arrays.sort(s);
        return String.valueOf(s);
    }

    public boolean reorderedPowerOf22(int n) {
        String s = getString(n);
        return set.contains(s);
    }

    public static void main(String[] args) {

    }
}
