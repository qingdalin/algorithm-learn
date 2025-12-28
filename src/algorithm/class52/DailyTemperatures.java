package algorithm.class52;

/**
 * @author 汪大鹏
 * @version 1.0
 * @date 2024-03-09 13:07
 * https://leetcode.cn/problems/daily-temperatures/
 * 每日温度，单调栈，相等时的处理
 */
public class DailyTemperatures {
    public static int r;
    public static int[] stack = new int[100000];
    public int[] dailyTemperatures(int[] temperatures) {
        int[] ans = new int[temperatures.length];
        r = 0;
        for (int i = 0, cur; i < temperatures.length; i++) {
            while (r > 0 && temperatures[stack[r - 1]] < temperatures[i]) {
                cur = stack[--r];
                ans[cur] = i - cur;
            }
            stack[r++] = i;
        }
        return ans;
    }
}
