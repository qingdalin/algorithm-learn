package leetcode.study.leetcode401_600;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/8/30 16:36
 * https://leetcode.cn/problems/construct-the-rectangle/description/
 */
public class Leetcode492ConstructRectangle {
    public int[] constructRectangle(int area) {
        int l = 0;
        int w = (int) Math.sqrt(area);
        while (area % w != 0) {
            w--;
        }
        return new int[] {area / w, w};
    }

    public int[] constructRectangle1(int area) {
        int x = 0, y = 0;
        for (int l = 1, r = area; l <= r;) {
            int s = l * r;
            if (s == area) {
                x = l++;
                y = r--;
            } else if (s < area) {
                l++;
            } else {
                r--;
            }
        }
        return new int[] {y, x};
    }
}
