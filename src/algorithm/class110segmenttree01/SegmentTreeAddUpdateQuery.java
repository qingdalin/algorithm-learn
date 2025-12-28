package algorithm.class110segmenttree01;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2024/9/01 09:09
 */
public class SegmentTreeAddUpdateQuery {
    public static int MAXN = 100001;
    public static long[] arr = new long[MAXN];
    public static long[] sum = new long[MAXN << 2];
    public static long[] add = new long[MAXN << 2];
    public static long[] change = new long[MAXN << 2];
    public static boolean[] update = new boolean[MAXN << 2];

    private static void up(int i) {
        sum[i] = sum[i << 1] + sum[i << 1 | 1];
    }

    private static void down(int i, int ln, int rn) {
        if (update[i]) {
            updateLazy(i << 1, change[i], ln);
            updateLazy(i << 1 | 1, change[i], rn);
            update[i] = false;
        }
        if (add[i] != 0) {
            addLazy(i << 1, add[i], ln);
            addLazy(i << 1 | 1, add[i], rn);
            add[i] = 0;
        }
    }

    private static void updateLazy(int i, long v, int n) {
        sum[i] = v * n;
        add[i] = 0;
        change[i] = v;
        update[i] = true;
    }

    // 当前来到l~r范围，对应的信息下标是i，范围上数字的个数是n = r-l+1
    // 现在收到一个懒更新任务 : l~r范围上每个数字增加v
    // 这个懒更新任务有可能是任务范围把当前线段树范围全覆盖导致的
    // 也有可能是父范围的懒信息下发下来的
    // 总之把线段树当前范围的sum数组和add数组调整好
    // 就不再继续往下下发了，懒住了
    public static void addLazy(int i, long v, int n) {
        sum[i] += v * n;
        add[i] += v;
    }

    public static void build(int l, int r, int i) {
        if (l == r) {
            sum[i] = arr[l];
        } else {
            int mid = (l + r) >> 1;
            // i * 2
            build(l, mid, i << 1);
            // i * 2 + 1
            build(mid + 1, r, i << 1 | 1);
            up(i);
        }
        add[i] = 0;
        change[i] = 0;
        update[i] = false;
    }

    public static void update(int jobl, int jobr, long jobv, int l, int r, int i) {
        if (jobl <= l && r <= jobr) {
            // 全部包裹，懒更新
            updateLazy(i, jobv, r - l + 1);
        } else {
            int mid = (l + r) >> 1;
            down(i, mid - l + 1, r - mid);
            if (jobl <= mid) {
                add(jobl, jobr, jobv, l, mid, i << 1);
            }
            if (jobr > mid) {
                add(jobl, jobr, jobv, mid + 1, r, i << 1 | 1);
            }
            up(i);
        }
    }

    public static void add(int jobl, int jobr, long jobv, int l, int r, int i) {
        if (jobl <= l && r <= jobr) {
            // 全部包裹，懒更新
            addLazy(i, jobv, r - l + 1);
        } else {
            int mid = (l + r) >> 1;
            down(i, mid - l + 1, r - mid);
            if (jobl <= mid) {
                add(jobl, jobr, jobv, l, mid, i << 1);
            }
            if (jobr > mid) {
                add(jobl, jobr, jobv, mid + 1, r, i << 1 | 1);
            }
            up(i);
        }
    }


    /**
     * 线段树范围查询jobl到jobr的和
     *
      * @param jobl 任务的左侧边界
     * @param jobr 任务的右侧边界
     * @param l 线段树当前左边界
     * @param r 线段树当前右边界
     * @param i sum数组的位置
     * @return 总和
     */
    public static long query(int jobl, int jobr, int l, int r, int i) {
        if (jobl <= l && r <= jobr) {
            // 当前l-r范围被任务包裹，直接返回
            return sum[i];
        }
        int mid = (l + r) >> 1;
        // 任务  线段树中点mid=4
        // 懒更新, l - mid的个数，mid + 1到r的个数
        down(i, mid - l + 1, r - mid);
        long ans = 0;
        // 2-3, 1-4 5-8
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
            // op 为0添加
            // op 为1更新
            // op 为2查询
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
            } else if (op == 1){
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

    private static void checkAdd(int jobl, int jobr, int jobv, long[] check) {
        for (int i = jobl; i <= jobr; i++) {
            check[i] += jobv;
        }
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
