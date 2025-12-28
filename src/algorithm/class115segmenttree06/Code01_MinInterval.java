package algorithm.class115segmenttree06;

import java.util.Arrays;
import java.util.PriorityQueue;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2024/9/16 7:40
 * https://leetcode.cn/problems/minimum-interval-to-include-each-query/description/
 * 给你一个二维整数数组 intervals ，其中 intervals[i] = [lefti, righti]
 * 表示第 i 个区间开始于 lefti 、结束于 righti（包含两侧取值，闭区间）。
 * 区间的 长度 定义为区间中包含的整数数目，更正式地表达是 righti - lefti + 1 。
 * 再给你一个整数数组 queries 。
 * 第 j 个查询的答案是满足 lefti <= queries[j] <= righti 的 长度最小区间 i 的长度 。
 * 如果不存在这样的区间，那么答案是 -1 。
 *
 * 以数组形式返回对应查询的所有答案。
 */
public class Code01_MinInterval {
    public int[] minInterval(int[][] intervals, int[] queries) {
        // 将区间数组按照开始排序，查询数组按照值排序
        int n = intervals.length;
        int m = queries.length;
        Arrays.sort(intervals, (a, b) -> a[0] - b[0]);
        int[][] ques = new int[m][2];
        // 转为二维数组，对应原数组值和下标
        for (int i = 0; i < m; i++) {
            ques[i][0] = queries[i];
            ques[i][1] = i;
        }
        Arrays.sort(ques, (a, b) -> a[0] - b[0]);
        // 堆，按照区间长度升序，存储长度和区间过期的点
        PriorityQueue<int[]> heap = new PriorityQueue<>((a, b) -> a[0] - b[0]);
        int[] ans = new int[m];
        for (int i = 0, j = 0; i < m; i++) {
            for (; j < n && intervals[j][0] <= ques[i][0]; j++) {
                // 查询的值，大于等于区间的开始，进入堆
                heap.add(new int[] {intervals[j][1] - intervals[j][0] + 1, intervals[j][1]});
            }
            while (!heap.isEmpty() && heap.peek()[1] < ques[i][0]) {
                // 堆顶元素过期值，小于查询的开始值
                heap.poll();
            }
            if (!heap.isEmpty()) {
                // 堆不空，收集答案
                ans[ques[i][1]] = heap.peek()[0];
            } else {
                // 否则-1
                ans[ques[i][1]] = -1;
            }
        }
        return ans;
    }
}
