package leetcode.study.leetcdoe201_400;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/7/12 10:22
 */
public class Leetcode203RemoveElements {
    public ListNode removeElements(ListNode head, int val) {
        if (head == null) {
            return head;
        }
        ListNode root = new ListNode(-1000);
        root.next = head;
        ListNode tmp = root;
        while (tmp.next != null) {
            if (tmp.next.val == val) {
                tmp.next = tmp.next.next;
            } else {
                tmp = tmp.next;
            }
        }
        return root.next;
    }

    public ListNode removeElements1(ListNode head, int val) {
        if (head == null) {
            return head;
        }
        List<Integer> list = new ArrayList<>();
        while (head != null) {
            if (head.val != val) {
                list.add(head.val);
            }
            head = head.next;
        }
        ListNode root = new ListNode(-1000);
        ListNode cur = root;
        ListNode next = null;
        for (Integer num : list) {
            next = new ListNode(num);
            cur.next = next;
            cur = cur.next;
        }
        return root.next;
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
