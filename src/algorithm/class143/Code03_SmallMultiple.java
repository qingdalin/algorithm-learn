package algorithm.class143;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;
import java.util.ArrayDeque;
import java.util.Deque;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2024/12/28 14:23
 * // 正整数倍的最小数位和
 * // 给定一个整数k，求一个k的正整数倍s，使得在十进制下，s的数位累加和最小
 * // 2 <= k <= 10^5
 * // 测试链接 : https://www.luogu.com.cn/problem/AT_arc084_b
 * // 测试链接 : https://atcoder.jp/contests/abc077/tasks/arc084_b
 * // 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例
 */
public class Code03_SmallMultiple {
    public static int MAXK = 100001;
    public static int k;
    public static Deque<int[]> deque = new ArrayDeque<>();
    public static boolean[] visited = new boolean[MAXK];
    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StreamTokenizer in = new StreamTokenizer(bf);
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        in.nextToken(); k = (int) in.nval;
        out.println(bfs());
        out.flush();
        out.close();
        bf.close();
    }

    private static int bfs() {
        deque.clear();
        deque.addFirst(new int[] {1, 1});
        int[] cur;
        int mod, cost;
        while (!deque.isEmpty()) {
            cur = deque.pollFirst();
            mod = cur[0];
            cost = cur[1];
            if (!visited[mod]) {
                visited[mod] = true;
                if (mod == 0) {
                    return cost;
                }
                deque.addFirst(new int[] {mod * 10 % k, cost});
                deque.addLast(new int[]{(mod + 1) % k, cost + 1});
            }
        }
        return -1;
    }
}
