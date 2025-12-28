package algorithm.class89greedy01;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;
import java.util.PriorityQueue;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2024/7/21 13:36
 * https://leetcode.cn/problems/minimum-cost-to-connect-sticks/description/
 * https://www.luogu.com.cn/problem/P1090
 * 连接棒材的最小花费，赫夫曼编码
 */
public class MinCostConnect {
    public static int MAXN = 10001;
    public static int[] nums = new int[MAXN];
    public static int n;
    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StreamTokenizer st = new StreamTokenizer(bf);
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        while (st.nextToken() != StreamTokenizer.TT_EOF) {
            n = (int) st.nval;
            for (int i = 0; i < n; i++) {
                st.nextToken();
                nums[i] = (int) st.nval;
            }
            System.out.println(f());
        }
        out.flush();
        out.close();
        bf.close();
    }

    private static int f() {
        PriorityQueue<Integer> heap = new PriorityQueue<>();
        for (int i = 0; i < n; i++) {
            heap.add(nums[i]);
        }
        int sum = 0, cur = 0;
        while (heap.size() > 1) {
            cur = heap.poll() + heap.poll();
            sum += cur;
            heap.add(cur);
        }
        return sum;
    }
}
