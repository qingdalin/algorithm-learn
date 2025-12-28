package leetcode.leetcodeweek.test2025.test468;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/9/21 9:57
 * https://leetcode.cn/contest/weekly-contest-468/problems/bitwise-or-of-even-numbers-in-an-array/
 */
public class Code468_01 {
    public int evenNumberBitwiseORs(int[] nums) {
        // 0010
        // 0100
        // 0110
        int ans = 0;
        for (int num : nums) {
            if (num % 2 == 0) {
                ans |= num;
            }
        }
        return ans;
    }
}
