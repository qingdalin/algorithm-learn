package leetcode.study.leetcdoe201_400;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/7/27 15:13
 * https://leetcode.cn/problems/intersection-of-two-arrays/
 */
public class Leetcode349Intersection {
    public int[] intersection(int[] nums1, int[] nums2) {
        Arrays.sort(nums1);
        Arrays.sort(nums2);
        int n = nums1.length, m = nums2.length;
        int[] ans = new int[n + m];
        int i = 0, j = 0, idx = 0, a, b;
        while (i < n && j < m) {
            a = nums1[i];
            b = nums2[j];
            if (a == b) {
                if (idx == 0 || ans[idx - 1] != a) {
                    ans[idx++] = a;
                }
                i++;
                j++;
            } else if (a < b) {
                i++;
            } else {
                j++;
            }
        }
        return Arrays.copyOfRange(ans, 0, idx);
    }

    public int[] intersection2(int[] nums1, int[] nums2) {
        Set<Integer> list = new HashSet<>();
        int n = nums1.length;
        int m = nums2.length;
        if (n > m) {
            int tmp = n;
            n = m;
            m = tmp;
            int[] arrTmp = nums1;
            nums1 = nums2;
            nums2 = arrTmp;
        }
        Set<Integer> set = new HashSet<>();
        for (int i = 0; i < m; i++) {
            set.add(nums2[i]);
        }
        for (int i = 0; i < n; i++) {
            if (set.contains(nums1[i])) {
                list.add(nums1[i]);
            }
        }
        int[] ans = new int[list.size()];
        int i = 0;
        for (Integer num : list) {
            ans[i++] = num;
        }
        return ans;
    }
}
