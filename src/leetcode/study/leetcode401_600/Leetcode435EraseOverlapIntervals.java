package leetcode.study.leetcode401_600;

import java.util.Arrays;
import java.util.PriorityQueue;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/8/15 19:34
 * https://leetcode.cn/problems/non-overlapping-intervals/
 */
public class Leetcode435EraseOverlapIntervals {
    public static int eraseOverlapIntervals1(int[][] arr) {
        int n = arr.length;
        Arrays.sort(arr, (a, b) -> a[0] != b[0] ? a[0] - b[0] : a[1] - b[1]);
        PriorityQueue<Integer> heap = new PriorityQueue<>((a, b) -> b - a);
        int cnt = 0;
        // 1 2
        // 1   3
        //   2 3
        //     3 4
        for (int i = 0; i < n; i++) {
            if (!heap.isEmpty() && arr[i][0] < heap.peek()) {
                cnt++;
            } else {
                heap.add(arr[i][1]);
            }
        }
        return cnt;
    }

    public static int eraseOverlapIntervals(int[][] arr) {
        int n = arr.length;
        Arrays.sort(arr, (a, b) -> a[1] - b[1]);
        int cnt = 0;
        // 1 2
        // 1   3
        //   2 3
        //     3 4
        int limit = arr[0][1];
        for (int i = 1; i < n; i++) {
            if (arr[i][0] < limit) {
                cnt++;
            } else {
                limit = arr[i][1];
            }
        }
        return cnt;
    }

    public static void main(String[] args) {
//        int[][] arr = new int[][]{
//            {1, 2},
//            {2, 3},
//            {3, 4},
//            {1, 3}
//        };
//        int[][] arr = new int[][]{
//            {1, 100},
//            {11, 22},
//            {1, 11},
//            {2, 12}
//        };
//        int[][] arr = new int[][]{
//            {0, 2},
//            {1, 3},
//            {2, 4},
//            {3, 5},
//            {4, 6}
//        };
        int[][] arr = new int[][]{
            {-52, 31},
            {-73, -26},
            {82, 97},
            {-65, -11},
            {-62, -49},
            {95, 99},
            {58, 95},
            {-31, 49},
            {66, 98},
            {-63, 2},
            {30, 47},
            {-40, -26}
        };
        System.out.println(eraseOverlapIntervals(arr));
    }
}
