package leetcode.leetcodeweek.test2026.test497;

import java.util.Arrays;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2026/4/12 9:46
 * https://leetcode.cn/contest/weekly-contest-497/problems/angles-of-a-triangle/
 */
public class Code497_02 {
    public static double[] internalAngles(int[] sides) {
        double a = sides[0], b = sides[1], c = sides[2];
        if (a + b <= c || a + c <= b || b + c <= a) {
            return new double[]{};
        }
        double degress1 = getDegress(a, b, c);
        double degress2 = getDegress(b, a, c);
        double degress3 = getDegress(c, b, a);
        double[] ans = {degress1, degress2, degress3};
        Arrays.sort(ans);
        return ans;
    }

    public static double getDegress(double a, double b, double c) {
        double radians = Math.acos((b * b + c * c - a * a) / (2 * b * c));
        double degress = Math.toDegrees(radians);
        String format = String.format("%.8f", degress);
        return Double.parseDouble(format);
    }

    public static void main(String[] args) {
        int[] arr = {3,4,5};
        System.out.println(Arrays.toString(internalAngles(arr)));
    }
}
