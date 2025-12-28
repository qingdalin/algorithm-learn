package algorithm.class90greedy02;

import java.util.Arrays;
import java.util.PriorityQueue;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2024/7/21 20:10
 * 给你一个数组 events，其中 events[i] = [startDayi, endDayi] ，表示会议 i 开始于 startDayi ，结束于 endDayi 。
 *
 * 你可以在满足 startDayi <= d <= endDayi 中的任意一天 d 参加会议 i 。在任意一天 d 中只能参加一场会议。
 *
 * 请你返回你可以参加的 最大 会议数目
 * https://leetcode.cn/problems/maximum-number-of-events-that-can-be-attended/description/
 */
public class MaxEvents {
    public int maxEvents(int[][] events) {
        int n = events.length;
        Arrays.sort(events, (a, b) -> a[0] - b[0]);
        int min = events[0][0];
        int max = events[0][1];
        for (int i = 1; i < n; i++) {
            max = Math.max(max, events[i][1]);
        }
        int i = 0 , ans = 0;
        PriorityQueue<Integer> heap = new PriorityQueue<>();
        for (int day = min; day <= max; day++) {
            while (i < n && events[i][0] == day) {
                // 来到1天，第一件事，把开始的天的等于day的全加在堆里
                heap.add(events[i++][1]);
            }
            while (!heap.isEmpty() && heap.peek() < day) {
                // 第二件事，结束时间小于day的全是过期
                heap.poll();
            }
            if (!heap.isEmpty()) {
                // 第三件事，如果还有会议，就开当前堆顶会议
                heap.poll();
                ans++;
            }
        }
        return ans;
    }
}
