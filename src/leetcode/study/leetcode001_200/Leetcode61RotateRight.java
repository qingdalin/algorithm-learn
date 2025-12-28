package leetcode.study.leetcode001_200;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/5/18 20:54
 * https://leetcode.cn/problems/rotate-list/description/
 */
public class Leetcode61RotateRight {

    public ListNode rotateRight(ListNode head, int k) {
        List<Integer> list = new ArrayList<>();
        int length = 0;
        while (head != null) {
            length++;
            list.add(head.val);
            head = head.next;
        }
        int[] arr = new int[length];
        for (int i = 0; i < length; i++) {
            int index = (i + k) % length;
            arr[index] = list.get(i);
        }
        ListNode ans = new ListNode(-1);
        ListNode next;
        ListNode cur = ans;
        // 新头 -> next ->
        // cur -> next
        //        cur -> next
        for (int i = 0; i < length; i++) {
            next = new ListNode(arr[i]);
            cur.next = next;
            cur = next;
        }
        return ans.next;
    }

     public class ListNode {
         int val;
         ListNode next;
         ListNode() {}
         ListNode(int val) { this.val = val; }
         ListNode(int val, ListNode next) { this.val = val; this.next = next; }
     }
}
