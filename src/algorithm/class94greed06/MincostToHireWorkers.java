package algorithm.class94greed06;

import java.util.Arrays;
import java.util.PriorityQueue;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2024/7/28 10:59
 * 有 n 名工人。 给定两个数组 quality 和 wage ，其中，quality[i] 表示第 i 名工人的工作质量，其最低期望工资为 wage[i] 。
 *
 * 现在我们想雇佣 k 名工人组成一个 工资组。在雇佣 一组 k 名工人时，我们必须按照下述规则向他们支付工资：
 *
 * 对工资组中的每名工人，应当按其工作质量与同组其他工人的工作质量的比例来支付工资。
 * 工资组中的每名工人至少应当得到他们的最低期望工资。
 * 给定整数 k ，返回 组成满足上述条件的付费群体所需的最小金额 。与实际答案误差相差在 10-5 以内的答案将被接受。
 * https://leetcode.cn/problems/minimum-cost-to-hire-k-workers/description/
 */
public class MincostToHireWorkers {
    public double mincostToHireWorkers(int[] quality, int[] wage, int k) {
        int n = quality.length;
        Employ[] employs = new Employ[n];
        for (int i = 0; i < n; i++) {
            employs[i] = new Employ((double) wage[i] / quality[i], quality[i]);
        }
        // 最低支付等于 薪水 / 质量的最低值 * k个员工的质量和
        Arrays.sort(employs, (a, b) -> a.rate <= b.rate ? -1 : 1);
        // 最小k个人的质量，大根堆
        PriorityQueue<Integer> heap = new PriorityQueue<>((a, b) -> b - a);
        double ans = Double.MAX_VALUE;
        int qualitySum = 0;
        for (int i = 0; i < n; i++) {
            int curQuality = employs[i].quality;
            if (heap.size() < k) {
                // 堆没满k，直接加
                qualitySum += curQuality;
                heap.add(curQuality);
                if (heap.size() == k) {
                    ans = Math.min(ans, employs[i].rate * qualitySum);
                }
            } else {
                if (heap.peek() > curQuality) {
                    qualitySum += curQuality - heap.poll();
                    heap.add(curQuality);
                    ans = Math.min(ans, employs[i].rate * qualitySum);
                }
            }
        }
        return ans;
    }

    class Employ {
        double rate; // 薪水 / 质量
        int quality;

        public Employ(double rate, int quality) {
            this.rate = rate;
            this.quality = quality;
        }
    }
}
