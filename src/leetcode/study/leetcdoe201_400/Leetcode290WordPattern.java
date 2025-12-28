package leetcode.study.leetcdoe201_400;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/7/23 20:42
 * https://leetcode.cn/problems/word-pattern/
 */
public class Leetcode290WordPattern {
    public boolean wordPattern(String pattern, String str) {
        Map<Character, String> map1 = new HashMap<>();
        Map<String, Character> map2 = new HashMap<>();
        String[] arr = str.split(" ");
        int n = arr.length;
        char[] s = pattern.toCharArray();
        int m = s.length;
        if (n != m) {
            return false;
        }
        for (int i = 0, j = 0; i < n && j < m; i++, j++) {
            if (map1.containsKey(s[j])) {
                if (!map1.get(s[j]).equals(arr[i])) {
                    return false;
                }
            } else {
                map1.put(s[j], arr[i]);
            }
            if (map2.containsKey(arr[i])) {
                if (map2.get(arr[i]) != s[j]) {
                    return false;
                }
            } else {
                map2.put(arr[i], s[j]);
            }
        }
        return true;
    }
}
