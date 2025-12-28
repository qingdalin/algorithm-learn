package leetcode.study.leetcode401_600;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/8/29 20:56
 * https://leetcode.cn/problems/generate-random-point-in-a-circle/
 */
public class RandomRadius {
    // Π * R^2 = S
    // (x1-x2)^2 * (y1-y2)^2 == R^2 == S/Π
    // (x1-x2)*(y1-y2)=R
    public double r, x, y;
    public RandomRadius(double radius, double x_center, double y_center) {
        r = radius;
        x = x_center;
        y = y_center;
    }

    public double[] randPoint() {
        while (true) {
            double x1 = Math.random() * (2 * r) - r;
            double y1 = Math.random() * (2 * r) - r;
            if (x1 * x1 + y1 * y1 <= r * r) {
                return new double[] {x1 + x, y1 + y};
            }
        }
    }
}
