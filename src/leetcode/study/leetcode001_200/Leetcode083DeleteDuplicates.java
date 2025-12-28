package leetcode.study.leetcode001_200;

import java.util.Map;
import java.util.TreeMap;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/6/7 16:28
 * https://leetcode.cn/problems/remove-duplicates-from-sorted-list/
 */
public class Leetcode083DeleteDuplicates {

    public ListNode deleteDuplicates(ListNode head) {
        if (head == null) {
            return head;
        }

        ListNode root = new ListNode(-200);
        ListNode cur = root;
        int preVal = cur.val;
        ListNode next;
        while (head != null) {
            if (head.val != preVal) {
                next = new ListNode(head.val);
                cur.next = next;
                preVal = head.val;
                cur = next;
            }
            head = head.next;
        }
        return root.next;
    }

    public ListNode deleteDuplicates1(ListNode head) {
        if (head == null) {
            return head;
        }
        TreeMap<Integer, Integer> map = new TreeMap<>();
        while (head != null) {
            map.put(head.val, map.getOrDefault(head.val, 0) + 1);
            head = head.next;
        }
        ListNode root = new ListNode();
        ListNode cur = root;
        ListNode next;
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            next = new ListNode(entry.getKey());
            cur.next = next;
            cur = next;
        }
        return root.next;
    }

    public class ListNode {
        int val;
        ListNode next;
        ListNode() {}
        ListNode(int val) { this.val = val; }
        ListNode(int val, ListNode next) { this.val = val; this.next = next; }
    }
}
