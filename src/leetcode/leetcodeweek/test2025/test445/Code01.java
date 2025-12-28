package leetcode.leetcodeweek.test2025.test445;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/4/13 10:02
 * https://leetcode.cn/contest/weekly-contest-445/problems/find-closest-person/
 */
public class Code01 {
    public int findClosest(int x, int y, int z) {
        int d1 = Math.abs(x - z);
        int d2 = Math.abs(y - z);
        if (d1 == d2) {
            return 0;
        } else if (d1 > d2) {
            return 2;
        } else {
            return 1;
        }
    }
}
