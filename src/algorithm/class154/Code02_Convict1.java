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
 * @date: 2025/2/16 16:55
 * // 断罪者，删除任意编号节点，java版
 * // 给定t，w，k，表示一共有t个人，死亡方式都为w，地狱阈值都为k，w和k含义稍后解释
 * // 每个人都给定n和m，表示这人一生有n件错事，有m次领悟
 * // 这个人的n件错事，给定对应的n个罪恶值，然后给定m次领悟，领悟类型如下
 * // 2 a   : 第a件错事的罪恶值变成0
 * // 3 a b : 第a件错事所在的集合中，最大罪恶值的错事，罪恶值减少b
 * //         如果减少后罪恶值变成负数，认为这件错事的罪恶值变为0
 * //         如果集合中，两件错事都是最大的罪恶值，取编号较小的错事
 * // 4 a b : 第a件错事所在的集合与第b件错事所在的集合合并
 * // 一个错事集合的罪恶值 = 这个集合中的最大罪恶值，只取一个
 * // 一个人的罪恶值 = 这个人所有错事集合的罪恶值累加起来
 * // 然后根据死亡方式w，对每个人的罪恶值做最后调整，然后打印这个人的下场
 * // 如果w==1，不调整
 * // 如果w==2，人的罪恶值 -= 错事集合的罪恶值中的最大值
 * // 如果w==3，人的罪恶值 += 错事集合的罪恶值中的最大值
 * // 如果一个人的罪恶值 == 0，打印"Gensokyo 0"
 * // 如果一个人的罪恶值  > k，打印"Hell "，然后打印罪恶值
 * // 如果一个人的罪恶值 <= k，打印"Heaven "，然后打印罪恶值
 * // 一共有t个人，所以最终会有t次打印
 * // 1 <= t <= 30
 * // 1 <= n <= 2 * 10^6
 * // 错事罪恶值可能很大，输入保证每个人的罪恶值用long类型不溢出
 * // 测试链接 : https://www.luogu.com.cn/problem/P4971
 * // 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例
 */
public class Code02_Convict1 {
    public static int MAXN = 2000001;
    public static int t, w, n, m;
    public static long k;
    public static long[] num = new long[MAXN];
    public static int[] left = new int[MAXN];
    public static int[] right = new int[MAXN];
    public static int[] dist = new int[MAXN];
    public static int[] father = new int[MAXN];
    public static int[] up = new int[MAXN];

    public static void prepare() {
        dist[0] = -1;
        for (int i = 1; i <= n; i++) {
            dist[i] = left[i] = right[i] = up[i] = 0;
            father[i] = i;
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
        // 维护大根堆，如果值一样，编号小的节点做头
        if (num[i] < num[j] || (num[i] == num[j] && i > j)) {
            int tmp = i;
            i = j;
            j = tmp;
        }
        right[i] = merge(right[i], j);
        up[right[i]] = i;
        if (dist[left[i]] < dist[right[i]]) {
            int tmp = left[i];
            left[i] = right[i];
            right[i] = tmp;
        }
        dist[i] = dist[right[i]] + 1;
        father[left[i]] = father[right[i]] = i;
        return i;
    }

    public static int remove(int i) {
        int h = find(i);
        father[left[i]] = left[i];
        father[right[i]] = right[i];
        int s = merge(left[i], right[i]);
        int f = up[i];
        father[i] = s;
        up[s] = f;
        if (h != i) {
            father[s] = h;
            if (left[f] == i) {
                left[f] = s;
            } else {
                right[f] = s;
            }
            for (int d = dist[s], tmp; dist[f] > d + 1; f = up[f], d++) {
                dist[f] = d + 1;
                if (dist[left[f]] < dist[right[f]]) {
                    tmp = left[f];
                    left[f] = right[f];
                    right[f] = tmp;
                }
            }
        }
        dist[i] = left[i] = right[i] = up[i] = 0;
        return father[s];
    }

    public static void reduce(int i, long v) {
        int h = remove(i);
        num[i] = Math.max(num[i] - v, 0);
        father[h] = father[i] = merge(h, i);
    }

    public static long compute() {
        long ans = 0;
        long max = 0;
        for (int i = 1; i <= n; i++) {
            if (father[i] == i) {
                ans += num[i];
                max = Math.max(max, num[i]);
            }
        }
        if (w == 2) {
            ans -= max;
        } else if (w == 3) {
            ans += max;
        }
        return ans;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StreamTokenizer in = new StreamTokenizer(bf);
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        in.nextToken(); t = (int) in.nval;
        in.nextToken(); w = (int) in.nval;
        in.nextToken(); k = (int) in.nval;
        for (int test = 0; test < t; test++) {
            in.nextToken(); n = (int) in.nval;
            in.nextToken(); m = (int) in.nval;
            prepare();
            for (int i = 1; i <= n; i++) {
                in.nextToken(); // 不能少
                num[i] = (long) in.nval;
            }
            for (int i = 1, op, a, b; i <= m; i++) {
                in.nextToken(); op = (int) in.nval;
                in.nextToken(); a = (int) in.nval;
                if (op == 2) {
                    reduce(a, num[a]);
                } else if (op == 3) {
                    in.nextToken(); b = (int) in.nval;
                    reduce(find(a), b);
                } else {
                    in.nextToken(); b = (int) in.nval;
                    int l = find(a);
                    int r = find(b);
                    if (l != r) {
                        merge(l, r);
                    }
                }
            }
            long ans = compute();
            if (ans == 0) {
                out.println("Gensokyo " + ans);
            } else if (ans > k) {
                out.println("Hell " + ans);
            } else {
                out.println("Heaven " + ans);
            }
        }
        out.flush();
        out.close();
        bf.close();
    }
}
