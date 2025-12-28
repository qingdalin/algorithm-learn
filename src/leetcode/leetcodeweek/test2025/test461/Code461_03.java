package leetcode.leetcodeweek.test2025.test461;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/8/3 7:58
 * https://leetcode.cn/contest/weekly-contest-461/problems/minimum-time-to-activate-string/
 */
public class Code461_03 {
    public static int minTime(String str, int[] order, int k) {
        int n = str.length();
        if (((long) n * (n + 1) / 2) < k) {
            return -1;
        }
        int[] star = new int[n];
        int l = -1, r = n;
        while (l + 1 < r) {
            int m = l + (r - l) / 2;
            if (check(m, k, order, star)) {
                r = m;
            } else {
                l = m;
            }
        }
        return r;
    }

    private static boolean check(int m, int k, int[] order, int[] star) {
        m++;
        for (int i = 0; i < m; i++) {
            star[order[i]] = m;
        }
        int cnt = 0;
        int last = -1;
        for (int i = 0; i < star.length; i++) {
            if (star[i] == m) {
                last = i;
            }
            cnt += last + 1;
            if (cnt >= k) {
                return true;
            }
        }
        return false;
    }

    public static int minTime1(String str, int[] order, int k) {
        int n = str.length();
        int cnt = 0;
        int l = 0, r = n - 1;
        for (int t = 0; t < n; t++) {
            int m = order[t];
            if (t != 0) {

            }
            cnt += sum(l, m) - 1 + sum(m, r);
            if (cnt >= k) {
                return t;
            }

        }
        return -1;
    }

    public static int sum(int i, int r) {
        return r - i + 1;
    }

    public static void main(String[] args) {
        String s = "cat";
        int[] arr = new int[] {0,2,1};
        int k = 6;
        System.out.println(minTime(s, arr, k));
    }
}
