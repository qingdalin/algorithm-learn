package algorithm.class87dp22;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2024/7/20 11:52
 */
// 选择k个数字使得两集合累加和相差不超过1
// 给定一个正数n，表示1~n这些数字都可以选择
// 给定一个正数k，表示要从1~n中选择k个数字组成集合A，剩下数字组成集合B
// 希望做到集合A和集合B的累加和相差不超过1
// 如果能做到，返回集合A选择了哪些数字，任何一种方案都可以
// 如果不能做到，返回长度为0的数组
// 2 <= n <= 10^6
// 1 <= k <= n
// 来自真实大厂笔试，没有测试链接，用对数器验证
public class PickNumber {
    public static void main(String[] args) {
        int N = 60;
        System.out.println("测试开始");
        for (int time = 0; time < 5000; time++) {
            int n = (int) (Math.random() * N) + 2;
            int k = (int) (Math.random() * n) + 1;
            int[] ans = f1(n, k);
            if (!pass(n, k, ans)) {
                System.out.println("出错了");
            }
            // 378
        }
        System.out.println("测试结束");
    }

    public static int[] f1(int n, int k) {
        // 1-n个数，选k个，两部分累加和不超过1
        long sum = (long) n * (n + 1) / 2;
        // 偶数情况
        int[] ans = generate(sum / 2, n, k);
        if (ans.length == 0 && (sum & 1) == 1) {
            ans = generate(sum / 2 + 1, n, k);
        }
        return ans;
    }

    private static int[] generate(long sum, int n, int k) {
        long minSum = (long) k * (k + 1) / 2; // 最小的k个数和
        int range = n - k; // 跳的范围
        if (sum < minSum || sum > minSum + (long) range * k) {
            // 如果sum小于最小或者大于最大的k个数和
            return new int[0];
        }
        // sum - minSum; 100 - 15 == 85 , 85 / 25 == 3
        long need = sum - minSum;
        int rightSize = (int) need / range;
        // 中间数跳的范围
        int minIndex = (int) ((k - rightSize) + need % range);
        int leftSize = k - rightSize - (need % range == 0 ? 0 : 1);
        int[] ans = new int[k];
        for (int i = 0; i < leftSize; i++) {
            ans[i] = i + 1;
        }
        if (need % range != 0) {
            ans[leftSize] = minIndex;
        }
        for (int i = k - 1, j = 0; j < rightSize; i--, j++) {
            ans[i] = n - j;
        }
        return ans;
    }

    public static boolean pass(int n, int k, int[] ans) {
        if (ans.length == 0) {
            // 如果方法1没有答案，方法2可以做到，两个不一致，返回false，不通过
            if (canSplit(n, k)) {
                return false;
            } else {
                // 如果方法1没有答案，方法2不能做到，两个一致，返回true，通过
                return true;
            }
        } else {
            if (ans.length != k) {
                return false;
            }
            long sum = (long) n * (n + 1) / 2;
            int pickSum = 0;
            for (int i : ans) {
                pickSum += i;
            }
            return Math.abs(pickSum - (sum - pickSum)) <= 1;
        }
    }

    private static boolean canSplit(int n, int k) {
        int sum = n * (n + 1) / 2;
        int wantSum = sum / 2 + ((sum & 1) == 0 ? 0 : 1);
        int[][][] dp = new int[n + 1][k + 1][wantSum + 1];
        return f(n, 1, k, wantSum, dp);
    }

    // 从第i个数开始选到n，一共选k个，和必须是s，是否可以做到
    private static boolean f(int n, int i, int k, int s, int[][][] dp) {
        if (k < 0 || s < 0) {
            return false;
        }
        if (i == n + 1) {
            // 越界了，选完k个数，和正好是0
            return k == 0 && s == 0;
        }
        if (dp[i][k][s] != 0) {
            return dp[i][k][s] == 1;
        }
        boolean ans = f(n, i + 1, k, s, dp) || f(n, i + 1, k - 1, s - i, dp);
        dp[i][k][s] = ans ? 1 : -1;
        return ans;
    }
}
