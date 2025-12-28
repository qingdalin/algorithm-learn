package algorithm;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 汪大鹏
 * @version 1.0
 * @date 2024-01-06 16:02
 */
public class LruCache {
    class LRUCache {
        class DoubleNode {
            public int k;
            public int v;
            public DoubleNode last;
            public DoubleNode next;

            public DoubleNode(int k, int v){
                this.k = k;
                this.v = v;
            }
        }

        class DoubleNodeList {
            public DoubleNode head;
            public DoubleNode tail;
            public DoubleNodeList() {
                head = null;
                tail = null;
            }

            public void add(DoubleNode newNode) {
                if (newNode == null) {
                    return;
                }
                if (head == null) {
                    head = newNode;
                    tail = newNode;
                } else {
                    tail.next = newNode;
                    newNode.last = tail;
                    tail = newNode;
                }
            }

            public void moveToEnd(DoubleNode newNode) {
                if (newNode == tail) {
                    return;
                }
                if (newNode == head) {
                    head = newNode.next;
                    newNode.last = null;
                } else {
                    newNode.last.next = newNode.next;
                    newNode.next.last = newNode.last;
                }
                newNode.last = tail;
                newNode.next = null;
                tail.next = newNode;
                tail = newNode;
            }

            public DoubleNode removeHead() {
                if (head == null) {
                    return null;
                }
                DoubleNode ans = head;
                if (head == tail) {
                    head = null;
                    tail = null;
                } else {
                    head = ans.next;
                    ans.next = null;
                    head.last = null;
                }
                return ans;
            }
        }

        public Map<Integer, DoubleNode> map;
        public int cap;
        public DoubleNodeList doubleNodeList;
        public LRUCache(int capacity) {
            cap = capacity;
            map = new HashMap<>();
            doubleNodeList = new DoubleNodeList();
        }

        public int get(int key) {
            if (map.containsKey(key)) {
                DoubleNode doubleNode = map.get(key);
                doubleNodeList.moveToEnd(doubleNode);
                return doubleNode.v;
            }
            return -1;
        }

        public void put(int key, int value) {
            if (map.containsKey(key)) {
                DoubleNode doubleNode = map.get(key);
                doubleNode.v = value;
                doubleNodeList.moveToEnd(doubleNode);
            } else {
                if (map.size() >= cap) {
                    DoubleNode doubleNode = doubleNodeList.removeHead();
                    map.remove(doubleNode.k);
                }
                DoubleNode newNode = new DoubleNode(key, value);
                map.put(key, newNode);
                doubleNodeList.add(newNode);
            }
        }
    }
}
