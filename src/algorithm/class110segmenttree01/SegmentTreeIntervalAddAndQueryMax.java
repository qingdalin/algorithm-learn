package algorithm.class110segmenttree01;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2024/8/31 21:21
 * 范围更新，维护最大值
 */
public class SegmentTreeIntervalAddAndQueryMax {
    public static int MAXN = 100001;

    public static long[] arr = new long[MAXN];
    public static long[] max = new long[MAXN << 2];
    public static long[] add = new long[MAXN << 2];

    public static void up(int i) {
        max[i] = Math.max(max[i << 1], max[i << 1 | 1]);
    }

    public static void down(int i) {
        if (add[i] != 0) {
            lazy(i << 1, add[i]);
            lazy(i << 1 | 1, add[i]);
            add[i] = 0;
        }
    }

    private static void lazy(int i, long v) {
        max[i] += v;
        add[i] += v;
    }

    public static void build(int l, int r, int i) {
        if (l == r) {
            max[i] = arr[l];
        } else {
            int mid = (l + r) >> 1;
            build(l, mid, i << 1);
            build(mid + 1, r, i << 1 | 1);
            up(i);
        }
        add[i] = 0;
    }

    public static void add(int jobl, int jobr, int jobv, int l, int r, int i) {
        if (jobl <= l && r <= jobr) {
            lazy(i, jobv);
        } else {
            int mid = (l + r) >> 1;
            down(i);
            if (jobl <= mid) {
                add(jobl, jobr, jobv, l, mid, i << 1);
            }
            if (jobr > mid) {
                add(jobl, jobr, jobv, mid + 1, r, i << 1 | 1);
            }
            up(i);
        }
    }

    public static long query(int jobl, int jobr, int l, int r, int i) {
        if (jobl <= l && r <= jobr) {
            return max[i];
        } else {
            int mid = (l + r) >> 1;
            down(i);
            long ans = Long.MIN_VALUE;
            if (jobl <= mid) {
                ans = Math.max(ans, query(jobl, jobr, l, mid, i << 1));
            }
            if (jobr > mid) {
                ans = Math.max(ans, query(jobl, jobr, mid + 1, r, i << 1 | 1));
            }
            return ans;
        }
    }

    public static void main(String[] args) {
        int n = 1000;
        int v = 2000;
        int t = 5000000;
        randomArray(n, v);
        long[] check = new long[n + 1];
        for (int i = 1; i < check.length; i++) {
            check[i] = arr[i];
        }
        build(1, n, 1);
        System.out.println("测试开始");
        for (int i = 0; i < t; i++) {
            // op 为0更新
            // op 为1查询
            int op = (int) (Math.random() * 2);
            //System.out.println(op);
            // 下标必须从1开始
            int a = (int) (Math.random() * n) + 1;
            int b = (int) (Math.random() * n) + 1;
            int jobl = Math.min(a, b);
            int jobr = Math.max(a, b);
            if (op == 0) {
                int jobv = (int) (Math.random() * v * 2) - v;
                add(jobl, jobr, jobv, 1, n, 1);
                checkAdd(jobl, jobr, jobv, check);
            } else {
                long ans1 = query(jobl, jobr, 1, n, 1);
                long ans2 = checkQuery(jobl, jobr, check);
                if (ans1 != ans2) {
                    System.out.println("出错了");
                }
            }

        }
        System.out.println("测试结束");
    }

    private static long checkQuery(int jobl, int jobr, long[] check) {
        long ans = Long.MIN_VALUE;
        for (int i = jobl; i <= jobr; i++) {
            ans = Math.max(ans, check[i]);
        }
        return ans;
    }

    private static void checkAdd(int jobl, int jobr, int jobv, long[] check) {
        for (int i = jobl; i <= jobr; i++) {
            check[i] += jobv;
        }
    }

    private static void randomArray(int n, int v) {
        for (int i = 1; i <= n; i++) {
            arr[i] = (long) (Math.random() * v);
        }
    }
}
