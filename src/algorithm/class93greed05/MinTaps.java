package algorithm.class93greed05;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2024/7/27 20:18
 * 在 x 轴上有一个一维的花园。花园长度为 n，从点 0 开始，到点 n 结束。
 *
 * 花园里总共有 n + 1 个水龙头，分别位于 [0, 1, ..., n] 。
 *
 * 给你一个整数 n 和一个长度为 n + 1 的整数数组 ranges ，
 * 其中 ranges[i] （下标从 0 开始）表示：如果打开点 i 处的水龙头，可以灌溉的区域为 [i -  ranges[i], i + ranges[i]] 。
 *
 * 请你返回可以灌溉整个花园的 最少水龙头数目 。如果花园始终存在无法灌溉到的地方，请你返回 -1 。
 * https://leetcode.cn/problems/minimum-number-of-taps-to-open-to-water-a-garden/description/
 */
public class MinTaps {
    public static int minTaps(int n, int[] ranges) {
        int[] right = new int[n + 1];
        // right[i] = j:表示所有左边界在i位置的水龙头最右影响的边界是j
        for (int i = 0, start; i < ranges.length; i++) {
            start = Math.max(0, i - ranges[i]);
            right[start] = Math.max(right[start], i + ranges[i]);
        }
        int cur = 0; // 当前水龙头影响的范围
        int next = 0; // 下一个影响的范围
        int ans = 0; // 水龙头的数量
        for (int i = 0; i < n; i++) {
            next = Math.max(next, right[i]);
            if (cur == i) {
                if (next > i) {
                    cur = next;
                    ans++;
                } else {
                    // 如果next小于等于i，下一个直接就和cur一样，
                    return -1;
                }
            }
        }
        return ans;
    }

    public static void main(String[] args) {
        int n = 5;
        int[] nums = {3,4,1,1,0,0};
        System.out.println(minTaps(n, nums));
    }
}
