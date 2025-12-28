package leetcode.study.leetcdoe201_400;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/8/2 9:30
 * https://leetcode.cn/problems/elimination-game/
 */
public class Leetcode390LastRemaining {
    public static int lastRemaining1(int n) {
        List<Integer> list = new ArrayList<>();
        for (int i = 1; i <= n; i++) {
            list.add(i);
        }
        int i = 0;
        boolean reverse = false;
        while (list.size() != 1) {
            for (int j = i; j < list.size(); j++) {
                list.remove(j);
                if (list.size() == 1) {
                    return list.get(0);
                }
            }
            i = list.size() - 1;
            for(int j = i, k = 0; j >= 0; j -= 2, k++) {
                list.remove(list.size() - 1 - k);
                if (list.size() == 1) {
                    return list.get(0);
                }
            }
            i = 0;
        }
        return list.get(0);
    }

    public static int lastRemaining(int n) {
        int head = 1;
        int step = 1;
        boolean left = true;
        while (n > 1) {
            if (left || n % 2 == 1) {
                head += step;
            }
            left = !left;
            step <<= 1;
            n >>= 1;
        }
        return head;
    }

    public static void main(String[] args) {
        int n = 10;
        for (int i = 2; i <= 19; i += 2) {
            System.out.print(i + " ");
        }
        // 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17
        // 2 4 6 8 10 12 14 16
        // 2 6 10 14
        // 8
        for (int i = 1; i <= 200; i++) {
            System.out.println("n是" + i +"的时候，"+(i / 2) + "结果是：" + lastRemaining(i));
        }
    }
}
