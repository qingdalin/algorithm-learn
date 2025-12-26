package leetcode.study.everydayleetcode;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/12/26 19:19
 * https://leetcode.cn/problems/minimum-penalty-for-a-shop/?envType=daily-question&envId=2025-12-26
 */
public class Code_20251226BestClosingTime {
    public static int bestClosingTime(String customers) {
        char[] s = customers.toCharArray();
        int n = s.length;
        int ans = 0;
        int min = 0;
        int sum = 0;
        for (int i = 0; i < n; i++) {
            sum += s[i] == 'N' ? 1 : 0;
            if (sum < min) {
                min = sum;
                ans = i;
            }
        }
        return ans;
    }


}
