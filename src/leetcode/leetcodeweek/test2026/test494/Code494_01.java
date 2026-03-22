package leetcode.leetcodeweek.test2026.test494;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2026/3/22 10:01
 * https://leetcode.cn/contest/weekly-contest-494/problems/construct-uniform-parity-array-i/description/
 */
public class Code494_01 {
    public static boolean uniformArray(int[] nums1) {
        // 如果是奇数，需要有小于等于1个偶数
        int evenCnt = 0;
        int oddCnt = 0;
        for (int num : nums1) {
            if (num % 2 == 0) {
                evenCnt++;
            } else {
                oddCnt++;
            }
        }
//        if (oddCnt != 1) {
//            // 如果是偶数，有奇数不是1个
//            return true;
//        }
//        if (evenCnt >= 0) {
//            // 如果是奇数，偶数大于等于0即可
//            return true;
//        }
//        return false;
        return true; // 一定可以，有1个奇数，是偶数数组，否则是奇数数组
    }

    public static void main(String[] args) {
        int[] arr = {55};
        System.out.println(uniformArray(arr));
    }
}
