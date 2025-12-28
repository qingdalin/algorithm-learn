package algorithm.class89greedy01;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;
import java.util.Arrays;
import java.util.PriorityQueue;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2024/7/21 11:38
 * 最大线段重合问题，27节题目2
 * https://leetcode.cn/problems/meeting-rooms-ii/
 *
 * https://www.nowcoder.com/practice/1ae8d0b6bb4e4bcdbf64ec491f63fc37
 * 每一个线段都有start和end两个数据项，表示这条线段在X轴上从start位置开始到end位置结束。
 * 给定一批线段，求所有重合区域中最多重合了几个线段，首尾相接的线段不算重合。
 * 例如：线段[1,2]和线段[2.3]不重合。
 * 线段[1,3]和线段[2,3]重合
 */
public class MeetingRoomsII {
    public static int MAXN = 10001;
    public static int[][] arr = new int[MAXN][2];
    public static int n;
    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StreamTokenizer st = new StreamTokenizer(bf);
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        while (st.nextToken() != StreamTokenizer.TT_EOF) {
            n = (int) st.nval;
            for (int i = 0; i < n; i++) {
                st.nextToken();
                arr[i][0] = (int) st.nval;
                st.nextToken();
                arr[i][1] = (int) st.nval;
            }
            System.out.println(f());
        }
    }

    private static int f() {
        // 开始位置谁小谁在前
        Arrays.sort(arr, 0, n, (a, b) -> a[0] - b[0]);
        PriorityQueue<Integer> heap = new PriorityQueue<>((a, b) -> a - b); // 默认升序
        int ans = 0;
        for (int i = 0; i < n; i++) {
            while (!heap.isEmpty() && heap.peek() <= arr[i][0]) {
                heap.poll();
            }
            heap.add(arr[i][1]);
            ans = Math.max(ans, heap.size());
        }
        return ans;
    }
}
