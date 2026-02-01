package leetcode.leetcodeweek.test2026.test487;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2026/2/1 10:18
 * https://leetcode.cn/contest/weekly-contest-487/problems/count-monobit-integers/description/
 */
public class Code487_01 {
    public static int countMonobit(int n) {
        // 101
        int i = Integer.numberOfLeadingZeros(n + 1);
        return 32 - i;
    }

    public static int countMonobit1(int n) {
        int ans = 0;
        for (int i = 0; i <= n; i++) {
            char[] s = Integer.toBinaryString(i).toCharArray();
            int zero = 0, one = 0;
            for (char c : s) {
                if (c == '0') {
                    zero++;
                } else {
                    one++;
                }
            }
            if (zero == 0 || one == 0) {
                ans++;
            }
        }
        return ans;
    }



    public static void main(String[] args) {
        int n = 22;
        System.out.println(countMonobit(n));
    }
}
