package algorithm.class71dp6;

/**
 * @author 汪大鹏
 * @version 1.0
 * @date 2024-06-01 14:46
 * 可以翻转一次的情况下子数组最大累加和
 * 给定一个数组nums
 * 现在允许你随意选择数组连续一段进行翻转，也就是子数组进行逆序调整
 * 返回必须随意翻转一次之后，子数组的最大累加和
 * 必须翻转一次和可以翻转一次没区别，如果只翻转一个即可
 */
public class ReverseSubArrayMaxSum {
    // 暴力方法
    public static int reverseSubArrayMaxSum1(int[] nums) {
        int n = nums.length;
        int ans = Integer.MIN_VALUE;
        for (int l = 0; l < n; l++) {
            for (int r = l; r < n; r++) {
                reverse(nums, l, r);
                ans = Math.max(ans, maxSum(nums));
                reverse(nums, l, r);
            }
        }
        return ans;
    }

    private static int maxSum(int[] nums) {
        int ans = nums[0];
        for (int i = 1, pre = nums[0]; i < nums.length; i++) {
            pre = Math.max(nums[i], nums[i] + pre);
            ans = Math.max(ans, pre);
        }
        return ans;
    }

    private static void reverse(int[] nums, int l, int r) {
        while (l < r) {
            int tmp = nums[l];
            nums[l] = nums[r];
            nums[r] = tmp;
            l++;
            r--;
        }
    }

    public static int reverseSubArrayMaxSum2(int[] nums) {
        int n = nums.length;
        // 数组以i开头，最大的累加和
        int[] start = new int[n];
        start[n - 1] = nums[n - 1];
        for(int i = n - 2; i >= 0; i--) {
            start[i] = Math.max(nums[i], nums[i] + start[i + 1]);
        }
        int ans = start[0];
        int end = nums[0]; // 子数组必须以i - 1结尾的最大子数组累加和
        int maxEnd = nums[0];
        // 子数组必须以0结尾的最大累加和
        // 子数组必须以1结尾的最大累加和
        // 子数组必须以i-1结尾的最大累加和，所有情况最大的一个
        for (int i = 1; i < n; i++) {
            ans = Math.max(ans, maxEnd + start[i]);
            end = Math.max(nums[i], end + nums[i]);
            maxEnd = Math.max(maxEnd, end);
        }
        ans = Math.max(ans, maxEnd);
        return ans;
    }

    public static void main(String[] args) {
        int n = 50;
        int v = 300;
        int t = 10000;
        System.out.println("开始测试");
        for (int i = 0; i < t; i++) {
            int len = (int) (Math.random() * n) + 1;
            int[] nums = randomArray(len, v);
            int ans1 = reverseSubArrayMaxSum1(nums);
            int ans2 = reverseSubArrayMaxSum2(nums);
            if (ans1 != ans2) {
                System.out.println("出错了");
            }
        }
        System.out.println("结束测试");
    }
    private static int[] randomArray(int n, int v) {
        int[] ans = new int[n];
        for (int i = 0; i < n; i++) {
            ans[i] = (int) (Math.random() * (2 * v +1) - v);
        }
        return ans;
    }
}
