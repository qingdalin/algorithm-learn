package leetcode.study.leetcode401_600;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/8/30 22:00
 * https://leetcode.cn/problems/keyboard-row/
 */
public class Leetcode500FindWords {
    public static Map<Character, Integer> map = new HashMap<>();
    static {
        map.put('q', 1);
        map.put('w', 1);
        map.put('e', 1);
        map.put('r', 1);
        map.put('t', 1);
        map.put('y', 1);
        map.put('u', 1);
        map.put('i', 1);
        map.put('o', 1);
        map.put('p', 1);
        map.put('a', 2);
        map.put('s', 2);
        map.put('d', 2);
        map.put('f', 2);
        map.put('g', 2);
        map.put('h', 2);
        map.put('j', 2);
        map.put('k', 2);
        map.put('l', 2);
        map.put('z', 3);
        map.put('x', 3);
        map.put('c', 3);
        map.put('v', 3);
        map.put('b', 3);
        map.put('n', 3);
        map.put('m', 3);
    }
    public static String[] findWords(String[] words) {
        List<String> list = new ArrayList<>();
        for (String word : words) {
            int n = word.length();
            int cur = map.get(Character.toLowerCase(word.charAt(0)));
            boolean flag = true;
            for (int i = 1; i < n; i++) {
                if (cur != map.get(Character.toLowerCase(word.charAt(i)))) {
                    flag = false;
                    break;
                }
            }
            if (flag) {
                list.add(word);
            }
        }
        String[] ans = new String[list.size()];
        for (int i = 0; i < list.size(); i++) {
            ans[i] = list.get(i);
        }
        return ans;
    }
}
