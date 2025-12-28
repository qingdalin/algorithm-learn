package leetcode.study.leetcode401_600;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/8/30 17:33
 * https://leetcode.cn/problems/random-point-in-non-overlapping-rectangles/
 */
public class RandomRec {
    public int[][] arr;
    public int len;
    public RandomRec(int[][] rects) {
        arr = rects;
        len = rects.length;
    }

    public int[] pick() {
        int idx = -1, cur = 0, curSum = 0;
        for (int i = 0; i < len; i++) {
            int x1 = arr[i][0], y1 = arr[i][1], x2 = arr[i][2], y2 = arr[i][3];
            cur = (x2 - x1 + 1) * (y2 - y1 + 1);
            curSum += cur;
            if ((int) (Math.random() * curSum + 1) < cur) {
                idx = i;
            }
        }
        int x1 = arr[idx][0], y1 = arr[idx][1], x2 = arr[idx][2], y2 = arr[idx][3];

//        int i = (int) (Math.random() * len);
//        int[] cur = arr[i];
//        long x1 = cur[0], y1 = cur[1], x2 = cur[2], y2 = cur[3];
        long x = x2 - x1;
        long a = (long) (Math.random() * (2 * x) - x) + x1;
        long y = y2 - y1;
        long b = (long) (Math.random() * (2 * y) - y) + y1;
        return new int[] {(int) a, (int) b};
    }
}
