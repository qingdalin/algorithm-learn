package algorithm.class62bfs;

import java.util.*;

/**
 * @author 汪大鹏
 * @version 1.0
 * @date 2024-04-10 19:49
 * https://leetcode.cn/problems/stickers-to-spell-word/
 * 宽度优先遍历和动态规划都可以
 */
public class MinStickers {
    public static int MAXN = 401;
    public static String[] queue = new String[MAXN];
    public static int l, r;
    public static List<List<String>> graph = new ArrayList<>();
    static {
        for (int i = 0; i < 26; i++) {
            graph.add(new ArrayList<>());
        }
    }

    public static Set<String> visited = new HashSet<>();
    public int minStickers(String[] stickers, String target) {
        for (int i = 0; i < 26; i++) {
            graph.get(i).clear();
        }
        visited.clear();
        target = sort(target);
        for (String str : stickers) {
            str = sort(str);
            for (int i = 0; i < str.length(); i++) {
                if (i == 0 || str.charAt(i) != str.charAt(i - 1)) {
                    graph.get(str.charAt(i) - 'a').add(str);
                }
            }
        }
        l = r = 0;
        queue[r++] = target;
        visited.add(target);
        int level = 1;
        while (l < r) {
            int size = r - l;
            for (int i = 0; i < size; i++) {
                String cur = queue[l++];
                for (String s : graph.get(cur.charAt(0) - 'a')) {
                    String next = next(cur, s);
                    if ("".equals(next)) {
                        return level;
                    } else if (!visited.contains(next)) {
                        visited.add(next);
                        queue[r++] = next;
                    }
                }
            }
            level++;
        }
        return -1;
    }

    private String next(String t, String s) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0, j = 0; i < t.length();) {
            if (j == s.length()) {
                sb.append(t.charAt(i++));
            } else {
                if (t.charAt(i) < s.charAt(j)) {
                    sb.append(t.charAt(i++));
                } else if (t.charAt(i) > s.charAt(j)) {
                    j++;
                } else {
                    i++;
                    j++;
                }
            }
        }
        return sb.toString();
    }

    private String sort(String target) {
        char[] arr = target.toCharArray();
        Arrays.sort(arr);
        return String.valueOf(arr);
    }
}
