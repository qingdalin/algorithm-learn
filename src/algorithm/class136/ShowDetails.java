package algorithm.class136;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2024/12/1 19:52
 */
public class ShowDetails {
    public static int MAXN = 101;
    public static int BIT = 60;
    public static long[] arr = new long[MAXN];
    public static long[] basis1 = new long[BIT + 1];
    public static int n;
    public static boolean zero1;
    public static void compute1() {
        zero1 = false;
        for (int i = 1; i <= n; i++) {
            if (!insert(arr[i])) {
                zero1 = true;
            }
        }
    }

    private static boolean insert(long num) {
        for (int i = BIT; i >= 0; i--) {
            if (num >> i == 1) {
                if (basis1[i] == 0) {
                    basis1[i] = num;
                    return true;
                }
                num ^= basis1[i];
            }
        }
        return false;
    }

    public static long[] basis2 = new long[MAXN];
    public static boolean zero2;
    public static int len;

    public static void compute2() {
        len = 1;
        for (int i = BIT; i >= 0; i--) {
            for (int j = len; j <= n; j++) {
                if ((basis2[j] & (1L << i)) != 0) {
                    swap(j, len);
                    break;
                }
            }
            if ((basis2[len] & (1L << i)) != 0) {
                for (int j = 1; j <= n; j++) {
                    if (j != len && (basis2[j] & (1L << i)) != 0) {
                        basis2[j] ^= basis2[len];
                    }
                }
                len++;
            }
        }
        len--;
        zero2 = len != n;
    }

    private static void swap(int i, int j) {
        long tmp = basis2[i];
        basis2[i] = basis2[j];
        basis2[j] = tmp;
    }

    public static void main(String[] args) {
        // 课上讲的普通消元，例子1
        // 12, 9, 14, 11
        System.out.println("例子1");
        Arrays.fill(basis1, 0);
        arr[1] = 12;
        arr[2] = 9;
        arr[3] = 14;
        arr[4] = 11;
        n = 4;
        System.out.println("原始数组得到的异或结果如下");
        printXor(arr, n);

        System.out.println("===========================");
        System.out.println("普通消元得到的线性基 : ");
        compute1();
        long[] b1 = new long[MAXN];
        int s1 = 0;
        for (int i = BIT; i >= 0; i--) {
            if (basis1[i] != 0) {
                System.out.print(basis1[i] + " ");
                b1[++s1] = basis1[i];
            }
        }
        System.out.println();
        System.out.println("是否能异或出0 : " + zero1);
        System.out.println("普通消元得到的异或结果如下");
        printXor(b1, s1);
        System.out.println("===========================");

        System.out.println();
        System.out.println();

        // 课上讲的普通消元，例子2
        // 2, 5, 11, 6
        System.out.println("例子2");
        Arrays.fill(basis1, 0);
        arr[1] = 2;
        arr[2] = 5;
        arr[3] = 11;
        arr[4] = 6;
        n = 4;
        System.out.println("原始数组得到的异或结果如下");
        printXor(arr, n);
        System.out.println("===========================");
        System.out.println("普通消元得到的线性基 : ");
        compute1();
        long[] b2 = new long[MAXN];
        int s2 = 0;
        for (int i = BIT; i >= 0; i--) {
            if (basis1[i] != 0) {
                System.out.print(basis1[i] + " ");
                b2[++s2] = basis1[i];
            }
        }
        System.out.println();
        System.out.println("是否能异或出0 : " + zero1);
        System.out.println("普通消元得到的异或结果如下");
        printXor(b2, s2);
        System.out.println("===========================");
        System.out.println();
        System.out.println();

        // 课上讲的高斯消元的例子，例子3
        // 6, 37, 35, 33
        System.out.println("例子3");
        Arrays.fill(basis2, 0);
        arr[1] = basis2[1] = 6;
        arr[2] = basis2[2] = 37;
        arr[3] = basis2[3] = 35;
        arr[4] = basis2[4] = 33;
        n = 4;
        System.out.println("原始数组得到的异或结果如下");
        printXor(arr, n);
        System.out.println("===========================");
        System.out.println("高斯消元得到的线性基 : ");
        compute2();
        for (int i = 1; i <= len; i++) {
            System.out.print(basis2[i] + " ");
        }
        System.out.println();
        System.out.println("是否能异或出0 : " + zero2);
        System.out.println("高斯消元得到的异或结果如下");
        printXor(basis2, len);
        System.out.println("===========================");

        System.out.println();
        System.out.println();
    }

    private static void printXor(long[] nums, int n) {
        Set<Long> set = new HashSet<>();
        dfs(nums, n, 1, 0, false, set);
        for (Long s : set) {
            System.out.print(s + " ");
        }
        System.out.println();
    }

    // 当前来到i位置
    // path之前已经选择的异或结果
    // pick是否选择数字
    // 当前i位置的数字要或者不要全决策
    // 收集所有可能的异或和
    private static void dfs(long[] nums, int n, int i, long path, boolean pick, Set<Long> set) {
        if (i > n) {
            if (pick) {
                set.add(path);
            }
        } else {
            dfs(nums, n, i + 1, path, pick, set);
            dfs(nums, n, i + 1, path ^ nums[i], true, set);
        }
    }
}
