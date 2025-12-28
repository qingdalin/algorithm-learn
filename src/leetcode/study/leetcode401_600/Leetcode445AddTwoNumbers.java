package leetcode.study.leetcode401_600;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/8/16 15:38
 * https://leetcode.cn/problems/add-two-numbers-ii/
 */
public class Leetcode445AddTwoNumbers {
    public static ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode ans = add2(l1, l2);
        return ans;
    }

    private static ListNode add2(ListNode l1, ListNode l2) {
        List<Integer> list1 = fromNodeToList(l1);
        List<Integer> list2 = fromNodeToList(l2);
        int n = list1.size();
        int m = list2.size();
        int carry = 0;
        List<Integer> collect = new ArrayList<>();
        for (int i = n - 1, j = m - 1, a, b; i >= 0 || j >= 0; i--, j--) {
            a = i < 0 ? 0 : list1.get(i);
            b = j < 0 ? 0 : list2.get(j);
            int curNum = a + b + carry;
            collect.add(curNum % 10);
            carry = curNum / 10;
        }
        if (carry != 0) {
            collect.add(carry);
        }
        ListNode ans = new ListNode(-1);
        ListNode head = ans;
        int size = collect.size();
        for(int i = size - 1; i >= 0; i--) {
            ListNode next = new ListNode(collect.get(i));
            head.next = next;
            head = next;
        }
        return ans.next;
    }

    private static List<Integer> fromNodeToList(ListNode l1) {
        List<Integer> list = new ArrayList<>();
        while (l1 != null) {
            list.add(l1.val);
            l1 = l1.next;
        }
        return list;
    }

    private static ListNode add1(ListNode l1, ListNode l2) {
        l1 = reverse(l1);
        l2 = reverse(l2);
        int carry = 0;
        ListNode ans = new ListNode(-1);
        ListNode head = ans;
        for (int a, b; l1 != null || l2 != null; l1 = l1 == null ? null : l1.next, l2 = l2 == null ? null : l2.next) {
            a = l1 == null ? 0 : l1.val;
            b = l2 == null ? 0 : l2.val;
            int curNum = a + b + carry;
            ListNode next = new ListNode(curNum % 10);
            head.next = next;
            head = next;
            carry = curNum / 10;
        }
        if (carry != 0) {
            head.next = new ListNode(carry);
        }
        return reverse(ans.next);
    }

    public static ListNode reverse(ListNode node) {
        ListNode pre = null, cur = node, tmp;
        while (cur != null) {
            tmp = cur.next;
            cur.next = pre;
            pre = cur;
            cur = tmp;
        }
        return pre;
    }

    static class ListNode {
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
