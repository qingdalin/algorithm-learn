package algorithm.class71dp6;

/**
 * @author 汪大鹏
 * @version 1.0
 * @date 2024-06-01 10:38
 * 魔法卷轴
 * 给定一个数组nums，其中可能有正、负、0
 * 每个魔法卷轴可以把nums中连续的一段全变成0
 * 你希望数组整体的累加和尽可能大
 * 卷轴可以不使用，使用多少随意，但是一共只有两个魔法卷轴
 * 返回数组尽可能大的累加和
 */
public class SubMaxSum {
    public static int mostSum1(int[] nums) {
        int n = nums.length;
        int p1 = 0;
        // 不使用卷轴
        for (int i : nums) {
            p1 += i;
        }
        // 使用一次卷轴
        int p2 = mostSumColorII(nums, 0, n -1);
        // 使用2次卷轴
        int p3 = Integer.MIN_VALUE;
        for (int i = 1; i < n; i++) {
            p3 = Math.max(p3, mostSumColorII(nums, 0 ,i - 1) + mostSumColorII(nums, i, n - 1));
        }
        return Math.max(p1, Math.max(p2, p3));
    }

    private static int mostSumColorII(int[] nums, int l, int r) {
        int ans = Integer.MIN_VALUE;
        for (int a = l; a <= r; a++) {
            for (int b = a; b <= r; b++) {
                int ansCur = 0;
                for (int i = l; i < a; i++) {
                    ansCur += nums[i];
                }
                for (int i = b + 1; i <= r; i++) {
                    ansCur += nums[i];
                }
                ans = Math.max(ans, ansCur);
            }
        }
        return ans;
    }

    public static int mostSum2(int[] nums) {
        int n = nums.length;
        int p1 = 0;
        // 不使用卷轴
        for (int i : nums) {
            p1 += i;
        }
        // 使用一次卷轴
        int[] prefix = new int[n];
        int sum = nums[0];
        int maxPreSum = Math.max(0, nums[0]);
        for (int i = 1; i < n; i++) {
            prefix[i] = Math.max(prefix[i - 1] + nums[i], maxPreSum);
            sum += nums[i];
            maxPreSum = Math.max(maxPreSum, sum);
        }
        int p2 = prefix[n - 1];
        // 使用2次卷轴
        int[] suffix = new int[n];
        sum = nums[n - 1];
        maxPreSum = Math.max(0, sum);
        for (int i = n - 2; i >= 0; i--) {
            suffix[i] = Math.max(suffix[i + 1] + nums[i], maxPreSum);
            sum += nums[i];
            maxPreSum = Math.max(sum, maxPreSum);
        }
        int p3 = Integer.MIN_VALUE;
        for (int i = 1; i < n; i++) {
            p3 = Math.max(p3, prefix[i - 1] + suffix[i]);
        }
        return Math.max(p1, Math.max(p2, p3));
    }

    public static void main(String[] args) {
        int n = 50;
        int v = 100;
        int t = 10000;
        System.out.println("测试开始");
        for (int i = 0; i < t; i++) {
            int len = (int) (Math.random() * n) + 1;
            int[] nums = randomArray(len, v);
            int ans1 = mostSum1(nums);
            int ans2 = mostSum2(nums);
            if (ans1 != ans2) {
                System.out.println("出错了");
            }
        }
        System.out.println("测试结束");
    }

    private static int[] randomArray(int n, int v) {
        int[] ans = new int[n];
        for (int i = 0; i < n; i++) {
            ans[i] = (int) (Math.random() * (2 * v +1) - v);
        }
        return ans;
    }
}
