package leetcode.study.leetcdoe201_400;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/7/30 20:25
 * https://leetcode.cn/problems/linked-list-random-node/
 */
public class RandomNode {
//    List<Integer> list;
//    int size;
//    public Solution(ListNode head) {
//        list = new ArrayList<>();
//        while (head != null) {
//            list.add(head.val);
//            head = head.next;
//        }
//        size = list.size();
//    }
//
//    public int getRandom() {
//        int index = (int) (Math.random() * size);
//        return list.get(index);
//    }


    public class ListNode {
        int val;
        ListNode next;

        ListNode() {
        }

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }
}
