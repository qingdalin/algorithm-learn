package leetcode.study.leetcdoe201_400;

import java.util.Arrays;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/7/27 15:38
 */
public class SummaryRanges {
    public int MAXN = 10001;
    public static int size;
    public int[] arr = new int[MAXN];
    public SummaryRanges() {
        Arrays.fill(arr, -1);
        size = 0;
    }

    public void addNum(int value) {
        arr[value] = value;
    }

    public int[][] getIntervals() {
        return null;
    }
}
