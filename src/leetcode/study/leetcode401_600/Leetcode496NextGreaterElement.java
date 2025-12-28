package leetcode.study.leetcode401_600;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/8/30 17:06
 * https://leetcode.cn/problems/next-greater-element-i/
 */
public class Leetcode496NextGreaterElement {
    public static int[] nextGreaterElement(int[] nums1, int[] nums2) {
        int n = nums1.length, m = nums2.length;
        int[] ans = new int[n];
        Map<Integer, Integer> map = new HashMap<>();
        int[] stack = new int[m];
        int r = 0;
        for (int i = 0; i < m; i++) {
            while (r > 0 && nums2[stack[r - 1]] < nums2[i]) {
                int cur = stack[r - 1];
                map.put(nums2[cur], nums2[i]);
                r--;
            }
            stack[r++] = i;
        }
        while (r > 0) {
            map.put(nums2[stack[r - 1]], -1);
            r--;
        }
        for (int i = 0; i < n; i++) {
            ans[i] = map.get(nums1[i]);
        }
        return ans;
    }

    public static void main(String[] args) {
        int[] arr1 = {1,3,5,2,4};
        int[] arr2 = {6,5,4,3,2,1,7};
        System.out.println(Arrays.toString(nextGreaterElement(arr1, arr2)));
    }

    public int[] nextGreaterElement1(int[] nums1, int[] nums2) {
        int n = nums1.length, m = nums2.length;
        int[] ans = new int[n];
        for (int i = 0; i < n; i++) {
            int next = -1;
            boolean flag = false;
            for (int j = 0; j < m; j++) {
                if (nums1[i] == nums2[j]) {
                    flag = true;
                }
                if (flag && nums2[j] > nums1[i]) {
                    next = nums2[j];
                    break;
                }
            }
            ans[i] = next;
        }
        return ans;
    }
}
