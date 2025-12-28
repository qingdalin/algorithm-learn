package algorithm.class111segmenttree02;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2024/9/4 20:03
 * // 瓶子里的花朵
 * // 给定n个瓶子，编号从0~n-1，一开始所有瓶子都是空的
 * // 每个瓶子最多插入一朵花，实现以下两种类型的操作
 * // 操作 1 from flower : 一共有flower朵花，从from位置开始依次插入花朵，已经有花的瓶子跳过
 * //                     如果一直到最后的瓶子，花也没有用完，就丢弃剩下的花朵
 * //                     返回这次操作插入的首个空瓶的位置 和 最后空瓶的位置
 * //                     如果从from开始所有瓶子都有花，打印"Can not put any one."
 * // 操作 2 left right  : 从left位置开始到right位置的瓶子，变回空瓶，返回清理花朵的数量
 * // 测试链接 : https://acm.hdu.edu.cn/showproblem.php?pid=4614,
 * // 请同学们务必参考如下代码中关于输入、输出的处理
 * // 这是输入输出处理效率很高的写法
 * // 提交以下的code，提交时请把类名改成"Main"，可以直接通过
 */
// TODO 等待测试链接账号通过可以提交
public class VasesAndFlowers {
    public static int MAXN = 50001;
    public static int n, m;
    public static int[] sum = new int[MAXN << 2];
    public static int[] change = new int[MAXN << 2];
    public static boolean[] update = new boolean[MAXN << 2];

    public static void up(int i) {
        sum[i] = sum[i << 1] + sum[i << 1 | 1];
    }

    public static void down(int i, int ln, int rn) {
        if (update[i]) {
            lazy(i << 1, change[i], ln);
            lazy(i << 1 | 1, change[i], rn);
            update[i] = false;
        }
    }

    private static void lazy(int i, int v, int n) {
        sum[i] = v * n;
        change[i] = v;
        update[i] = true;
    }

    public static void build(int l, int r, int i) {
        if (l < r) {
            int mid = (l + r) >> 1;
            build(l, mid, i << 1);
            build(mid + 1, r, i << 1 | 1);
            up(i);
        }
        sum[i] = 0;
        change[i] = 0;
        update[i] = false;
    }

    public static void update(int left, int right, int v, int l, int r, int i) {
        if (left <= l && r <= right) {
            lazy(i, v, r - l + 1);
        } else {
            int mid = (l + r) >> 1;
            down(i, mid - l + 1, r - mid);
            if (left <= mid) {
                update(left, right, v, l, mid, i << 1);
            }
            if (right > mid) {
                update(left, right, v, mid + 1, r, i << 1 | 1);
            }
            up(i);
        }
    }

    public static int query(int left, int right, int l, int r, int i) {
        if (left <= l && r <= right) {
            return sum[i];
        } else {
            int ans = 0;
            int mid = (l + r) >> 1;
            down(i, mid - l + 1, r - mid);
            if (left <= mid) {
                ans += query(left, right, l, mid, i << 1);
            }
            if (right > mid) {
                ans += query(left, right, mid + 1, r, i << 1 | 1);
            }
            return ans;
        }
    }

    public static int[] insert(int from, int flower) {
        // 线段树下标是1开始，输入下标是0
        from++;
        // 算一下从from开始到最后剩余多少个0，
        int zero = n - from + 1 - query(from, n, 1, n, 1);
        int start, end;
        if (zero == 0) {
            // 等于0说明后续都有花，返回0,0
            start = 0;
            end = 0;
        } else {
            start = findZero(from, 1);
            end = findZero(from, Math.min(zero, flower));
            update(start, end, 1, 1, n, 1);
        }
        start--;
        end--;
        return new int[] {start, end};
    }

    private static int findZero(int s, int k) {
        int l = s, r = n, m = 0;
        int ans = 0;
        while (l <= r) {
            m = (l + r) / 2;
            // m - s, s不变
            if (m - s + 1 - query(s, m, 1, n, 1) >= k) {
                ans = m;
                r = m - 1;
            } else {
                l = m + 1;
            }
        }
        return ans;
    }

    public static int clear(int left, int right) {
        left++; // 下标从0开始
        right++;
        int ans = query(left, right, 1, n, 1);
        update(left, right, 0, 1, n, 1);
        return ans;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StreamTokenizer in = new StreamTokenizer(bf);
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        int t = Integer.parseInt(bf.readLine());
        for (int j = 0; j < t; j++) {
            in.nextToken();
            n = (int) in.nval;
            in.nextToken();
            m = (int) in.nval;

            build(1, n, 1);
            for (int i = 0, op; i < m; i++) {
                in.nextToken();
                op = (int) in.nval;
                if (op == 1) {
                    in.nextToken();
                    int from = (int) in.nval;
                    in.nextToken();
                    int flower = (int) in.nval;
                    int[] ans = insert(from, flower);
                    if (ans[0] == -1) {
                        System.out.println("Can not put any one.");
                    } else {
                        System.out.println(ans[0] + " " + ans[1]);
                    }
                }else {
                    in.nextToken();
                    int left = (int) in.nval;
                    in.nextToken();
                    int right = (int) in.nval;
                    System.out.println(clear(left, right));
                }
            }
        }
        out.flush();
        out.close();
        bf.close();
    }
}
