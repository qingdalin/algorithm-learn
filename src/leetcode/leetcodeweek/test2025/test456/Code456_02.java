package leetcode.leetcodeweek.test2025.test456;

import java.util.Arrays;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/6/29 7:50
 * https://leetcode.cn/contest/weekly-contest-456/problems/longest-common-prefix-between-adjacent-strings-after-removals/
 */
public class Code456_02 {
    public static int[] longestCommonPrefix(String[] arr) {
        int n = arr.length;
        int[] ans = new int[n];
        if (n == 1) {
            return ans;
        }
        int[][] sort = new int[n - 1][3];
        for (int i = 0; i < n - 1; i++) {
            int curLen = maxPre(arr[i], arr[i + 1]);
            sort[i][0] = curLen;
            sort[i][1] = i;
            sort[i][2] = i + 1;
        }
        Arrays.sort(sort, (a, b) -> b[0] - a[0]);
        int first = sort[0][0], idx1 = sort[0][1], idx2 = sort[0][2];
        int second = 0;
        if (n > 2) {
           second = sort[1][0];
        }
        for (int i = 0; i < n; i++) {
            if (i != idx1 && i != idx2) {
                if (i - 1 >= 0 && i + 1 < n) {
                    ans[i] = Math.max(first, maxPre(arr[i - 1], arr[i + 1]));
                } else {
                    ans[i] = first;
                }
            } else if (i == idx1){
                if (i - 1 >= 0 && i + 1 < n) {
                    ans[i] = Math.max(second, maxPre(arr[i - 1], arr[i + 1]));
                } else {
                    ans[i] = second;
                }
            } else {
                if (i - 1 >= 0 && i + 1 < n) {
                    ans[i] = Math.max(second, maxPre(arr[i - 1], arr[i + 1]));
                } else {
                    ans[i] = second;
                }
            }
        }
        return ans;
    }

    private static int maxPre(String str1, String str2) {
        char[] s1 = str1.toCharArray();
        int n = s1.length;
        char[] s2 = str2.toCharArray();
        int m = s2.length;
        int len = 0;
        for (int i = 0, j = 0; i < n && j < m; i++, j++) {
            if (s1[i] == s2[j]) {
                len++;
            } else {
                break;
            }
        }
        return len;
    }

    public static void main(String[] args) {
        String[] arr = new String[] {
//            "jump","run","run","jump","run"
//            "dog","racer","car"
//            "cdbff"
//            "fec","fef","acaa","adfa","afc","fdbda"
            "f","cfe","feab","fcc","cdfda","fcec","afae","cdeb","dc","bffd","edabe"
        };
        System.out.println(Arrays.toString(longestCommonPrefix(arr)));
    }
}
