package leetcode.study.leetcdoe201_400;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/7/26 14:54
 * https://leetcode.cn/problems/bulb-switcher/
 */
public class Leetcode319BulbSwitch {
    public static int bulbSwitch(int n) {
        if (n <= 1) {
            return n;
        }
        if (n == 2) {
            return (n + 1) / 2;
        }
        // 1轮 1 1 1 1 1 1 1 1
        // 2轮 1 0 1 0 1 0 1 0
        // 3轮 1 0 0 0 1 1 1 0
        // 4轮 1 0 0 1 1 1 1 1
        // 5轮 1 0 0 1 0 1 1 1
        // 6轮 1 0 0 1 0 0 1 1
        // 7轮 1 0 0 1 0 0 0 1
        // 8轮 1 0 0 1 0 0 0 0
        //
        return (int) Math.sqrt(n);
    }

    public static int bulbSwitch1(int n) {
        if (n <= 1) {
            return n;
        }
        if (n == 2) {
            return (n + 1) / 2;
        }
        int[] arr = new int[n + 1];
        Arrays.fill(arr, 1);
        int ans = 0;
        for (int i = 2; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                if (j % i == 0) {
                    arr[j] = arr[j] == 1 ? 0 : 1;
                }
            }
        }
        for (int i = 1; i <= n; i++) {
            if (arr[i] == 1) {
                ans++;
            }
        }
        return ans;
    }

    public static void main(String[] args) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 1; i <= 130; i++) {
            int ans = bulbSwitch1(i);
            System.out.println(i + " = " + ans);
            map.put(ans, map.getOrDefault(ans, 0) + 1);
        }
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            System.out.println(entry.getKey() + "答案出现的次数" + entry.getValue());
        }
    }
}
