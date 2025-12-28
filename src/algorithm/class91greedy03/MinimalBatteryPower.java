package algorithm.class91greedy03;

import algorithm.ArrayUtil;

import java.util.Arrays;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2024/7/27 9:46
 * // 执行所有任务的最少初始电量
 * // 每一个任务有两个参数，需要耗费的电量、至少多少电量才能开始这个任务
 * // 返回手机至少需要多少的初始电量，才能执行完所有的任务
 * // 来自真实大厂笔试，没有在线测试，对数器验证
 */
public class MinimalBatteryPower {
    public static int atLeast1(int[][] jobs) {
        return f(jobs, jobs.length, 0);
    }

    private static int f(int[][] jobs, int n, int i) {
        if (i == n) {
            int ans = 0;
            for (int j = 0; j < n; j++) {
                // 上一步返回的电量加上当前耗费，最少启动电量，返回大的
                ans = Math.max(ans + jobs[j][0], jobs[j][1]);
            }
            return ans;
        } else {
            int ans = Integer.MAX_VALUE;
            for (int j = i; j < n; j++) {
                ArrayUtil.swap(jobs, j, i);
                ans = Math.min(ans, f(jobs, n, i + 1));
                ArrayUtil.swap(jobs, j, i);
            }
            return ans;
        }
    }
    public static int atLeast2(int[][] jobs) {
        // 根据消耗减去至少电量，倒序排
        Arrays.sort(jobs, (a, b) -> (b[0] - b[1]) - (a[0] - a[1]));
        int ans = 0;
        for (int[] job : jobs) {
            ans = Math.max(ans + job[0], job[1]);
        }
        return ans;
    }

    public static void main(String[] args) {
        int N = 10;
        int V = 20;
        System.out.println("测试开始");
        for (int i = 0; i < 2000; i++) {
            int n = (int) (Math.random() * N + 1);
            int[][] jobs = ArrayUtil.randomTwoDimensionalArray(n, V);
            int ans1 = atLeast1(jobs);
            int ans2 = atLeast2(jobs);
            if (ans1 != ans2) {
                System.out.println("出错了");
            }
            if (i % 100 == 0) {
                System.out.println("测试到第" + i + "组");
            }
        }
        System.out.println("测试结束");
    }
}
