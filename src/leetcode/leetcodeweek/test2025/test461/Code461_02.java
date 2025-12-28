package leetcode.leetcodeweek.test2025.test461;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/8/3 7:58
 * https://leetcode.cn/contest/weekly-contest-461/problems/maximum-balanced-shipments/
 */
public class Code461_02 {
    public static int maxBalancedShipments(int[] arr) {
        int n = arr.length;
        int ans = 0;
        int max = arr[0];
        for (int i = 1; i < n; i++) {
            max = Math.max(max, arr[i]);
            if (arr[i] < max) {
                ans++;
                max = 0;
            }
        }
        return ans;
    }

    public static void main(String[] args) {
//        int[] arr = new int[] {2,5,1,4,3};
        int[] arr = new int[] {4,4};
        System.out.println(maxBalancedShipments(arr));
    }
}
