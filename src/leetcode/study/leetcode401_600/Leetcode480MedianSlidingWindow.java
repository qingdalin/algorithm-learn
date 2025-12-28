package leetcode.study.leetcode401_600;

import java.util.Arrays;
import java.util.PriorityQueue;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/8/30 10:49
 * https://leetcode.cn/problems/sliding-window-median/
 */
public class Leetcode480MedianSlidingWindow {
    public static int MAXN = 100001;
    public static PriorityQueue<int[]> maxHeap = new PriorityQueue<>((a, b) -> b[0] - a[0]);
    public static PriorityQueue<int[]> minHeap = new PriorityQueue<>((a, b) -> a[0] - b[0]);

    public static int n;
    public static int[] arr = new int[MAXN];
    public static double[] medianSlidingWindow(int[] nums, int k) {
        maxHeap.clear();
        minHeap.clear();
        n = nums.length;
        arr = nums;
        double[] ans = new double[n - k];
        for (int i = 0; i < k; i++) {
            add(i);
        }
        for (int i = k; i < n; i++) {
            ans[i - k] = findMidNum();
            remove(i - k);
            add(i);
        }
        return ans;
    }

    public static double findMidNum() {
        if (maxHeap.size() == minHeap.size()) {
            return ((double) maxHeap.peek()[0] + minHeap.peek()[0]) / 2;
        } else {
            return maxHeap.size() > minHeap.size() ? maxHeap.peek()[0] : minHeap.peek()[0];
        }
    }

    public static void remove(int i) {
        int[] cur = {arr[i], i};
        boolean remove = maxHeap.remove(cur);
        if (!remove) {
            minHeap.remove(cur);
        }
        balance();
    }

    public static void add(int i) {
        // 大根堆为空或者当前数小于大根堆
        if (maxHeap.isEmpty() || maxHeap.peek()[0] >= arr[i]) {
            maxHeap.add(new int[] {arr[i], i});
        } else {
            minHeap.add(new int[] {arr[i], i});
        }
        balance();
    }

    public static void balance() {
        if (Math.abs(maxHeap.size() - minHeap.size()) > 1) {
            if (maxHeap.size() > minHeap.size()) {
                int[] cur = maxHeap.poll();
                minHeap.add(cur);
            } else {
                maxHeap.add(minHeap.poll());
            }
        }
    }

    public static void main(String[] args) {
        int[] arr = {1,3,-1,-3,5,3,6,7};
        System.out.println(Arrays.toString(medianSlidingWindow(arr, 3)));
    }
}
