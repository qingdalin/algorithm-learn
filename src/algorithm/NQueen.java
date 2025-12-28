package algorithm;

/**
 * @author 汪大鹏
 * @version 1.0
 * @date 2024-01-24 19:23
 */
public class NQueen {
    public static void main(String[] args) {
        // test();
        long start, end;
        start = System.currentTimeMillis();
        System.out.println("=========位运算方式开始=========" + start);
        int sum = NQueens(15);
        end = System.currentTimeMillis();
        System.out.println("=========位运算方式开始,一共有" + sum + "方案=========耗时：" + (end - start));
    }

    private static void test() {
        for (int n = 0; n < 15; n++) {
            System.out.println(n+ "：皇后问题-----------------------");
            long start, end;
            start = System.currentTimeMillis();
            System.out.println("=========普通递归方式执行开始=========" + start);
            int totalNQueens = totalNQueens(n);
            end = System.currentTimeMillis();
            System.out.println("=========普通递归方式执行结束,一共有" + totalNQueens + "方案=========耗时：" + (end - start));
            System.out.println("=======================================");
            start = System.currentTimeMillis();
            System.out.println("=========位运算方式开始=========" + start);
            int sum = NQueens(n);
            end = System.currentTimeMillis();
            System.out.println("=========位运算方式开始,一共有" + sum + "方案=========耗时：" + (end - start));
        }
    }

    public static int NQueens(int n) {
        int limit = (1 << n) - 1;
        return f2(limit, 0, 0, 0);
    }

    private static int f2(int limit, int col, int left, int right) {
        if (limit == col) {
            return 1;
        } else {
            int ban = col | left | right;
            int candidate = limit & (~ban);
            int place = 0;
            int ans = 0;
            while (candidate != 0) {
                place = candidate & (-candidate);
                candidate ^= place;
                ans += f2(limit, col | place, (left | place) << 1, (right | place) >> 1);
            }
            return ans;
        }
    }


    public static int totalNQueens(int n) {
        int[] path = new int[n];
        return f(0, path, n);
    }
    public static int f(int i, int[] path, int n) {
        if (i == n) {
            return 1;
        } else {
            int ans = 0;
            for (int j = 0; j < n; j++) {
                if (check(i, j, path)) {
                    path[i] = j;
                    ans += f(i + 1, path, n);
                }
            }
            return ans;
        }
    }

    private static boolean check(int i, int j, int[] path) {
        for (int k = 0; k < i; k++) {
            if (j == path[k] || Math.abs(i - k) == Math.abs(j - path[k])) {
                return false;
            }
        }
        return true;
    }
}
