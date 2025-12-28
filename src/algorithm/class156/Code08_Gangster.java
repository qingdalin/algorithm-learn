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
 * @date: 2025/2/24 19:29
 * // 团伙
 * // 注意洛谷关于本题的描述有问题，请按照如下的描述来理解题意
 * // 一共有n个黑帮成员，编号1 ~ n，发现了m条事实，每条事实一定属于如下两种类型中的一种
 * // F l r : l号成员和r号成员是朋友
 * // E l r : l号成员和r号成员是敌人
 * // 黑帮遵守如下的约定，敌人的敌人一定是朋友，朋友都来自同一个黑帮，敌人一定不是同一个黑帮
 * // 如果根据事实无法推断出一个成员有哪些朋友，那么该成员自己是一个黑帮
 * // 输入数据不存在矛盾，也就是任何两人不会推出既是朋友又是敌人的结论
 * // 遵守上面的约定，根据m条事实，计算黑帮有多少个
 * // 1 <= n <= 1000    1 <= m <= 5000
 * // 测试链接 : https://www.luogu.com.cn/problem/P1892
 * // 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例
 */
public class Code08_Gangster {
    public static int MAXN = 1001;
    public static int MAXM = 5001;
    public static int[] father = new int[MAXN];
    public static int[] enemy = new int[MAXN];
    public static int[][] arr = new int[MAXM][2];
    public static int n, m;
    public static void prepare() {
        for (int i = 0; i <= n; i++) {
            father[i] = i;
            enemy[i] = 0;
        }
    }

    public static int find(int i) {
        father[i] = father[i] == i ? i : find(father[i]);
        return father[i];
    }

    public static void union(int l, int r) {
        int lf = find(l);
        int rf = find(r);
        if (lf != rf) {
            father[lf] = rf;
        }
    }

    public static boolean same(int l, int r) {
        return find(l) == find(r);
    }

    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StreamTokenizer in = new StreamTokenizer(bf);
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        in.nextToken(); n = (int) in.nval;
        in.nextToken(); m = (int) in.nval;
        prepare();
        int l, r;
        String op;
        for (int i = 1; i <= m; i++) {
            in.nextToken(); op = in.sval;
            in.nextToken(); l = (int) in.nval;
            in.nextToken(); r = (int) in.nval;
            if (op.equals("F")) {
                union(l, r);
            } else {
                if (enemy[l] == 0) {
                    enemy[l] = r;
                } else {
                    union(enemy[l], r);
                }
                if (enemy[r] == 0) {
                    enemy[r] = l;
                } else {
                    union(l, enemy[r]);
                }
            }
        }
        int ans = 0;
        for (int i = 1; i <= n; i++) {
            if (i == father[i]) {
                ans++;
            }
        }
        out.println(ans);
        out.flush();
        out.close();
        bf.close();
    }


}
