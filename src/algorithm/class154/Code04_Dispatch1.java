package algorithm.class154;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/2/17 19:59
 * // 派遣，java版
 * // 一共有n个忍者，每个忍者有上级编号、工资、能力，三个属性
 * // 输入保证，任何忍者的上级编号 < 这名忍者的编号，1号忍者是整棵忍者树的头
 * // 你一共有m的预算，可以在忍者树上随意选一棵子树，然后在这棵子树上挑选忍者
 * // 你选择某棵子树之后，不一定要选子树头的忍者，只要不超过m的预算，可以随意选择子树上的忍者
 * // 最终收益 = 雇佣人数 * 子树头忍者的能力，返回能取得的最大收益是多少
 * // 1 <= n <= 10^5           1 <= m <= 10^9
 * // 1 <= 每个忍者工资 <= m     1 <= 每个忍者领导力 <= 10^9
 * // 测试链接 : https://www.luogu.com.cn/problem/P1552
 * // 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例
 */
public class Code04_Dispatch1 {
    public static int MAXN = 100001;
    public static int[] leader = new int[MAXN];
    public static int[] cost = new int[MAXN];
    public static int[] ability = new int[MAXN];
    public static int[] left = new int[MAXN];
    public static int[] father = new int[MAXN];
    public static int[] right = new int[MAXN];
    public static int[] dist = new int[MAXN];
    public static int[] size = new int[MAXN];
    public static long[] sum = new long[MAXN];
    public static int n, m;

    public static void prepare() {
        dist[0] = -1;
        for (int i = 1; i <= n; i++) {
            father[i] = i;
            size[i] = 1;
            sum[i] = cost[i];
            dist[i] = left[i] = right[i] = 0;
        }
    }

    public static int find(int i) {
        father[i] = father[i] == i ? i : find(father[i]);
        return father[i];
    }

    public static int merge(int i, int j) {
        if (i == 0 || j == 0) {
            return i + j;
        }
        int tmp;
        if (cost[i] < cost[j] || (cost[i] == cost[j] && j < i)) {
            tmp = i;
            i = j;
            j = tmp;
        }
        right[i] = merge(right[i], j);
        if (dist[left[i]] < dist[right[i]]) {
            tmp = left[i];
            left[i] = right[i];
            right[i] = tmp;
        }
        dist[i] = dist[right[i]] + 1;
        father[left[i]] = father[right[i]] = i;
        return i;
    }

    public static int pop(int i) {
        father[left[i]] = left[i];
        father[right[i]] = right[i];
        father[i] = merge(left[i], right[i]);
        dist[i] = left[i] = right[i] = 0;
        return father[i];
    }

    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StreamTokenizer in = new StreamTokenizer(bf);
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        in.nextToken(); n = (int) in.nval;
        in.nextToken(); m = (int) in.nval;
        for (int i = 1; i <= n; i++) {
            in.nextToken(); leader[i] = (int) in.nval;
            in.nextToken(); cost[i] = (int) in.nval;
            in.nextToken(); ability[i] = (int) in.nval;
        }
        prepare();
        out.println(compute());
        out.flush();
        out.close();
        bf.close();
    }

    private static long compute() {
        long ans = 0;
        int h, hsize, p, psize;
        long hsum, psum;
        for (int i = n; i >= 1; i--) {
            h = find(i);
            hsum = sum[h];
            hsize = size[h];
            while (hsum > m) {
                pop(h);
                hsize--;
                hsum -= cost[h];
                h = find(h);
            }
            ans = Math.max(ans, (long) hsize * ability[i]);
            if (i > 1) {
                p = find(leader[i]);
                psize = size[p];
                psum = sum[p];
                father[h] = father[p] = merge(p, h);
                sum[father[h]] = psum + hsum;
                size[father[h]] = psize + hsize;
            }
        }
        return ans;
    }
}
