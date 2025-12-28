package algorithm.class55Queue2;

/**
 * @author 汪大鹏
 * @version 1.0
 * @date 2024-03-16 11:06
 * https://leetcode.cn/problems/max-value-of-equation/
 * 维护双端队列的头和尾
 */
public class FindMaxValueOfEquation {
    public static int[][] deque = new int[100001][2];
    public static int h, t;
    public int findMaxValueOfEquation(int[][] points, int k) {
        int ans = Integer.MIN_VALUE;
        h = t = 0;
        for (int i = 0, x, y; i < points.length; i++) {
            x = points[i][0];
            y = points[i][1];
            // i是此时的点，后面的x坐标不超过k，又能有最大y -x
            while (h < t && k < x - deque[h][0]) {
                h++;
            }
            if (h < t) {
                ans = Math.max(ans, x + y + deque[h][1] - deque[h][0]);
            }
            while (h < t && deque[t - 1][1] - deque[t - 1][0] <= y - x) {
                t--;
            }
            deque[t][0] = x;
            deque[t++][1] = y;
        }
        return ans;
    }
}
