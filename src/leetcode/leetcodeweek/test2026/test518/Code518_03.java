package leetcode.leetcodeweek.test2026.test518;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2026/9/6 10:19
 * https://leetcode.cn/contest/weekly-contest-518/problems/count-robot-groups/
 */
public class Code518_03 {
    public static int countGroups1(int[] position, int[] speed, int distance) {
        int n = position.length;
        int ans = n;
        for (int i = 0; i < n - 1; i++) {
            if (speed[i] > speed[i + 1] || position[i] + distance >= position[i + 1]) {
                ans--;
            }
        }
        return ans;
    }

    public static int countGroups(int[] position, int[] speed, int distance) {
        int n = position.length;
        int ans = n;
        for (int i = n - 1; i > 0; i--) {
            if (speed[i] < speed[i - 1] || position[i - 1] + distance >= position[i]) {
                ans--;
                speed[i - 1] = speed[i];
            }
        }
        return ans;
    }

    public static void main(String[] args) {
//        int[] pos = {1,5,6,20};
//        int[] speed = {4,3,2,3};
//        int d = 1;

        int[] pos = {677,711,942,960};
        int[] speed = {774,951,743,516};
        int d = 27;
        System.out.println(countGroups(pos, speed, d));
    }
}
