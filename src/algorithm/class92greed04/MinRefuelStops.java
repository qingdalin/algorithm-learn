package algorithm.class92greed04;

import java.util.PriorityQueue;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2024/7/27 13:57
 * 汽车从起点出发驶向目的地，该目的地位于出发位置东面 target 英里处。
 *
 * 沿途有加油站，用数组 stations 表示。其中 stations[i] = [positioni, fueli]
 * 表示第 i 个加油站位于出发位置东面 positioni 英里处，并且有 fueli 升汽油。
 *
 * 假设汽车油箱的容量是无限的，其中最初有 startFuel 升燃料。它每行驶 1 英里就会用掉 1 升汽油。
 * 当汽车到达加油站时，它可能停下来加油，将所有汽油从加油站转移到汽车中。
 *
 * 为了到达目的地，汽车所必要的最低加油次数是多少？如果无法到达目的地，则返回 -1 。
 *
 * 注意：如果汽车到达加油站时剩余燃料为 0，它仍然可以在那里加油。如果汽车到达目的地时剩余燃料为 0，仍然认为它已经到达目的地。
 * https://leetcode.cn/problems/minimum-number-of-refueling-stops/description/
 */
public class MinRefuelStops {
    public int minRefuelStops(int target, int startFuel, int[][] stations) {
        if (startFuel >= target) {
            return 0;
        }
        // 油量大根堆
        PriorityQueue<Integer> heap = new PriorityQueue<>((a, b) -> b - a);
        int to = startFuel;
        int cnt = 0;
        for (int[] station : stations) {
            int position = station[0];
            int fuel = station[1];
            if (to < position) {
                // 到不了下个加油站，进行提前加油
                while (!heap.isEmpty() && to < position) {
                    // 加上油，记录加油次数
                    to += heap.poll();
                    cnt++;
                    if (to >= target) {
                        // 如果加的油能够到达目的地，直接返回次数
                        return cnt;
                    }
                }
                // 加了所有的油到不了下个加油站，直接返回-1
                if (to < position) {
                    return -1;
                }
            }
            heap.add(fuel);
        }
        // 如果到这里了，走完了所有的加油站还没到终点，把剩余的油加完，看是否到达目的地
        while (!heap.isEmpty()) {
            to += heap.poll();
            cnt++;
            if (to >= target) {
                return cnt;
            }
        }
        return -1;
    }
}
