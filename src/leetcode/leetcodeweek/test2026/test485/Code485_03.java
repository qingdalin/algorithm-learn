package leetcode.leetcodeweek.test2026.test485;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2026/1/18 9:29
 * https://leetcode.cn/contest/weekly-contest-485/problems/design-auction-system/
 */
public class Code485_03 {
    class AuctionSystem {
        public Map<Integer, TreeMap<Integer, Integer>> map;
        public Map<Integer, Integer> itemToUserId;
        public Map<Integer, Integer> itemToAmount;
        public AuctionSystem() {
            map = new HashMap<>();
            itemToUserId = new HashMap<>();
            itemToAmount = new HashMap<>();
        }

        public void addBid(int userId, int itemId, int bidAmount) {
            if (map.containsKey(itemId)) {
                TreeMap<Integer, Integer> cur = map.get(itemId);
                cur.put(userId, bidAmount);
            } else {
                TreeMap<Integer, Integer> cur = new TreeMap<>();
                cur.put(userId, bidAmount);
                map.put(itemId, cur);
            }
            update(userId, itemId, bidAmount);
        }

        private void update(int userId, int itemId, int bidAmount) {
            if (itemToAmount.containsKey(itemId)) {
                int oldAmount = itemToAmount.get(itemId);
                if (bidAmount > oldAmount) {
                    itemToUserId.put(itemId, userId);
                } else if (bidAmount == oldAmount) {
                    if (itemToUserId.containsKey(itemId)) {
                        int oldUserId = itemToUserId.get(itemId);
                        if (userId > oldUserId) {
                            itemToUserId.put(itemId, userId);
                        }
                    } else {
                        itemToUserId.put(itemId, userId);
                    }
                }
            } else {
                itemToAmount.put(itemId, bidAmount);
                itemToUserId.put(itemId, userId);
            }
        }

        public void updateBid(int userId, int itemId, int newAmount) {
            TreeMap<Integer, Integer> cur = map.get(itemId);
            cur.put(userId, newAmount);
            update(userId, itemId, newAmount);
        }

        public void removeBid(int userId, int itemId) {
            TreeMap<Integer, Integer> cur = map.get(itemId);
            cur.remove(userId);
            if (cur.isEmpty()) {
                map.remove(itemId);
                itemToUserId.remove(itemId);
                itemToAmount.remove(itemId);
            }
        }

        public int getHighestBidder(int itemId) {
            if (map.containsKey(itemId)) {
                if (itemToUserId.containsKey(itemId)) {
                    return itemToUserId.get(itemId);
                }
            }
            return -1;
        }
    }
}
