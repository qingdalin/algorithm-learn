package leetcode.study.leetcode001_200;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/6/27 20:22
 * https://leetcode.cn/problems/insertion-sort-list/
 */
public class Leetcode147InsertionSortList {
    public ListNode insertionSortList(ListNode head) {
        List<Integer> list = new ArrayList<>();
        while (head != null) {
            list.add(head.val);
            head = head.next;
        }
        list.sort((a, b) -> a - b);
        ListNode root = new ListNode(-1);
        ListNode cur = root;
        ListNode next;
        for (int num : list) {
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
