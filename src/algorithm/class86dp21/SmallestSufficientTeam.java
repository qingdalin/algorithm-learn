package algorithm.class86dp21;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2024/7/15 20:04
 * 作为项目经理，你规划了一份需求的技能清单 req_skills，并打算从备选人员名单 people
 * 中选出些人组成一个「必要团队」（ 编号为 i 的备选人员 people[i] 含有一份该备选人员掌握的技能列表）。
 *
 * 所谓「必要团队」，就是在这个团队中，对于所需求的技能列表 req_skills 中列出的每项技能，
 * 团队中至少有一名成员已经掌握。可以用每个人的编号来表示团队中的成员：
 *
 * 例如，团队 team = [0, 1, 3] 表示掌握技能分别为 people[0]，people[1]，和 people[3] 的备选人员。
 * 请你返回 任一 规模最小的必要团队，团队成员用人员编号表示。你可以按 任意顺序 返回答案，题目数据保证答案存在。
 * https://leetcode.cn/problems/smallest-sufficient-team/description/
 */
public class SmallestSufficientTeam {
    public int[] smallestSufficientTeam(String[] req_skills, List<List<String>> people) {
        int n = req_skills.length; // 技能数
        int m = people.size(); // 人数
        Map<String, Integer> cntMap = new HashMap<>();
        int cnt = 0;
        // 给每个技能编号
        for (String skill : req_skills) {
            cntMap.put(skill, cnt++);
        }
        // 每个人的技能状态
        int[] arr = new int[m];
        for (int i = 0, status; i < m; i++) {
            status = 0;
            for (String s : people.get(i)) {
                if (cntMap.containsKey(s)) {
                    status |= 1 << cntMap.get(s);
                }
                arr[i] = status;
            }
        }
        int[][] dp = new int[m][1 << n];
        for (int i = 0; i < m; i++) {
            Arrays.fill(dp[i], -1);
        }
        int size = f1(arr, m, n, 0, 0, dp);
        int[] ans = new int[size];
        for (int j = 0, i = 0, s = 0; s != ((1 << n) - 1); i++) {
            if (i == m - 1 || dp[i][s] != dp[i + 1][s]) {
                // 1.来到最后一个人还不满足，则最后一个人必须要
                // 2.i号人和i+1号人状态不一样，则必须要
                ans[j++] = i;
                s |= arr[i];
            }
        }
        return ans;
    }

    // arr:每个人技能状态
    // m:人数的多少
    // n:技能的数量
    // i:当前来到i号人
    // s:当前满足的技能状态
    private int f1(int[] arr, int m, int n, int i, int s, int[][] dp) {
        if (s == ((1 << n) - 1)) {
            // 已经满足技能状态，则不需要人
            return 0;
        }
        if (i == m) {
            // 没有满足技能状态，并且没有人了
            return Integer.MAX_VALUE;
        }
        if (dp[i][s] != -1) {
            return dp[i][s];
        }
        // 不要当前i号人
        int p1 = f1(arr, m, n, i + 1, s, dp);
        int p2 = Integer.MAX_VALUE;
        int next = f1(arr, m, n, i + 1, s | arr[i], dp);
        if (next != Integer.MAX_VALUE) {
            // 有效返回，加上当前的一个人和后续的
            p2 = 1 + next;
        }
        int ans = Math.min(p1, p2);
        dp[i][s] = ans;
        return ans;
    }
}
