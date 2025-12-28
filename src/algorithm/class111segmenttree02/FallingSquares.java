package algorithm.class111segmenttree02;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2024/9/2 20:05
 * 在二维平面上的 x 轴上，放置着一些方块。
 *
 * 给你一个二维整数数组 positions ，其中 positions[i] = [lefti, sideLengthi]
 * 表示：第 i 个方块边长为 sideLengthi ，其左侧边与 x 轴上坐标点 lefti 对齐。
 *
 * 每个方块都从一个比目前所有的落地方块更高的高度掉落而下。方块沿 y 轴负方向下落，直到着陆到 另一个正方形的顶边 或者是 x 轴上 。
 * 一个方块仅仅是擦过另一个方块的左侧边或右侧边不算着陆。一旦着陆，它就会固定在原地，无法移动。
 *
 * 在每个方块掉落后，你必须记录目前所有已经落稳的 方块堆叠的最高高度 。
 *
 * 返回一个整数数组 ans ，其中 ans[i] 表示在第 i 块方块掉落后堆叠的最高高度。
 * https://leetcode.cn/problems/falling-squares/description/
 */
public class FallingSquares {
    public static int MAXN = 2001;
    public static int[] arr = new int[MAXN];
    public static int[] max = new int[MAXN << 2];
    public static int[] change = new int[MAXN << 2];
    public static boolean[] update = new boolean[MAXN << 2];

    public static void main(String[] args) {
        FallingSquares fallingSquares = new FallingSquares();
        //int[][] arr = {{100,100},{200,100}};
        int[][] arr = {{1,2},{2,3},{6,1}};
        System.out.println(fallingSquares.fallingSquares(arr));
    }

    public List<Integer> fallingSquares(int[][] positions) {
        // 离散化处理
        int n = collect(positions);
        int max = 0;
        List<Integer> ans = new ArrayList<>();
        build(1, n, 1);
        for (int[] pos : positions) {
            // 找到当前方块的左右下标
            int l = rank(pos[0], n);
            int r = rank(pos[1] + pos[0] - 1, n);
            // 当前的边长加上范围内的最大值，就是当前的h高度
            int h = query(l, r, 1, n, 1) + pos[1];
            // pk最大值
            max = Math.max(max, h);
            ans.add(max);
            // 更新当前范围的最大值
            update(l, r, h, 1, n, 1);
        }
        return ans;
    }

    private int rank(int v, int n) {
        int l = 1, r = n, m = 0;
        int ans = 0;
        while (l <= r) {
            m = (l + r) / 2;
            if (arr[m] >= v) {
                ans = m;
                r = m - 1;
            } else {
                l = m + 1;
            }
        }
        return ans;
    }

    public static void up(int i) {
        max[i] = Math.max(max[i << 1], max[i << 1 | 1]);
    }

    public static void down(int i) {
        if (update[i]) {
            lazy(i << 1, change[i]);
            lazy(i << 1 | 1, change[i]);
            update[i] = false;
        }
    }

    private static void lazy(int i, int v) {
        max[i] = v;
        change[i] = v;
        update[i] = true;
    }

    public static void build(int l, int r, int i) {
        if (l < r) {
            int mid = (l + r) >> 1;
            build(l, mid, i << 1);
            build(mid + 1, r, i << 1 | 1);
        }
        max[i] = 0;
        change[i] = 0;
        update[i] = false;
    }

    public static void update(int jobl, int jobr, int jobv, int l, int r, int i) {
        if (jobl <= l && r <= jobr) {
            lazy(i, jobv);
        } else {
            down(i);
            int mid = (l + r) >> 1;
            if (jobl <= mid) {
                update(jobl, jobr, jobv, l, mid, i << 1);
            }
            if (jobr > mid) {
                update(jobl, jobr, jobv, mid + 1, r, i << 1 | 1);
            }
            up(i);
        }
    }

    public static int query(int jobl, int jobr, int l, int r, int i) {
        if (jobl <= l && r <= jobr) {
            return max[i];
        } else {
            down(i);
            int mid = (l + r) >> 1;
            int ans = Integer.MIN_VALUE;
            if (jobl <= mid) {
                ans = Math.max(ans, query(jobl, jobr, l, mid, i << 1));
            }
            if (jobr > mid) {
                ans = Math.max(ans, query(jobl, jobr, mid + 1, r, i << 1 | 1));
            }
            return ans;
        }
    }

    private int collect(int[][] positions) {
        int size = 1;
        for (int[] pos : positions) {
            arr[size++] = pos[0];
            arr[size++] = pos[1] + pos[0] - 1;
        }
        Arrays.sort(arr, 1, size);
        int n = 1;
        for (int i = 2; i < size; i++) {
            if (arr[n] != arr[i]) {
                arr[++n] = arr[i];
            }
        }
        return n;
    }
}
