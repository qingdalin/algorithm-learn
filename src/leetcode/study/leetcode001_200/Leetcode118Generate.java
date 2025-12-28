package leetcode.study.leetcode001_200;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/6/15 19:45
 * https://leetcode.cn/problems/pascals-triangle/
 */
public class Leetcode118Generate {
    //0  1
    //1  1 1
    //2  1 2 1
    //3  1 3 3  1
    //4  1 4 6  4  1
    //5  1 5 10 10 5  1
    //6  1 6 15 20 15 6 1
    public static List<List<Integer>> generate(int n) {
        // 当前格子= 左上 + 上方
        List<List<Integer>> ans = new ArrayList<>();
        int[][] arr = new int[n + 1][n + 1];
        arr[1][1] = 1;
        for (int i = 2; i <= n; i++) {
            arr[i][1] = 1;
            for (int j = 2; j <= i; j++) {
                arr[i][j] = arr[i - 1][ j - 1] + arr[i - 1][j];
            }
        }
        for (int i = 1; i <= n; i++) {
            List<Integer> cur = new ArrayList<>();
            for (int j = 1; j <= i; j++) {
                cur.add(arr[i][j]);
            }
            ans.add(cur);
        }
        return ans;
    }

    public static void main(String[] args) {
        int n = 1;
        System.out.println(generate(n));
    }
}
