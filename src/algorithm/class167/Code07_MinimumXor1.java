package algorithm.class167;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/5/11 8:44
 * // 最小异或查询，java版
 * // 一共有q条操作，每种操作是如下三种类型中的一种
 * // 操作 1 x : 黑板上写上一个数字x，同一种数字可以出现多次
 * // 操作 2 x : 将一个x从黑板上擦掉，操作时保证至少有一个x在黑板上
 * // 操作 3   : 打印黑板上任意两数的最小异或值，操作时保证黑板上至少有两个数
 * // 1 <= q <= 3 * 10^5
 * // 0 <= x <= 2^30
 * // 测试链接 : https://www.luogu.com.cn/problem/AT_abc308_g
 * // 测试链接 : https://atcoder.jp/contests/abc308/tasks/abc308_g
 * // 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例
 */
public class Code07_MinimumXor1 {
    public static int MAXN = 10000001;
    public static int BIT = 29;
    public static int INF = 1 << 30;
    public static int q;
    public static int[] fa = new int[MAXN];
    public static int[][] tree = new int[MAXN][2];
    public static int[] pass = new int[MAXN];
    public static int cnt = 1;
    public static int[] mineor = new int[MAXN];
    public static int[] only = new int[MAXN];

    public static int change(int x, int changeCnt) {
        int cur = 1;
        pass[cur] += changeCnt;
        // 错误写法 for(int b = BIT, path; b > 0; b--) {
        for(int b = BIT, path; b >= 0; b--) {
            path = (x >> b) & 1;
            if (tree[cur][path] == 0) {
                tree[cur][path] = ++cnt;
                fa[tree[cur][path]] = cur;
            }
            cur = tree[cur][path];
            pass[cur] += changeCnt;
        }
        return cur;
    }

    public static void compute(int x, int changeCnt) {
        int bottom = change(x, changeCnt);
        mineor[bottom] = pass[bottom] >= 2 ? 0 : INF;
        only[bottom] = pass[bottom] == 1 ? x : 0;
        for(int i = fa[bottom], l, r; i > 0; i = fa[i]) {
            l = tree[i][0];
            r = tree[i][1];
            if (pass[i] < 2) {
                mineor[i] = INF;
            } else if (pass[l] == 1 && pass[r] == 1) {
                mineor[i] = only[l] ^ only[r];
            } else if (pass[l] == 0 ^ pass[r] == 0) {
                mineor[i] = pass[l] == 0 ? mineor[r] : mineor[l];
            } else {
                mineor[i] = Math.min(mineor[l], mineor[r]);
            }
            if (pass[l] + pass[r] == 1) {
                only[i] = pass[l] == 1 ? only[l] : only[r];
            } else {
                only[i] = 0;
            }
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StreamTokenizer in = new StreamTokenizer(bf);
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        in.nextToken(); q = (int) in.nval;
        for (int i = 1, op, x; i <= q; i++) {
            in.nextToken(); op = (int) in.nval;
            if (op == 3) {
                out.println(mineor[1]);
            } else {
                in.nextToken(); x = (int) in.nval;
                if (op == 1) {
                    compute(x, 1);
                } else {
                    compute(x, -1);
                }
            }
        }
        out.flush();
        out.close();
        bf.close();
    }
}
