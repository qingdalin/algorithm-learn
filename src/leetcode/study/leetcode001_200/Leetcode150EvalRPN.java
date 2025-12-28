package leetcode.study.leetcode001_200;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/7/2 19:43
 * https://leetcode.cn/problems/evaluate-reverse-polish-notation/
 */
public class Leetcode150EvalRPN {
    public int evalRPN(String[] tokens) {
        Deque<Integer> deque = new ArrayDeque<>();
        int num;
        for (String s : tokens) {
            if (s.equals("+")) {
                int a = deque.pollLast();
                int b = deque.pollLast();
                deque.addLast(a + b);
            } else if (s.equals("-")) {
                int a = deque.pollLast();
                int b = deque.pollLast();
                deque.addLast(b - a);
            } else if (s.equals("*")) {
                int a = deque.pollLast();
                int b = deque.pollLast();
                deque.addLast(a * b);
            } else if (s.equals("/")) {
                int a = deque.pollLast();
                int b = deque.pollLast();
                deque.addLast(b / a);
            } else {
                num = Integer.parseInt(s);
                deque.addLast(num);
            }
        }
        return deque.getLast();
    }
}
