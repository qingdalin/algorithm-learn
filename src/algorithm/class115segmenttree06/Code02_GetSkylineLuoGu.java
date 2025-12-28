package algorithm.class115segmenttree06;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2024/9/16 8:40
 * https://www.luogu.com.cn/problem/P1904
 */
public class Code02_GetSkylineLuoGu {
    public static int MAXN = 15001;
    public static int[] xsort = new int[MAXN];
    public static int[][] arr = new int[MAXN][3];
    public static int[] height = new int[MAXN];
    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StreamTokenizer in = new StreamTokenizer(bf);
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        int n = 0;
        while (in.nextToken() != StreamTokenizer.TT_EOF) {
            arr[n][0] = (int) in.nval;
            in.nextToken();
            arr[n][2] = (int) in.nval;
            in.nextToken();
            arr[n][1] = (int) in.nval;
            n++;
        }
        List<List<Integer>> ans = getSkyline(arr, n);
        for (int i = 0; i < ans.size(); i++) {
            System.out.print(ans.get(i).get(0) + " " + ans.get(i).get(1));
            if (i != ans.size() - 1) {
                System.out.print(" ");
            }
        }
        out.flush();
        out.close();
        bf.close();
    }
    public static List<List<Integer>> getSkyline(int[][] arr, int n) {
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

    private static int prepare(int[][] arr, int n) {
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

    private static int rank(int m, int v) {
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
