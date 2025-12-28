package leetcode.leetcodeweek.test2025.test455;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/6/22 10:01
 * https://leetcode.cn/contest/weekly-contest-455/problems/check-if-any-element-has-prime-frequency/
 */
public class Code455_01 {
    public static boolean checkPrimeFrequency(int[] arr) {
        Map<Integer, Integer> map = new HashMap<>();
        int n = arr.length;
        for (int i = 0; i < n; i++) {
            map.put(arr[i], map.getOrDefault(arr[i], 0) + 1);
        }
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            if (prime(entry.getValue())) {
                return true;
            }
        }
        return false;
    }

    public static boolean prime(int num) {
        if (num == 2) {
            return true;
        }
        if (num == 1) {
            return false;
        }
        for (int i = 2; i * i <= num; i++) {
            if (num % i == 0) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
//        int[] arr = new int[] {1,2,3,4,5,4};
        int[] arr = new int[] {2,2,2,4,4};
        System.out.println(checkPrimeFrequency(arr));
    }
}
