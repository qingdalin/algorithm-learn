package algorithm.class114segmenttree05;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2024/9/15 9:02
 * 对数器验证
 * // 线段树的区间最值操作
 * // 给定一个长度为n的数组arr，实现支持以下三种操作的结构
 * // 操作 0 l r x : 把arr[l..r]范围的每个数v，更新成min(v, x)
 * // 操作 1 l r   : 查询arr[l..r]范围上的最大值
 * // 操作 2 l r   : 查询arr[l..r]范围上的累加和
 * // 三种操作一共调用m次，做到时间复杂度O(n * log n + m * log n)
 */
public class Code03_SegmentTreeSetMinQueryMaxSum1 {
    public static int MAXN = 100001;
    // 比题目给定的范围小的值
    public static int LOWEST = -100001;
    public static int[] arr = new int[MAXN];
    // 求和
    public static long[] sum = new long[MAXN << 2];
    // 最大值,既是查询信息，也是懒更新信息
    public static int[] max = new int[MAXN << 2];
    // second max 次大值，严格小于最大值
    public static int[] sem = new int[MAXN << 2];
    // 最大值个数
    public static int[] cnt = new int[MAXN << 2];

    public static void up(int i) {
        int l = i << 1;
        int r = i << 1 | 1;
        sum[i] = sum[l] + sum[r];
        max[i] = Math.max(max[l], max[r]);
        if (max[l] > max[r]) {
            // pk出最大值个数和次大值
            cnt[i] = cnt[l];
            sem[i] = Math.max(sem[l], max[r]);
        } else if (max[l] < max[r]) {
            cnt[i] = cnt[r];
            sem[i] = Math.max(max[l], sem[r]);
        } else {
            cnt[i] = cnt[l] + cnt[r];
            sem[i] = Math.max(sem[l], sem[r]);
        }
    }

    public static void down(int i) {
        lazy(i << 1, max[i]);
        lazy(i << 1 | 1, max[i]);
    }

    private static void lazy(int i, int v) {
        if (v < max[i]) {
            sum[i] -= ((long) max[i] - v) * cnt[i];
            max[i] = v;
        }
    }

    public static void build(int l, int r, int i) {
        if (l == r) {
            sum[i] = max[i] = arr[l];
            cnt[i] = 1;
            sem[i] = LOWEST;
        } else {
            int mid = (l + r) >> 1;
            build(l, mid, i << 1);
            build(mid + 1, r, i << 1 | 1);
            up(i);
        }
    }

    public static void setMin(int jobl, int jobr, int jobv, int l, int r, int i) {
        if (jobv >= max[i]) {
            // 更新的值比最大值大，直接剪枝
            return;
        }
        if (jobl <= l && r <= jobr && sem[i] < jobv) {
            // 全包并且大于次大值
            lazy(i, jobv);
        } else {
            // 到这说明下列两种情况
            // 1.没有全包
            // 2.小于等于次大值
            int mid = (l + r) >> 1;
            down(i);
            if (jobl <= mid) {
                setMin(jobl, jobr, jobv, l, mid, i << 1);
            }
            if (jobr > mid) {
                setMin(jobl, jobr, jobv, mid + 1, r, i << 1 | 1);
            }
            up(i);
        }
    }

    public static int queryMax(int jobl, int jobr, int l, int r, int i) {
        if (jobl <= l && r <= jobr) {
            return max[i];
        }
        int mid = (l + r) >> 1;
        down(i);
        int ans = Integer.MIN_VALUE;
        if (jobl <= mid) {
            ans = Math.max(ans, queryMax(jobl, jobr, l, mid, i << 1));
        }
        if (jobr > mid) {
            ans = Math.max(ans, queryMax(jobl, jobr, mid + 1, r, i << 1 | 1));
        }
        return ans;
    }

    public static long querySum(int jobl, int jobr, int l, int r, int i) {
        if (jobl <= l && r <= jobr) {
            return sum[i];
        }
        int mid = (l + r) >> 1;
        down(i);
        long ans = 0;
        if (jobl <= mid) {
            ans += querySum(jobl, jobr, l, mid, i << 1);
        }
        if (jobr > mid) {
            ans += queryMax(jobl, jobr, mid + 1, r, i << 1 | 1);
        }
        return ans;
    }

    public static void main(String[] args) {
        System.out.println("测试开始");
        int n = 2000;
        int v = 5000;
        int t = 1000000;
        randomArray(n, v);
        int[] check = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            check[i] = arr[i];
        }
        build(1, n, 1);
        for (int i = 0, op, jobl, jobr, a, b, jobv; i < t; i++) {
            op = (int) (Math.random() * 3);
            a = (int) (Math.random() * n) + 1;
            b = (int) (Math.random() * n) + 1;
            jobl = Math.min(a, b);
            jobr = Math.max(a, b);
            if (op == 0) {
                jobv = (int) (Math.random() * v * 2) - v;
                setMin(jobl, jobr, jobv, 1, n, 1);
                checkMin(check, jobl, jobr, jobv);
            } else if (op == 1) {
                int ans1 = queryMax(jobl, jobr, 1, n, 1);
                int ans2 = checkMax(check, jobl, jobr);
                if (ans1 != ans2) {
                    System.out.println("出错了");
                }
            } else {
                long ans1 = querySum(jobl, jobr, 1, n, 1);
                long ans2 = checkSum(check, jobl, jobr);
                if (ans1 != ans2) {
                    System.out.println("出错了");
                }
            }
        }
        System.out.println("测试结束");
    }

    private static long checkSum(int[] check, int jobl, int jobr) {
        long ans = 0;
        for (int i = jobl; i <= jobr; i++) {
            ans += check[i];
        }
        return ans;
    }

    private static int checkMax(int[] check, int jobl, int jobr) {
        int ans = Integer.MIN_VALUE;
        for (int i = jobl; i <= jobr; i++) {
            ans = Math.max(ans, check[i]);
        }
        return ans;
    }

    private static void checkMin(int[] check, int jobl, int jobr, int jobv) {
        for (int i = jobl; i <= jobr; i++) {
            check[i] = Math.min(check[i], jobv);
        }
    }

    private static void randomArray(int n, int v) {
        for (int i = 1; i <= n; i++) {
            arr[i] = (int) (Math.random() * v * 2) - v;
        }
    }
}
