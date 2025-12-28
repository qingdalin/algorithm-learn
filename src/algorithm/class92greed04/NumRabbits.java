package algorithm.class92greed04;

import java.util.Arrays;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2024/7/27 11:10
 * 森林中有未知数量的兔子。提问其中若干只兔子 "还有多少只兔子与你（指被提问的兔子）颜色相同?" ，
 * 将答案收集到一个整数数组 answers 中，其中 answers[i] 是第 i 只兔子的回答。
 *
 * 给你数组 answers ，返回森林中兔子的最少数量。
 * https://leetcode.cn/problems/rabbits-in-forest/description/
 */
public class NumRabbits {
    public int numRabbits(int[] answers) {
        Arrays.sort(answers);
        int ans = 0;
        int n = answers.length;
        for (int i = 0, j = 1; i < n; j++) {
            int x = answers[i];
            while (j < n && x == answers[j]) {
                j++;
            }
            // a / b 向上取整 -> (a + b - 1) / b
            // i...j-1 都是同一种答案，当前组有j-i个回答
            ans += (j - i + x) / (x + 1) * (x + 1);
            i = j;
        }
        return ans;
    }
}
