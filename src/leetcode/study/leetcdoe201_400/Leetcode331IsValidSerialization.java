package leetcode.study.leetcdoe201_400;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/7/26 16:45
 * https://leetcode.cn/problems/verify-preorder-serialization-of-a-binary-tree/
 */
public class Leetcode331IsValidSerialization {
    public static boolean isValidSerialization(String str) {
        String[] arr = str.split(",");
        return f(0, arr);
    }
    private static boolean f(int i, String[] arr) {
        int cnt = 1;
        for (int j = 0; j < arr.length; j++) {
            if (cnt == 0) {
                return false;
            }
            if (!arr[j].equals("#")) {
                cnt++;
            } else {
                cnt--;
            }
        }
        return cnt == 0;
    }

    private static boolean f2(int i, String[] arr) {
        Deque<Integer> deque = new ArrayDeque<>();
        deque.push(1);
        for (int j = 0; j < arr.length; j++) {
            if (deque.isEmpty()) {
                return false;
            }
            if (!arr[j].equals("#")) {
                int top = deque.poll() - 1;
                if (top > 0) {
                    deque.push(top);
                }

                deque.push(2);
            } else {
                int top = deque.poll() - 1;
                if (top > 0) {
                    deque.push(top);
                }
            }
        }
        return deque.isEmpty();
    }

    public static void main(String[] args) {
        String s = "9,3,4,#,#,1,#,#,2,#,6,#,#";
        System.out.println(isValidSerialization(s));
    }
}
