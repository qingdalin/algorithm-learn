package leetcode.study.leetcode001_200;

import java.util.Map;
import java.util.TreeMap;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/6/7 16:15
 * https://leetcode.cn/problems/remove-duplicates-from-sorted-list-ii/
 */
public class Leetcode082DeleteDuplicates {
    public static ListNode deleteDuplicates(ListNode head) {
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
            if (entry.getValue() > 1) {
                continue;
            }
            next = new ListNode(entry.getKey());
            cur.next = next;
            cur = next;
        }
        return root.next;
    }


     static class ListNode {
         int val;
         ListNode next;
         ListNode() {}
         ListNode(int val) { this.val = val; }
         ListNode(int val, ListNode next) { this.val = val; this.next = next; }
     }
}
