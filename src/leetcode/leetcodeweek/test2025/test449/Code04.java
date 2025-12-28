package leetcode.leetcodeweek.test2025.test449;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/5/11 9:50
 * https://leetcode.cn/contest/weekly-contest-449/problems/equal-sum-grid-partition-ii/
 */
public class Code04 {
    public static boolean canPartitionGrid(int[][] grid) {
        int n = grid.length;
        int m = grid[0].length;
        // Map<String, Set<Integer>> map = new HashMap<>();
        Map<Integer, Set<Integer>> colMap = getColMap(grid, n, m);
        Map<Integer, Set<Integer>> rowMap = getRowMap(grid, n, m);
        getSum(grid, n, m);
        int a, b;
        for (int j = 0; j < m; j++) {
            a = grid[n - 1][j];
            b = grid[n - 1][m - 1] - a;
            if (a == b) {
                return true;
            } else if (a < b) {
                // 从b中一出
                int diff = b - a;
                for (int col = j + 1; col < m; col++) {
                    Set<Integer> set = colMap.get(col);
                    if (set.contains(diff)) {
                        return true;
                    }
                }
            } else {
                int diff = a - b;
                for (int col = 0; col < j; col++) {
                    Set<Integer> set = colMap.get(col);
                    if (set.contains(diff)) {
                        return true;
                    }
                }
            }
        }
        for (int i = 0; i < n; i++) {
            a = grid[i][m - 1];
            b = grid[n - 1][m - 1] - a;
            if (a == b) {
                return true;
            } else if (a < b) {
                // 从b中一出
                int diff = b - a;
                for (int row = i + 1; row < n; row++) {
                    Set<Integer> set = rowMap.get(row);
                    if (set.contains(diff)) {
                        return true;
                    }
                }
            } else {
                int diff = a - b;
                for (int row = 0; row < i; row++) {
                    Set<Integer> set = rowMap.get(row);
                    if (set.contains(diff)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private static Map<Integer, Set<Integer>> getColMap(int[][] grid, int n, int m) {
        Map<Integer, Set<Integer>> colMap = new HashMap<>();
        for (int j = 0; j < m; j++) {
            for (int i = 0; i < n; i++) {
                if (colMap.containsKey(j)) {
                    colMap.get(j).add(grid[i][j]);
                } else {
                    Set<Integer> set = new HashSet<>();
                    set.add(grid[i][j]);
                    colMap.put(j, set);
                }
            }
        }
        return colMap;
    }

    private static Map<Integer, Set<Integer>> getRowMap(int[][] grid, int n, int m) {
        Map<Integer, Set<Integer>> rowMap = new HashMap<>();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (rowMap.containsKey(i)) {
                    rowMap.get(i).add(grid[i][j]);
                } else {
                    Set<Integer> set = new HashSet<>();
                    set.add(grid[i][j]);
                    rowMap.put(i, set);
                }
            }
        }
        return rowMap;
    }

    private static void getSum(int[][] grid, int n, int m) {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                // 左+上+自己-左上
                int sum = 0;
                if (i - 1 >= 0) {
                    sum += grid[i - 1][j];
                }
                if (j - 1 >= 0) {
                    sum += grid[i][j - 1];
                }
                if (i - 1 >= 0 && j - 1 >= 0) {
                    sum -= grid[i - 1][j - 1];
                }
                grid[i][j] += sum;
            }
        }
    }

    public static void main(String[] args) {
        int[][] grid = new int[][] {{1,2,4}, {2, 3, 5}};
        System.out.println(canPartitionGrid(grid));
    }
}
