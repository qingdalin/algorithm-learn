package algorithm.class50;

import java.util.Arrays;

/**
 * @author 汪大鹏
 * @version 1.0
 * @date 2024-03-03 10:41
 * https://leetcode.cn/problems/heaters/
 * 寻找最佳供暖器半径，双指针
 */
public class FindRadius {
    public static void main(String[] args) {
        int[] houses = {1, 5};
        int[] heaters = {10};
        findRadius(houses, heaters);
    }
    public static int findRadius(int[] houses, int[] heaters) {
        Arrays.sort(houses);
        Arrays.sort(heaters);
        int ans = 0;
        for (int i = 0, j = 0; i < houses.length; i++) {
            while (!best(houses, heaters, i, j)) {
                j++;
            }
            ans = Math.max(ans, Math.abs(houses[i] - heaters[j]));
        }
        return ans;
    }

    // i号房屋 选j号供暖器 是最佳半径
    private static boolean best(int[] houses, int[] heaters, int i, int j) {
        return j == heaters.length - 1 || Math.abs(houses[i] - heaters[j]) < Math.abs(houses[i] - heaters[j + 1]);
    }
}
