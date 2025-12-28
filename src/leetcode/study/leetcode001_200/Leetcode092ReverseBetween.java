package leetcode.study.leetcode001_200;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/6/7 17:11
 * https://leetcode.cn/problems/reverse-linked-list-ii/
 */
public class Leetcode092ReverseBetween {
    public static ListNode reverseBetween(ListNode head, int left, int right) {
        int[] arr = new int[501];
        int len = 0;
        while (head != null) {
            arr[++len] = head.val;
            head = head.next;
        }
        reverseList(arr, left, right);
        ListNode root = new ListNode(-1000);
        ListNode cur = root;
        ListNode next;
        for (int i = 1; i <= len; i++) {
            next = new ListNode(arr[i]);
            cur.next = next;
            cur = cur.next;
        }
        return root.next;
    }

    public static void reverseList(int[] arr, int left, int right) {
        for (int l = left, r = right; l <= r; l++, r--) {
            int tmp = arr[l];
            arr[l] = arr[r];
            arr[r] = tmp;
        }
    }


     static class ListNode {
         int val;
         ListNode next;
         ListNode() {}
         ListNode(int val) { this.val = val; }
         ListNode(int val, ListNode next) { this.val = val; this.next = next; }
     }
}
