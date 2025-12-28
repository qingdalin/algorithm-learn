package leetcode.study.leetcode001_200;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/7/12 8:42
 * https://leetcode.cn/problems/repeated-dna-sequences/
 */
public class Leetcode187FindRepeatedDnaSequences {
    public static int MAXN = 100001;
    public static long[] power = new long[MAXN];
    public static long[] hash = new long[MAXN];
    public static int BASE = 499;
    static {
        power[0] = 1;
        for (int i = 1; i < MAXN; i++) {
            power[i] = BASE * power[i - 1];
        }
    }
    public static List<String> findRepeatedDnaSequences(String str) {
        char[] s = str.toCharArray();
        int n = s.length;
        Set<String> set = new HashSet<>();
        List<String> ans = new ArrayList<>();
        Map<String, Integer> cnt = new HashMap<>();
        for (int l = 0, r = 10; r <= n; l++, r++) {
            String cur = str.substring(l, r);
            if (cnt.containsKey(cur)) {
                Integer num = cnt.get(cur);
                cnt.put(cur, num + 1);
                if (num + 1 >= 2) {
                    if (set.add(cur)) {
                        ans.add(cur);
                    }
                }
            } else {
                cnt.put(cur, 1);
            }
        }
        return ans;
    }

    public static long query(int l, int r) {
        long ans = hash[r];
        if (l > 0) {
            ans -= hash[l - 1] * power[r - l + 1];
        }
        return ans;
    }

    public static void build(char[] s, int n) {
        hash[0] = (long) (s[0] - 'A' + 1);
        for (int i = 1; i < n; i++) {
            hash[i] = hash[i - 1] * BASE + s[i] - 'A' + 1;
        }
    }

    public static List<String> findRepeatedDnaSequences1(String str) {
        char[] s = str.toCharArray();
        int n = s.length;
        int[] next = nextArr(s, n);
        Set<String> set = new HashSet<>();
        List<String> ans = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            if (next[i] >= 10) {
                String cur = str.substring(next[i] - 10, next[i]);
                if (set.add(cur)) {
                    ans.add(cur);
                }
            }
        }
        // AAAAACCCCCAAAAA
        // AAAAACCCCCCAAAAA
        return ans;
    }

    private static int[] nextArr(char[] s, int n) {
        if (n == 1) {
            return new int[] {-1};
        }
        int[] next = new int[n + 1];
        next[0] = -1;
        next[1] = 0;
        int i = 2, cn = 0;
        while (i < n) {
            if (s[i - 1] == s[cn]) {
                next[i++] = ++cn;
            } else if (cn > 0) {
                cn = next[cn];
            } else {
                next[i++] = 0;
            }
        }
        return next;
    }

    public static void main(String[] args) {
        String s = "AAAAAAAAAAA";
        System.out.println(findRepeatedDnaSequences(s));
    }
}
