package leetcode.leetcodeweek.test2026.test492;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2026/3/8 10:32
 * https://leetcode.cn/contest/weekly-contest-492/problems/minimum-capacity-box/description/
 */
public class Code492_01 {
    public static int minimumIndex(int[] capacity, int itemSize) {
        int n = capacity.length;
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < n; i++) {
            if (capacity[i] >= itemSize) {
                min = Math.min(min, capacity[i]);
            }
        }
        for (int i = 0; i < n; i++) {
            if (capacity[i] == min) {
                return i;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        int[] arr = {1,5,3,7};
        int itemSize = 3;
        System.out.println(minimumIndex(arr, itemSize));
    }
}
