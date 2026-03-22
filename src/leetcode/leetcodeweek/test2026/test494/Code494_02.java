package leetcode.leetcodeweek.test2026.test494;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2026/3/22 10:02
 * https://leetcode.cn/contest/weekly-contest-494/problems/construct-uniform-parity-array-ii/
 */
public class Code494_02 {
    public static boolean uniformArray(int[] nums1) {
        int midOdd = Integer.MAX_VALUE;
        for (int num : nums1) {
            if (num % 2 != 0) {
                midOdd = Math.min(midOdd, num);
            }
        }
        if (midOdd == Integer.MAX_VALUE) {
            return true;
        }
        for (int num : nums1) {
            if (num % 2 == 0 && num < midOdd) {
                return false;
            }
        }
        return true;
    }

    public static boolean uniformArray1(int[] nums1) {
        if (nums1.length == 1) {
            return true;
        }
        int oddCnt = 0, evenCnt = 0, oddNum = -1, evenNum = -1;
        for (int num : nums1) {
            if (num % 2 == 0) {
                evenCnt++;
                evenNum = num;
            } else {
                oddCnt++;
                oddNum = num;
            }
        }
        if (evenCnt == 0 || oddCnt == 0) {
            return true;
        }
        if (oddCnt == 1) {
            for (int num : nums1) {
                if (num != oddNum && num - oddNum < 1) {
                    return false;
                }
            }
            return true;
        }
        if (evenCnt == 1) {
            for (int num : nums1) {
                if (evenNum - num >= 1) {
                    return true;
                }
            }
            return false;
        }
        return false;
    }

    public static void main(String[] args) {
//        int[] arr = {1,4, 7};
        int[] arr = {22,2, 13};
        System.out.println(uniformArray(arr));
    }

    public static boolean f(int[] nums1) {
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
        if (oddCnt != 1) {
            // 如果是偶数，有奇数不是1个
            return true;
        }
        // 奇数等于1
        if (evenCnt >= 0) {
            // 如果是奇数，偶数大于等于0即可
            return true;
        }
        return false;
    }
}
