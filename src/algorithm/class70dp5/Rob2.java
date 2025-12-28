package algorithm.class70dp5;

/**
 * @author 汪大鹏
 * @version 1.0
 * @date 2024-05-29 20:09
 * 你是一个专业的小偷，计划偷窃沿街的房屋，每间房内都藏有一定的现金。这个地方所有的房屋都 围成一圈 ，
 * 这意味着第一个房屋和最后一个房屋是紧挨着的。同时，相邻的房屋装有相互连通的防盗系统，如果两间相邻的房屋在同一晚上被小偷闯入，系统会自动报警 。
 *
 * 给定一个代表每个房屋存放金额的非负整数数组，计算你 在不触动警报装置的情况下 ，今晚能够偷窃到的最高金额。
 * https://leetcode.cn/problems/house-robber-ii/description/
 */
public class Rob2 {
    public int rob1(int[] nums) {
        int n = nums.length;
        if (n == 1) {
            return nums[0];
        }
        if (n == 2) {
            return Math.max(nums[0], nums[1]);
        }
        if (n == 3) {
            return Math.max(nums[2], Math.max(nums[0], nums[1]));
        }
        int prePre = nums[0];
        int pre = Math.max(nums[0], nums[1]);
        int ans1 = Integer.MIN_VALUE;
        for (int i = 2; i < n - 1; i++) {
            ans1 = Math.max(pre, Math.max(nums[i], prePre + nums[i]));
            prePre = pre;
            pre = ans1;
        }
        prePre = nums[1];
        pre = Math.max(nums[2], nums[1]);
        int ans2 = Integer.MIN_VALUE;
        for (int i = 3; i < n; i++) {
            ans2 = Math.max(pre, Math.max(nums[i], prePre + nums[i]));
            prePre = pre;
            pre = ans2;
        }
        return Math.max(ans1, ans2);
    }

    public int rob(int[] nums) {
        int n = nums.length;
        if (n == 1) {
            return nums[0];
        }
        return Math.max(best(nums, 1, n - 1), nums[0] + best(nums, 2, n - 2));
    }

    private int best(int[] nums, int l, int r) {
        if (l > r) {
            return 0;
        }
        if (l == r) {
            return nums[l];
        }
        if (l + 1 == r) {
            return Math.max(nums[l], nums[r]);
        }
        int prePre = nums[l];
        int pre = Math.max(nums[l], nums[l + 1]);
        int cur = Integer.MIN_VALUE;
        for (int i = l + 2; i <= r; i++) {
            cur = Math.max(pre, Math.max(nums[i], prePre + nums[i]));
            prePre = pre;
            pre = cur;
        }
        return cur;
    }
}
