package leetcode.study.leetcode001_200;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/3/31 20:16
 */
public class LeetCode06convert {
    public static String convert(String s, int n) {
        if (s.length() <= n || n == 1) {
            return s;
        }
        List<List<Character>> list = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            list.add(new ArrayList<>());
        }
        // 0P       6I          12N
        // 1A    5L 7S      11I 13G
        // 2Y 4A    8H  10R
        // 3P       9I
        int m = 2 * n - 2;
        int[] indexArr = new int[m];
        for (int i = 0, j = n - 2; i < m; i++) {
            if (i < n) {
                indexArr[i] = i % n;
            } else {
                indexArr[i] = j--;
            }
        }
        char[] arr = s.toCharArray();
        for (int i = 0; i < arr.length; i++) {
            int index = i % m;
            list.get(indexArr[index]).add(arr[i]);
        }
        StringBuilder ans = new StringBuilder();
        for (int i = 0; i < n; i++) {
            for (Character c : list.get(i)) {
                ans.append(c);
            }
        }
        return ans.toString();
    }

    public static void main(String[] args) {
        //String s = "PAYPALISHIRING";
        String s = "A";
        int n = 1;
        System.out.println(convert(s, n));
    }
}
