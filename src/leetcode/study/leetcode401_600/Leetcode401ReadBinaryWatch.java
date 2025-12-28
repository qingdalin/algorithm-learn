package leetcode.study.leetcode401_600;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/8/3 8:43
 * https://leetcode.cn/problems/binary-watch/
 */
public class Leetcode401ReadBinaryWatch {
    public static List<String> readBinaryWatch(int n) {
        List<String> ans = new ArrayList<>();
        for (int h = 0; h < 12; h++) {
            for (int m = 0; m < 60; m++) {
                if (Integer.bitCount(h) + Integer.bitCount(m) == n) {
                    ans.add(h + ":" + (m < 10 ? "0" : "") + m);
                }
            }
        }
        return ans;
    }



    public static void main(String[] args) {
        int n = 1;
        System.out.println(readBinaryWatch(n));
    }
}
