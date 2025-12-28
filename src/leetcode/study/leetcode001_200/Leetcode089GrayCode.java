package leetcode.study.leetcode001_200;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/6/7 16:47
 * https://leetcode.cn/problems/gray-code/
 */
public class Leetcode089GrayCode {
    public static List<Integer> grayCode(int n) {
        int m = 1 << n;
        List<Integer> ans = new ArrayList<>();
        for (int i = 0; i < m; i++) {
            // 2 10 -> 01 ^ 10 == 11
            ans.add((i >> 1) ^ i);
        }
        return ans;
    }


    public static void main(String[] args) {
        int n = 2;
        System.out.println(grayCode(n));
    }
}
