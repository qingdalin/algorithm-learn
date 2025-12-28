package leetcode.leetcodeweek.test2025.test457;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/7/6 9:50
 * https://leetcode.cn/contest/weekly-contest-457/problems/minimum-moves-to-reach-target-in-grid/
 */
public class Code457_04 {
    public int minMoves(int sx, int sy, int x, int y) {
        int ans = 0;
        for (; x != sx || y != sy; ans++) {
            if (x < sx || y < sy) {
                return -1;
            }
            if (x == y) {
                if (sy > 0) {
                    x = 0;
                } else {
                    y = 0;
                }
                continue;
            }
            if (x < y) {
                int tmp = x;
                x = y;
                y = tmp;
                tmp = sx;
                sx = sy;
                sy = tmp;
            }
            if (x > 2 * y) {
                if (x % 2 > 0) {
                    return -1;
                }
                x /= 2;
            } else {
                x -= y;
            }
        }
        return ans;
    }
}
