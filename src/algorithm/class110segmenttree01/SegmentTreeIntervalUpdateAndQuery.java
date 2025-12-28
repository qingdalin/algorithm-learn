package algorithm.class110segmenttree01;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2024/8/31 20:08
 * 对数器验证，范围内重置为某个树，范围查询
 */
public class SegmentTreeIntervalUpdateAndQuery {
    public static int MAXN = 100001;

    public static long[] arr =  new long[MAXN];
    public static long[] sum =  new long[MAXN << 2];
    // i号下标重置的数字
    public static long[] change =  new long[MAXN << 2];
    // 维护是否有重置信息
    public static boolean[] update =  new boolean[MAXN << 2];

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

    private static void lazy(int i, long v, int n) {
        sum[i] = v * n;
        change[i] = v;
        update[i] = true;
    }

    public static void build(int l, int r, int i) {
        if (l == r) {
            sum[i] = arr[l];
        } else {
            int mid = (l + r) >> 1;
            build(l, mid, i << 1);
            build(mid + 1, r, i << 1 | 1);
            up(i);
        }
        sum[i] = 0;
        change[i] = 0;
        update[i] = false;
    }

    public static void update(int jobl, int jobr, int jobv, int l, int r, int i) {
        if (jobl <= l && r <= jobr) {
            lazy(i, jobv, r - l + 1);
        } else {
            int mid = (l + r) >> 1;
            down(i, mid - l + 1, r - mid);
            if (jobl <= mid) {
                update(jobl, jobr, jobv, l, mid, i << 1);
            }
            if (jobr > mid) {
                update(jobl, jobr, jobv, mid + 1, r, i << 1 | 1);
            }
            up(i);
        }
    }

    public static long query(int jobl, int jobr, int l, int r, int i) {
        if (jobl <= l && r <= jobr) {
            return sum[i];
        }
        long ans = 0;
        int mid = (l + r) >> 1;
        down(i, mid - l + 1, r - mid);
        if (jobl <= mid) {
            ans += query(jobl, jobr, l, mid, i << 1);
        }
        if (jobr > mid) {
            ans += query(jobl, jobr, mid + 1, r, i << 1 | 1);
        }
        return ans;
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
                update(jobl, jobr, jobv, 1, n, 1);
                checkUpdate(jobl, jobr, jobv, check);
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
        long ans = 0;
        for (int i = jobl; i <= jobr; i++) {
            ans += check[i];
        }
        return ans;
    }

    private static void checkUpdate(int jobl, int jobr, int jobv, long[] check) {
        for (int i = jobl; i <= jobr; i++) {
            check[i] = jobv;
        }
    }

    private static void randomArray(int n, int v) {
        for (int i = 1; i <= n; i++) {
            arr[i] = (long) (Math.random() * v);
        }
    }
}
