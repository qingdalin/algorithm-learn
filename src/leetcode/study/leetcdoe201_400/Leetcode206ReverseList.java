package leetcode.study.leetcdoe201_400;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/8/16 15:44
 * https://leetcode.cn/problems/reverse-linked-list/
 */
public class Leetcode206ReverseList {
    public ListNode reverseList(ListNode head) {
        ListNode newHead = null, next = null;
        while (head != null) {
            next = head.next;
            head.next = newHead;
            newHead = head;
            head = next;
        }
        return head;
    }

    public ListNode reverseList1(ListNode head) {
        ListNode newHead = null, cur = head, tmp;
        while (cur != null) {
            tmp = cur.next;
            cur.next = newHead;
            newHead = cur;
            cur = tmp;
        }
        return newHead;
    }


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
