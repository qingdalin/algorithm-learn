package leetcode.study.leetcdoe201_400;

import java.util.Arrays;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/7/29 19:20
 * https://leetcode.cn/problems/smallest-subarrays-with-maximum-bitwise-or/?envType=daily-question&envId=2025-07-29
 */
public class Leetcode2411SmallestSubarrays {
    public static int[] smallestSubarrays(int[] arr) {
        int n = arr.length;
        int[] ans = new int[n];
        int sufEor = 0, leftEor = 0;
        int r = n - 1, b = n - 1;
        for(int l = n - 1; l >= 0; l--) {
            sufEor |= arr[l];
            leftEor |= arr[l];
            while (r >= l && ((leftEor | arr[r]) == sufEor)) {
                r--;
                if (b > r) {
                    for (int i = l + 1; i <= r; i++) {
                        arr[i] |= arr[i - 1];
                    }
                    b = l;
                    leftEor = 0;
                }
            }
            ans[l] = r - l + 2;
        }
        return ans;
    }

    public static void main(String[] args) {
        int[] arr = new int[] {1,0,2,1,3};
        System.out.println(Arrays.toString(smallestSubarrays(arr)));
    }
}
