package algorithm.class156;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/2/23 17:39
 * // 银河英雄传说
 * // 一共有30000搜战舰，编号1~30000，一开始每艘战舰各自成一队
 * // 如果若干战舰变成一队，那么队伍里的所有战舰竖直地排成一列
 * // 实现如下两种操作，操作一共调用t次
 * // M l r : 合并l号战舰所在队伍和r号战舰所在队伍
 * //         l号战舰的队伍，整体移动到，r号战舰所在队伍的最末尾战舰的后面
 * //         如果l号战舰和r号战舰已经是一队，不进行任何操作
 * // C l r : 如果l号战舰和r号战舰不在一个队伍，打印-1
 * //         如果l号战舰和r号战舰在一个队伍，打印它俩中间隔着几艘战舰
 * // 1 <= t <= 5 * 10^5
 * // 测试链接 : https://www.luogu.com.cn/problem/P1196
 * // 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例
 */
public class Code04_LegendOfHeroes {
    public static int MAXN = 30001;
    public static int n = 30000;
    public static int[] father = new int[MAXN];
    public static int[] dist = new int[MAXN];
    public static int[] size = new int[MAXN];
    public static int[] stack = new int[MAXN];

    public static void prepare() {
        for (int i = 0; i <= n; i++) {
            father[i] = i;
            dist[i] = 0;
            size[i] = 1;
        }
    }

    public static int find(int i) {
        if (i != father[i]) {
            int tmp = father[i];
            father[i] = find(tmp);
            dist[i] += dist[tmp];
        }
        return father[i];
    }

//    public static int find(int i) {
//        int si = 0;
//        while (i != father[i]) {
//            stack[++si] = i;
//            i = father[i];
//        }
//        stack[si + 1] = i;
//        for (int j = si; j >= 1; j--) {
//            father[stack[j]] = i;
//            dist[stack[j]] += dist[stack[j + 1]];
//        }
//        return i;
//    }

    public static void union(int l, int r) {
        int lf = find(l), rf = find(r);
        if (lf != rf) {
            father[lf] = rf;
            dist[lf] += size[rf];
            size[rf] += size[lf];
        }
    }

    public static int query(int l, int r) {
        if (find(l) == find(r)) {
            return Math.abs(dist[l] - dist[r]) - 1;
        }
        return -1;
    }
    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StreamTokenizer in = new StreamTokenizer(bf);
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        in.nextToken(); int m = (int) in.nval;
        prepare();
        int l, r;
        String op;
        for (int i = 1; i <= m; i++) {
            in.nextToken(); op = in.sval;
            in.nextToken(); l = (int) in.nval;
            in.nextToken(); r = (int) in.nval;
            if (op.equals("M")) {
                union(l, r);
            } else {
                out.println(query(l, r));
            }
        }
        out.flush();
        out.close();
        bf.close();
    }
}
