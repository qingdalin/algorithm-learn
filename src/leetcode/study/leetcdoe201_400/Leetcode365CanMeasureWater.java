package leetcode.study.leetcdoe201_400;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/7/27 16:22
 * https://leetcode.cn/problems/water-and-jug-problem/
 */
public class Leetcode365CanMeasureWater {
    public boolean canMeasureWater(int x, int y, int target) {
        if (x + y < target) {
            return false;
        }
        if (x == 0 || y == 0) {
            return target == 0 || x + y == target;
        }
        return target % gcd(x, y) == 0;
    }

    private int gcd(int x, int y) {
        return x % y == 0 ? y : gcd(y, x % y);
    }
}
