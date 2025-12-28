package leetcode.study.leetcdoe201_400;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/7/26 14:34
 * https://leetcode.cn/problems/maximum-product-of-word-lengths/
 */
public class Leetcode318MaxProduct {
    public static Set<Character> set = new HashSet<>();
    public static int maxProduct(String[] arr) {
        Arrays.sort(arr, (a, b) -> b.length() - a.length());
        int n = arr.length;
        int ans = Integer.MIN_VALUE;
        char[] s1, s2;
        for (int i = 0; i < n; i++) {
            s1 = arr[i].toCharArray();
            int m = s1.length;
            set.clear();
            for (int j = 0; j < m; j++) {
                set.add(s1[j]);
            }
            for (int j = i + 1; j < n; j++) {
                s2 = arr[j].toCharArray();
                int k = s2.length;
                boolean flag = true;
                for (int l = 0; l < k; l++) {
                    if (set.contains(s2[l])) {
                        flag = false;
                        break;
                    }
                }
                if (flag) {
                    ans = Math.max(ans, s1.length * s2.length);
                    return ans;
                }
            }
        }
        return ans == Integer.MIN_VALUE ? 0 : ans;
    }
}
