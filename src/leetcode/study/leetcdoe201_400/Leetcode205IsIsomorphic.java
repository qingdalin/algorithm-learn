package leetcode.study.leetcdoe201_400;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/7/12 10:56
 * https://leetcode.cn/problems/isomorphic-strings/description/
 */
public class Leetcode205IsIsomorphic {
    public static boolean isIsomorphic(String str1, String str2) {
        char[] s1 = str1.toCharArray();
        char[] s2 = str2.toCharArray();
        int n = s1.length;
        int m = s2.length;
        if (n < m) {
            int tmp = n;
            n = m;
            m = tmp;
            char[] s = s1;
            s1 = s2;
            s2 = s;
        }
        Map<Character, Character> map1 = new HashMap<>();
        Map<Character, Character> map2 = new HashMap<>();
        for (int i = 0; i < m; i++) {
            if (map1.containsKey(s1[i])) {
                char tar = map1.get(s1[i]);
                if (tar != s2[i]) {
                    return false;
                }
            } else {
                map1.put(s1[i], s2[i]);
            }
            if (map2.containsKey(s2[i])) {
                char tar = map2.get(s2[i]);
                if (tar != s1[i]) {
                    return false;
                }
            } else {
                map2.put(s2[i], s1[i]);
            }
        }
        return true;
    }

    public static void main(String[] args) {
        String s1 = "badc";
        String s2 = "baba";
        System.out.println(isIsomorphic(s1, s2));
    }
}
