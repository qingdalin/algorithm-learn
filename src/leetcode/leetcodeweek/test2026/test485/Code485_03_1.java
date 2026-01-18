package leetcode.leetcodeweek.test2026.test485;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.TreeMap;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2026/1/18 9:29
 * https://leetcode.cn/contest/weekly-contest-485/problems/design-auction-system/
 */
public class Code485_03_1 {
    class AuctionSystem {
        public Map<Integer, Integer> amount;
        // key: userId和itemId
        // val: 价格
        public Map<Integer, PriorityQueue<int[]>> itemHeap;
        // key: itemId
        // val: {amount, userId}
        public AuctionSystem() {
            amount = new HashMap<>();
            itemHeap = new HashMap<>();
        }

        public void addBid(int userId, int itemId, int bidAmount) {
            amount.put(userId << 16 | itemId, bidAmount);
            if (itemHeap.containsKey(itemId)) {
                itemHeap.get(itemId).add(new int[] {bidAmount, userId});
            } else {
                PriorityQueue<int[]> heap =
                    new PriorityQueue<>((a, b) -> a[0] != b[0] ? b[0] - a[0] : b[1] - a[1]);
                heap.add(new int[] {bidAmount, userId});
                itemHeap.put(itemId, heap);
            }
        }

        public void updateBid(int userId, int itemId, int newAmount) {
            addBid(userId, itemId, newAmount);
        }

        public void removeBid(int userId, int itemId) {
            amount.remove(userId << 16 | itemId);
        }

        public int getHighestBidder(int itemId) {
            PriorityQueue<int[]> heap = itemHeap.get(itemId);
            if (heap == null) {
                return -1;
            }
            while (!heap.isEmpty()) {
                int[] cur = heap.peek();
                int bidAmount = cur[0];
                int userId = cur[1];
                Integer realAmount = amount.get(userId << 16 | itemId);
                if (realAmount != null && bidAmount == realAmount) {
                    return userId;
                }
                heap.poll();
            }
            return -1;
        }
    }
}
