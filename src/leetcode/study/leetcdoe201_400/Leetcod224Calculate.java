package leetcode.study.leetcdoe201_400;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/7/13 9:32
 * https://leetcode.cn/problems/basic-calculator/
 */
public class Leetcod224Calculate {
    public static Deque<Character> opStack = new ArrayDeque<>();
    public static Deque<Integer> numStack = new ArrayDeque<>();
    public static int calculate(String str) {
        char[] s = str.replaceAll(" ", "").toCharArray();
        int n = s.length;
        opStack.clear();
        numStack.clear();
        numStack.addLast(0);
        for (int i = 0; i < n;) {
            if (s[i] == ')') {
                while (!opStack.isEmpty()) {
                    char op = opStack.pollLast();
                    if (op == '(') {
                        break;
                    }
                    getVal(op);
                }
                i++;
            } else if (s[i] == '(' || s[i] == '+' || s[i] == '-') {
                if (s[i] == '-' && i - 1 >= 0 && s[i - 1] == '(') {
                    numStack.addLast(0);
                    while (!opStack.isEmpty() && opStack.getLast() == '-') {
                        getVal(opStack.pollLast());
                    }
                }
                opStack.addLast(s[i++]);
            } else {
                int num = 0;
                while (i < n && Character.isDigit(s[i])) {
                    num = num * 10 + s[i++] - '0';
                }
                numStack.addLast(num);
                while (!opStack.isEmpty() && opStack.getLast() == '-') {
                    getVal(opStack.pollLast());
                }
            }
        }
        // 1+0-(0+0-2)
        while (!opStack.isEmpty()) {
            getVal(opStack.pollLast());
        }
        return numStack.getLast();
    }

    public static void getVal(char op) {
        if (numStack.size() < 2) {
            return;
        }
        int a = numStack.pollLast();
        int b = numStack.pollLast();
        if (op == '+') {
            numStack.addLast(a + b);
        } else {
            numStack.addLast(b - a);
        }
    }

    public static int calculate1(String str) {
        char[] s = str.replaceAll(" ", "").toCharArray();
        int n = s.length;
        opStack.clear();
        numStack.clear();
        numStack.addLast(0);
        for (int i = 0; i < n;) {
            if (s[i] == ')') {
                while (!opStack.isEmpty()) {
                    char op = opStack.pollLast();
                    if (op == '(') {
                        break;
                    }
                    getVal(op);
                }
                i++;
            } else if (s[i] == '(') {
                opStack.addLast(s[i++]);
            } else {
                if (Character.isDigit(s[i])) {
                    int num = 0;
                    while (i < n && Character.isDigit(s[i])) {
                        num = num * 10 + s[i++] - '0';
                    }
                    numStack.addLast(num);
                    while (!opStack.isEmpty() && opStack.getLast() == '-') {
                        getVal(opStack.pollLast());
                    }
                } else {
                    if (i - 1 >= 0 && (s[i - 1] == '(')) {
                        // 当前是+或者-，前一位没越界并且是左括号添加0
                        // 2+(-2);
                        numStack.addLast(0);
                    }
                    while (!opStack.isEmpty() && opStack.getLast() != '(') {
                        getVal(opStack.pollLast());
                    }
                    opStack.addLast(s[i++]);
                }
            }
        }
        // 1+0-(0+0-2)
        while (!opStack.isEmpty()) {
            getVal(opStack.pollLast());
        }
        return numStack.getLast();
    }

    public static void main(String[] args) {
        String s = "(7)-(0)+(4)";
        System.out.println(calculate(s));
    }
}
