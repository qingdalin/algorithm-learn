package leetcode.study.leetcdoe201_400;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/7/27 14:01
 * https://leetcode.cn/problems/count-hills-and-valleys-in-an-array/?envType=daily-question&envId=2025-07-27
 */
public class Leetcode2210CountHillValley {
    public static int countHillValley(int[] arr) {
        int ans = 0;
        int n = arr.length;
        int size = 0;
        for (int j = 1; j < n; j++) {
            if (arr[j - 1] != arr[j]) {
                arr[++size] = arr[j];
            }
        }
        for (int i = 1; i < size; i++) {
            if (arr[i] > arr[i - 1] && arr[i] > arr[i + 1]) {
                ans++;
            }
            if (arr[i]  < arr[i - 1] && arr[i] < arr[i + 1]) {
                ans++;
            }
        }
        return ans;
    }

    public static void main(String[] args) {
        int[] arr = new int[] {2,4,1,1,6,5};
        System.out.println(countHillValley(arr));
    }
}
