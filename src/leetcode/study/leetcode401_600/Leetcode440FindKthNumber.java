package leetcode.study.leetcode401_600;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/8/16 10:43
 * https://leetcode.cn/problems/k-th-smallest-in-lexicographical-order/
 */
public class Leetcode440FindKthNumber {
    public int findKthNumber(int n, int k) {
        int cur = 1;
        k--;
        while (k > 0) {
            int step = getStep(cur, n);
            if (step <= k) {
                k -= step;
                cur++;
            } else {
                cur *= 10;
                k--;
            }
        }
        return cur;
    }

    private int getStep(int cur, long n) {
        int step = 0;
        long first = cur;
        long last = cur;
        while (first <= n) {
            step += Math.min(last, n) - first + 1;
            first *= 10;
            last = last * 10 + 9;
        }
        return step;
    }

    public int findKthNumber1(int n, int k) {
        List<String> list = new ArrayList<>();
        for (int i = 1; i <= n; i++) {
            list.add(String.valueOf(i));
        }
        list.sort((a, b) -> a.compareTo(b));
        return Integer.parseInt(list.get(k));
    }
}
