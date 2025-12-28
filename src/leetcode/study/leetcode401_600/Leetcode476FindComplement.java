package leetcode.study.leetcode401_600;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/8/29 20:18
 * https://leetcode.cn/problems/number-complement/
 */
public class Leetcode476FindComplement {
    public static int findComplement(int num) {
        int ans = 0;
        int cur = 1;
        while (num > 0) {
            if ((num & 1) == 0) {
                ans += cur;
            }
            cur <<= 1;
            num >>= 1;
        }
        return ans;
    }

    public static void main(String[] args) {
        System.out.println(findComplement(5));
    }
}
