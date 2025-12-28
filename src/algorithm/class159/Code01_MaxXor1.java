package algorithm.class159;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/3/3 21:11
 * // 最大异或和，java版
 * // 非负序列arr的初始长度为n，一共有m条操作，每条操作是如下两种类型中的一种
 * // A x     : arr的末尾增加数字x，arr的长度n也增加1
 * // Q l r x : l~r这些位置中，选一个位置p，现在希望
 * //           arr[p] ^ arr[p+1] ^ .. ^ arr[n] ^ x 这个值最大
 * //           打印这个最大值
 * // 1 <= n、m <= 3 * 10^5
 * // 0 <= arr[i]、x <= 10^7
 * // 因为练的就是可持久化前缀树，所以就用在线算法，不要使用离线算法
 * // 测试链接 : https://www.luogu.com.cn/problem/P4735
 * // 提交以下的code，提交时请把类名改成"Main"
 * // java实现的逻辑一定是正确的，但是有一些测试用例通过不了
 * // 因为这道题根据C++的运行时间，制定通过标准，根本没考虑java的用户
 * // 想通过用C++实现，本节课Code01_MaxXor2文件就是C++的实现
 * // 两个版本的逻辑完全一样，C++版本可以通过所有测试
 */
public class Code01_MaxXor1 {
    public static int MAXN = 500001;
    public static int MAXT = MAXN * 22;
    public static int BIT = 25;
    public static int n, m, eor;
    public static int[] root = new int[MAXN];
    public static int[][] tree = new int[MAXT][2];
    public static int[] pass = new int[MAXT];
    public static int cnt = 0;

    public static int insert(int num, int i) {
        int rt = ++cnt;
        tree[rt][0] = tree[i][0];
        tree[rt][1] = tree[i][1];
        pass[rt] = pass[i] + 1;
        for (int b = BIT, pre = rt, cur, path; b >= 0; b--, pre = cur) {
            path = (num >> b) & 1;
            i = tree[i][path];
            cur = ++cnt;
            tree[cur][0] = tree[i][0];
            tree[cur][1] = tree[i][1];
            pass[cur] = pass[i] + 1;
            tree[pre][path] = cur;
        }
        return rt;
    }

    public static int query(int num, int u, int v) {
        int ans = 0;
        for (int b = BIT, path, best; b >= 0; b--) {
            path = (num >> b) & 1;
            best = path ^ 1;
            if (pass[tree[v][best]] > pass[tree[u][best]]) {
                ans += 1 << b;
                u = tree[u][best];
                v = tree[v][best];
            } else {
                u = tree[u][path];
                v = tree[v][path];
            }
        }
        return ans;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StreamTokenizer in = new StreamTokenizer(bf);
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        in.nextToken(); n = (int) in.nval;
        in.nextToken(); m = (int) in.nval;
        eor = 0;
        root[0] = insert(eor, 0);
        for (int i = 1, num; i <= n; i++) {
            in.nextToken(); num = (int) in.nval;
            eor ^= num;
            root[i] = insert(eor, root[i - 1]);
        }
        String op;
        for (int i = 1, x, y, z; i <= m; i++) {
            in.nextToken(); op = in.sval;
            if (op.equals("A")) {
                in.nextToken(); x = (int) in.nval;
                eor ^= x;
                n++;
                root[n] = insert(eor, root[n - 1]);
            } else {
                in.nextToken(); x = (int) in.nval;
                in.nextToken(); y = (int) in.nval;
                in.nextToken(); z = (int) in.nval;
                if (x == 1) {
                    out.println(query(eor ^ z, 0, root[y - 1]));
                } else {
                    out.println(query(eor ^ z, root[x - 2], root[y - 1]));
                }
            }
        }
        out.flush();
        out.close();
        bf.close();
    }
}
