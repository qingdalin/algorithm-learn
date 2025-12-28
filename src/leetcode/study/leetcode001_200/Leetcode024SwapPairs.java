package leetcode.study.leetcode001_200;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/4/14 19:24
 * https://leetcode.cn/problems/swap-nodes-in-pairs/
 */
public class Leetcode024SwapPairs {
    public static ListNode swapPairs(ListNode head) {
        if (head == null) {
            return null;
        }
        if (head.next == null) {
            return head;
        }
        ListNode newHead = new ListNode(0);
        newHead.next = head;
        ListNode pre = newHead;
        while (pre.next != null && pre.next.next != null) {
            ListNode tmp = head.next.next;
            pre.next = head.next;
            head.next.next = head;
            head.next = tmp;
            pre = head;
            head = head.next;
        }
        return newHead.next;
    }

    static class ListNode {
        int val;
        ListNode next;

        public ListNode() {
        }

        public ListNode(int val) {
            this.val = val;
        }

        public ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }
}
