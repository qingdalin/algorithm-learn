package algorithm.class90greedy02;

import java.util.Arrays;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2024/7/21 17:42
 * 会议必须独占时间段的最大会议数量
 * 给定若干会议的开始和结束时间
 * 你参加会议的某个时间，不能参加其他会议
 * 返回你能参加的最大会议数量
 * 对数器
 */
public class MaxMeeting2 {
    public static int maxMeeting1(int[][] meeting) {
        return f1(meeting, meeting.length, 0);
    }

    private static int f1(int[][] meeting, int n, int i) {
        int ans = 0;
        if (i == n) {
            for (int j = 0, cur = -1; j < n; j++) {
                if (cur <= meeting[j][0]) {
                    ans++;
                    cur = meeting[j][1];
                }
            }
        } else {
            for (int j = 0; j < n; j++) {
                swap(meeting, i, j);
                ans = Math.max(ans, f1(meeting, n, i + 1));
                swap(meeting, i, j);
            }
        }
        return ans;
    }

    private static void swap(int[][] meeting, int i, int j) {
        int[] tem = meeting[i];
        meeting[i] = meeting[j];
        meeting[j] = tem;
    }

    public static int maxMeeting2(int[][] meeting) {
        // 按照结束时间早的排序
        Arrays.sort(meeting, (a, b) -> a[1] - b[1]);
        int ans = 0;
        for (int i = 0, cur = -1; i < meeting.length; i++) {
            if (cur <= meeting[i][0]) {
                ans++;
                cur = meeting[i][1];
            }
        }
        return ans;
    }

    public static void main(String[] args) {
        int N = 8;
        int m = 12;
        System.out.println("测试开始");
        for (int i = 0; i < 2000; i++) {
            int n = (int) (Math.random() * N + 1);
            int v = (int) (Math.random() * m + 1);
            int[][] meeting = randomMeeting(n, v);
            int ans1 = maxMeeting1(meeting);
            int ans2 = maxMeeting2(meeting);
            if (ans1 != ans2) {
                System.out.println("出错了");
            }
            if (i % 100 == 0) {
                System.out.println("测试第" + i + "组");
            }
        }
        System.out.println("测试结束");
    }

    private static int[][] randomMeeting(int n, int v) {
        int[][] meeting = new int[n][2];
        for (int i = 0; i < n; i++) {
            int a = (int) (Math.random() * v) + 1;
            int b = (int) (Math.random() * v) + 1;
            if (a == b) {
                meeting[i][0] = a;
                meeting[i][1] = a + 1;
            } else {
                meeting[i][0] = Math.min(a, b);
                meeting[i][1] = Math.max(a, b);
            }
        }
        return meeting;
    }
}
