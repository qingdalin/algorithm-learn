package leetcode.study.leetcdoe201_400;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/7/29 19:42
 * https://leetcode.cn/problems/find-k-pairs-with-smallest-sums/
 */
public class Leetcode373KSmallestPairs {
    public static List<List<Integer>> kSmallestPairs(int[] nums1, int[] nums2, int k) {
        PriorityQueue<int[]> heap = new PriorityQueue<>(k, (o1, o2)->{
            return nums1[o1[0]] + nums2[o1[1]] - nums1[o2[0]] - nums2[o2[1]];
        });

        List<List<Integer>> ans = new ArrayList<>();
        int n = nums1.length;
        int m = nums2.length;
        for (int i = 0; i < Math.min(n, k); i++) {
            heap.add(new int[] {i, 0});
        }
        List<Integer> cur;
        while (k-- > 0 && !heap.isEmpty()) {
            int[] arr = heap.poll();
            cur = new ArrayList<>();
            cur.add(nums1[arr[0]]);
            cur.add(nums2[arr[1]]);
            ans.add(cur);
            if (arr[1] + 1 < m) {
                heap.add(new int[] {arr[0], arr[1] + 1});
            }
        }
        return ans;
    }

    public static void main(String[] args) {
        int[] arr1 = new int[] {1,2,4,5,6};
        int[] arr2 = new int[] {3,5,7,9};
        int k = 3;
        System.out.println(kSmallestPairs(arr1, arr2, k));
    }
}
