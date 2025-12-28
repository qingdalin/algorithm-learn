package algorithm;

/**
 * @author 汪大鹏
 * @version 1.0
 * @date 2024-06-01 16:35
 */
public class ArrayUtil {
    /**
     * 返回一个随机数组，元素值从 -v -- +v
     *
     * @param n 数组长度
     * @param v 数组的每个值范围
     * @return 随机数组
     */
    public static int[] randomPositiveAndNegativeNumArray(int n, int v) {
        int[] ans = new int[n];
        for (int i = 0; i < n; i++) {
            ans[i] = (int) (Math.random() * (2 * v +1) - v);
        }
        return ans;
    }

    /**
     * 返回一个随机数组，元素值从 -v -- +v
     *
     * @param n 数组长度
     * @param v 数组的每个值范围
     * @return 随机数组
     */
    public static int[] randomPositiveNumArray(int n, int v) {
        int[] ans = new int[n];
        for (int i = 0; i < n; i++) {
            ans[i] = (int) (Math.random() * v + 1);
        }
        return ans;
    }

    /**
     * 交换二维数组两个元素
     * @param arr 当前交换的数组
     * @param i 交换的位置i
     * @param j 交换的位置j
     */
    public static void swap(int[][] arr, int i, int j) {
        int[] tem = arr[i];
        arr[i] = arr[j];
        arr[j] = tem;
    }

    /**
     * 随机生成二维数组
     *
     * @param n 数组长度
     * @param v 元素取值范围
     * @return 生成后的数组
     */
    public static int[][] randomTwoDimensionalArray(int n, int v) {
        int[][] ans = new int[n][2];
        for (int i = 0; i < n; i++) {
            ans[i][0] = (int) (Math.random() * v + 1);
            ans[i][1] = (int) (Math.random() * v + 1);
        }
        return ans;
    }
}
