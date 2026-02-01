package leetcode.leetcodeweek.test2026.test487;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2026/2/1 10:18
 * https://leetcode.cn/contest/weekly-contest-487/problems/design-ride-sharing-system/
 */
public class Code487_03 {
    class RideSharingSystem {
        public Deque<Integer> riderQueue;
        public Deque<Integer> driverQueue;
        public RideSharingSystem() {
            riderQueue = new ArrayDeque<>();
            driverQueue = new ArrayDeque<>();
        }

        public void addRider(int riderId) {
            riderQueue.addLast(riderId);
        }

        public void addDriver(int driverId) {
            driverQueue.addLast(driverId);
        }

        public int[] matchDriverWithRider() {
            if (riderQueue.isEmpty() || driverQueue.isEmpty()) {
                return new int[] {-1, -1};
            }
            return new int[] {driverQueue.pollFirst(), riderQueue.pollFirst()};
        }

        public void cancelRider(int riderId) {
            riderQueue.remove(riderId);
        }
    }
}
