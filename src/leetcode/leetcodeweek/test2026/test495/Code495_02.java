package leetcode.leetcodeweek.test2026.test495;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2026/3/29 10:24
 * https://leetcode.cn/contest/weekly-contest-495/problems/design-event-manager/
 */
public class Code495_02 {
    class EventManager {
        public Map<Integer, Integer> idToPriority;
        public PriorityQueue<int[]> heap;
        public EventManager(int[][] events) {
            idToPriority = new HashMap<>();
            heap = new PriorityQueue<>((a, b) -> b[1] != a[1] ? b[1] - a[1] : a[0] - b[0]);
            for (int[] event : events) {
                // id 和 优先级的哈希表，更新使用
                idToPriority.put(event[0], event[1]);
                // 先根据优先级降序，再根据id升序
                heap.add(event);
            }
        }

        public void updatePriority(int eventId, int newPriority) {
            // 更新id的新的优先级，不更新堆，懒更新，再把id和优先级添加进去
            idToPriority.put(eventId, newPriority);
            heap.add(new int[] {eventId, newPriority});
        }
        // 13 8
        // 13 8
        // 14 7
        // 20 6
        // 13 2
        // 17 2
        // 13 1
        public int pollHighest() {
            while (!heap.isEmpty()) {
                // 当前优先级
                int[] cur = heap.poll();
                if (idToPriority.containsKey(cur[0])
                    && idToPriority.get(cur[0]) == cur[1]) {
                    // 如果id的哈希表有这个id并且优先级和最新的一样，弹出，并且删除该id的哈希
                    idToPriority.remove(cur[0]);
                    return cur[0];
                }
            }
            return -1;
        }

        public int pollHighest1() {
            while (!heap.isEmpty()) {
                // 当前优先级
                int[] cur = heap.peek();
                if (idToPriority.containsKey(cur[0])
                    && idToPriority.get(cur[0]) == cur[1]) {
                    // 如果id的哈希表有这个id并且优先级和最新的一样，弹出，并且删除该id的哈希
                    heap.poll();
                    idToPriority.remove(cur[0]);
                    return cur[0];
                } else {
                    // 到这说明都是无用数据，弹出
                    heap.poll();
                }
            }
            return -1;
        }
    }
}
