package leetcode.study.leetcode401_600;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashSet;
import java.util.Set;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/8/13 20:25
 * https://leetcode.cn/problems/minimum-genetic-mutation/
 */
public class Leetcode433MinMutation {
    public static int minMutation(String start, String end, String[] arr) {
        Set<String> set = new HashSet<>(Arrays.asList(arr));
        if (!set.contains(end)) {
            return -1;
        }
        Set<String> vis = new HashSet<>();
        Deque<String> deque = new ArrayDeque<>();
        deque.addLast(start);
        vis.add(start);
        int level = 0;
        while (!deque.isEmpty()) {
            int size = deque.size();
            level++;
            for (int i = 0; i < size; i++) {
                String cur = deque.pollFirst();
                if (cur.equals(end)) {
                    return level - 1;
                }
                for (String s : set) {
                    if (!vis.contains(s) && diff(s, cur) == 1) {
                        deque.addLast(s);
                        vis.add(s);
                    }
                }
            }
        }
        return -1;
    }

    public static int diff(String s1, String s2) {
        int cnt = 0;
        for (int i = 0; i < s1.length(); i++) {
            if (s1.charAt(i) != s2.charAt(i)) {
                cnt++;
            }
        }
        return cnt;
    }

    public static void main(String[] args) {
        String s1 = "AAAAAAAT";
        String s2 = "CCCCCCCC";
        String[] arr = {"AAAAAAAC","AAAAAAAA","CCCCCCCC"};
        System.out.println(minMutation(s1, s2, arr));
    }
}
