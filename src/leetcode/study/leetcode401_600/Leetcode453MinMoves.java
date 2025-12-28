package leetcode.study.leetcode401_600;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/8/17 10:07
 * https://leetcode.cn/problems/minimum-moves-to-equal-array-elements/
 */
public class Leetcode453MinMoves {
    public static int minMoves(int[] nums) {
        // 1 0 2
        // 2 1 2
        // 3 2 2
        // 3 3 3
        // 找到最小值，其余数减到最小值需要的总和
        int min = Integer.MAX_VALUE;
        for (int num : nums) {
            min = Math.min(min, num);
        }
        int cnt = 0;
        for (int num : nums) {
            cnt += num - min;
        }
        return cnt;
    }

    public static int minMoves1(int[] nums) {
        // 1 0 2
        // 2 1 2
        // 3 2 2
        // 3 3 3
        int min = Integer.MAX_VALUE;
        for (int num : nums) {
            min = Math.min(min, num);
        }
        int cnt = 0;
        for (int num : nums) {
            cnt += num - min;
        }
        return cnt;
    }

    public static void main(String[] args) {
        int[] arr = {0, 0};
        System.out.println(minMoves(arr));
    }
}
