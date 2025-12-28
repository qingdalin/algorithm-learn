package leetcode.study.leetcode401_600;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/8/17 9:15
 * https://leetcode.cn/problems/sort-characters-by-frequency/
 */
public class Leetcode451FrequencySort {
    public static String frequencySort(String str) {
        char[] s = str.toCharArray();
        int n = s.length;
        PriorityQueue<Node> heap = new PriorityQueue<>((a, b) -> b.cnt - a.cnt);
        Map<Character, Integer> map = new HashMap<>();
        for (int i = 0; i < n; i++) {
            map.put(s[i], map.getOrDefault(s[i], 0) + 1);
        }
        for (Map.Entry<Character, Integer> entry : map.entrySet()) {
            heap.add(new Node(entry.getValue(), entry.getKey()));
        }
        StringBuilder ans = new StringBuilder();
        while (!heap.isEmpty()) {
            Node cur = heap.poll();
            int len = cur.cnt;
            char c = cur.c;
            for (int i = 0; i < len; i++) {
                ans.append(c);
            }
        }
        return ans.toString();
    }

    static class Node {
        int cnt;
        char c;

        public Node(int cnt, char c) {
            this.cnt = cnt;
            this.c = c;
        }
    }
}
