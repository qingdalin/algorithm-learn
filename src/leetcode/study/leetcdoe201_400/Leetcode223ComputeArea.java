package leetcode.study.leetcdoe201_400;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/7/13 9:07
 */
public class Leetcode223ComputeArea {
    public int computeArea(int ax1, int ay1, int ax2, int ay2, int bx1, int by1, int bx2, int by2) {
        int s1 = (ax2 - ax1) * (ay2 - ay1);
        int s2 = (bx2 - bx1) * (by2 - by1);
        int cx1 = 0, cy1 = 0, cx2 = 0, cy2 = 0;
        int w = Math.min(ax2, bx2) - Math.max(ax1, bx1);
        int h = Math.min(ay2, by2) - Math.max(ay1, by1);
        int s3 = Math.max(w, 0) * Math.max(h, 0);
        return s1 + s2 - s3;
    }
}
