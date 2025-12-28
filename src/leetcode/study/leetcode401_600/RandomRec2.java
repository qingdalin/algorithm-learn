package leetcode.study.leetcode401_600;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/9/1 20:26
 * https://leetcode.cn/problems/random-flip-matrix/
 */
public class RandomRec2 {
    int n, m, size;
    Map<Integer, Integer> map = new HashMap<>();
    Random random = new Random();
    public RandomRec2(int n, int m) {
        this.n = n;
        this.m = m;
        size = n * m;
    }

    public int[] flip() {
        int x = random.nextInt(size);
        size--;
        int idx = map.getOrDefault(x, x);
        map.put(x, map.getOrDefault(size, size));
        return new int[] {idx / n, idx % n};
    }

    public void reset() {
        size = n * m;
        map.clear();
    }

    public static void main(String[] args) {
        RandomRec2 solution = new RandomRec2(2, 3);
        for (int i = 0; i < 6; i++) {
            System.out.println(Arrays.toString(solution.flip()));
        }
    }
}
