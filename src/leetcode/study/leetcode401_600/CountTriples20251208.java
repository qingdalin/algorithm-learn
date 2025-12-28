package leetcode.study.leetcode401_600;

import java.util.HashSet;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/12/8 20:30
 * https://leetcode.cn/problems/count-square-sum-triples/?envType=daily-question&envId=2025-12-08
 */
public class CountTriples20251208 {
    public static int countTriples(int n) {
        int ans = 0;
        for (int c = n; c >= 3; c--) {
            int b = c - 1, a = 1;
            while (a < b) {
                int ab2 = a * a + b * b;
                int c2 = c * c;
                if (ab2 < c2) {
                    a++;
                } else if (ab2 > c2) {
                    b--;
                } else {
                    ans++;
                    a++;
                    b--;
                }
            }
        }
        return ans * 2;
    }

    public static HashSet<Integer> set = new HashSet<>();

    static {
        for (int i = 1; i <= 250; i++) {
            set.add(i * i);
        }
    }

    public static int countTriples2(int n) {
        int ans = 0;
        int n2 = n * n;
        for (int a = 1; a <= n; a++) {
            int a2 = a * a;
            for (int b = 1; b <= n; b++) {
                int b2 = b * b;
                if (a2 + b2 > n2) {
                    continue;
                }
                if (set.contains(a2 + b2)) {
                    ans++;
                }
            }
        }
        return ans;
    }

    public static int countTriples1(int n) {
        int ans = 0;
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                for (int k = 1; k <= n; k++) {
                    if (i * i + j * j == k * k) {
                        System.out.println(i + " " + j + " " + k);
                        ans++;
                    }
                }
            }
        }
        return ans;
    }

    public static void main(String[] args) {
        int n = 18;
        System.out.println(countTriples1(n));
    }
}
