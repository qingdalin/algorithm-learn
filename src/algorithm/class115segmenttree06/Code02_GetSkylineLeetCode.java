package algorithm.class115segmenttree06;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2024/9/16 8:40
 * https://leetcode.cn/problems/the-skyline-problem/description/
 * 城市的 天际线 是从远处观看该城市中所有建筑物形成的轮廓的外部轮廓。给你所有建筑物的位置和高度，
 * 请返回 由这些建筑物形成的 天际线 。
 * 每个建筑物的几何信息由数组 buildings 表示，其中三元组 buildings[i] = [lefti, righti, heighti] 表示：
 * lefti 是第 i 座建筑物左边缘的 x 坐标。
 * righti 是第 i 座建筑物右边缘的 x 坐标。
 * heighti 是第 i 座建筑物的高度。
 * 你可以假设所有的建筑都是完美的长方形，在高度为 0 的绝对平坦的表面上。
 * 天际线 应该表示为由 “关键点” 组成的列表，格式 [[x1,y1],[x2,y2],...] ，
 * 并按 x 坐标 进行 排序 。关键点是水平线段的左端点。列表中最后一个点是最右侧建筑物的终点，y 坐标始终为 0 ，
 * 仅用于标记天际线的终点。此外，任何两个相邻建筑物之间的地面都应被视为天际线轮廓的一部分。
 *
 * 注意：输出天际线中不得有连续的相同高度的水平线。例如 [...[2 3], [4 5], [7 5], [11 5], [12 7]...]
 * 是不正确的答案；三条高度为 5 的线应该在最终输出中合并为一个：[...[2 3], [4 5], [12 7], ...]
 */
public class Code02_GetSkylineLeetCode {
    public static int MAXN = 30001;
    public static int[] xsort = new int[MAXN];
    public static int[] height = new int[MAXN];
    public List<List<Integer>> getSkyline(int[][] arr) {
        int n = arr.length;
        int m = prepare(arr, n);
        // 堆按照高度降序，存储高度和过期下标
        PriorityQueue<int[]> heap = new PriorityQueue<>((a, b) -> b[0] - a[0]);
        for (int i = 1, j = 0; i <= m; i++) {
            for (; j < n && arr[j][0] <= i; j++) {
                heap.add(new int[] {arr[j][2], arr[j][1]});
            }
            while (!heap.isEmpty() && heap.peek()[1] < i) {
                heap.poll();
            }
            if (!heap.isEmpty()) {
                height[i] = heap.peek()[0];
            }
        }
        List<List<Integer>> ans = new ArrayList<>();
        for (int i = 1, pre = 0; i <= m; i++) {
            if (pre != height[i]) {
                ans.add(Arrays.asList(xsort[i], height[i]));
            }
            pre = height[i];
        }
        return ans;
    }

    private int prepare(int[][] arr, int n) {
        int size = 0;
        for (int i = 0; i < n; i++) {
            // 将原数组处理，改为左，右-1，右
            xsort[++size] = arr[i][0];
            xsort[++size] = arr[i][1] - 1;
            xsort[++size] = arr[i][1];
        }
        Arrays.sort(xsort, 1, size + 1);
        // xsort去重
        int m = 1;
        for (int i = 2; i <= size; i++) {
            if (xsort[m] != xsort[i]) {
                xsort[++m] = xsort[i];
            }
        }
        // 将arr原数组处理为离散化后的下标
        for (int i = 0; i < n; i++) {
            arr[i][0] = rank(m, arr[i][0]);
            arr[i][1] = rank(m, arr[i][1] - 1);
        }
        // 高度数组清空
        Arrays.fill(height, 1, m + 1, 0);
        // 按照开始位置升序
        Arrays.sort(arr, 0, n, (a, b) -> a[0] - b[0]);
        return m;
    }

    private int rank(int m, int v) {
        int l = 1, r = m, mid = 0;
        int ans = 0;
        while (l <= r) {
            mid = (l + r) / 2;
            if (xsort[mid] >= v) {
                ans = mid;
                r = mid - 1;
            } else {
                l = mid + 1;
            }
        }
        return ans;
    }
}
