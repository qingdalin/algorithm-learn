package algorithm.class90greedy02;

import java.util.PriorityQueue;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2024/7/21 20:30
 * 给你 n 个项目。对于每个项目 i ，它都有一个纯利润 profits[i] ，和启动该项目需要的最小资本 capital[i] 。
 *
 * 最初，你的资本为 w 。当你完成一个项目时，你将获得纯利润，且利润将被添加到你的总资本中。
 *
 * 总而言之，从给定项目中选择 最多 k 个不同项目的列表，以 最大化最终资本 ，并输出最终可获得的最多资本。
 *
 * 答案保证在 32 位有符号整数范围内。
 * https://leetcode.cn/problems/ipo/description/
 */
public class FindMaximizedCapital {
    public int findMaximizedCapital(int k, int w, int[] profits, int[] capital) {
        int n = profits.length;
        PriorityQueue<Project> heap1 = new PriorityQueue<>((a, b) -> a.c - b.c); // 启动资金正序
        PriorityQueue<Project> heap2 = new PriorityQueue<>((a, b) -> b.p - a.p); // 利润倒排
        for (int i = 0; i < n; i++) {
            heap1.add(new Project(profits[i], capital[i]));
        }
        while (k > 0) {
            while (!heap1.isEmpty() && heap1.peek().c <= w) {
                heap2.add(heap1.poll());
            }
            if (heap2.isEmpty()) {
                break;
            }
            w += heap2.poll().p;
            k--;
        }
        return w;
    }

    class Project {
        int p;
        int c;

        public Project(int p, int c) {
            this.p = p;
            this.c = c;
        }
    }
}
