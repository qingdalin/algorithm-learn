package leetcode.study.leetcode001_200;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/6/27 20:06
 * https://leetcode.cn/problems/reorder-list/
 */
public class Leetcode143ReorderList {
    public void reorderList(ListNode head) {
        List<Integer> list = new ArrayList<>();
        ListNode tmp = head;
        while (tmp != null) {
            list.add(tmp.val);
            tmp = tmp.next;
        }

        List<Integer> collect = new ArrayList<>();
        for (int l = 0, r = list.size() - 1; l <= r; l++, r--) {
            if (l == r) {
                collect.add(list.get(l));
            } else {
                collect.add(list.get(l));
                collect.add(list.get(r));
            }
        }
        ListNode cur = head;
        ListNode next;
        for (int i = 1; i < collect.size(); i++) {
            next = new ListNode(collect.get(i));
            cur.next = next;
            cur = cur.next;
        }
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
