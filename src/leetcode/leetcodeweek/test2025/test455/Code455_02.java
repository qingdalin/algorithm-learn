package leetcode.leetcodeweek.test2025.test455;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/6/22 10:01
 * https://leetcode.cn/contest/weekly-contest-455/problems/inverse-coin-change/
 */
public class Code455_02 {
    public List<Integer> findCoins(int[] arr) {
        // 1, 2, 3, 4, 15
        // 1  2  3  4  5
        // 1种方法凑出1金币
        // 2种方法凑出2金币
        // 3种方法凑出3金币
        // 4种方法凑出4金币

        // 1,
        // 1,1，2
        // 1,1,1    2,1,    3
        // 1,1,1,1  2,1,1,  3,1, 4
        int n = arr.length;
        List<Integer> ans = new ArrayList<>();
        for (int i = 1; i <= n; i++) {
            if (arr[i - 1] != 0) {

            }
        }
        return ans;
    }
}
