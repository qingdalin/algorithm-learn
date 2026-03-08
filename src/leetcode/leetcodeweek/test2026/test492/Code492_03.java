package leetcode.leetcodeweek.test2026.test492;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2026/3/8 11:01
 * https://leetcode.cn/contest/weekly-contest-492/problems/minimum-operations-to-sort-a-string/
 */
public class Code492_03 {
    // wangdapeng
    public static int minOperations1(String str) {
        char[] s = str.toCharArray();
        int n = s.length;
        if (n == 2) {
            if (s[0] > s[1]) {
                return -1;
            }
        }
        int ans = -1;
        for (int i = 0; i < n - 1; i++) {
            if (s[i] > s[i + 1]) {
                ans = 0;
                break;
            }
        }
        if (ans == -1) {
            return 0;
        }
        for (int i = 0; i < n - 1; i++) {
            if (s[i] > s[n - 1]) {
                ans++;
                break;
            }
        }
        for (int i = 1; i < n; i++) {
            if (s[0] > s[i]) {
                ans++;
                break;
            }
        }
//        for (int i = 0; i < n - 2; i++) {
//            if (s[i] > s[i + 1]) {
//                ans++;
//                break;
//            }
//        }
//        for (int i = 1; i < n - 1; i++) {
//            if (s[i] > s[i + 1]) {
//                ans++;
//                break;
//            }
//        }
        return ans == 0 ? -1 : ans;
    }
//    如果 s 已经是升序，无需操作。
//    如果 n=2，无法排序。
//    如果 s[0] 是最小值，排序 [1,n−1] 即可，操作 1 次。
//    如果 s[n−1] 是最大值，排序 [0,n−2] 即可，操作 1 次。
//    如果 [1,n−2] 中有最小值，那么先排序 [0,n−2]，把最小值排到最前面，然后排序 [1,n−1] 即可，操作 2 次。
//    如果 [1,n−2] 中有最大值，那么先排序 [1,n−1]，把最大值排到最后面，然后排序 [0,n−2] 即可，操作 2 次。
//    现在只剩下一种情况：s[0] 是最大值，s[n−1] 是最小值，且 [1,n−2] 不含最小值和最大值：
//    先排序 [0,n−2]，把最大值排到下标 n−2。
//    然后排序 [1,n−1]，把最大值排到最后面，且最小值排到下标 1。
//    最后排序 [0,n−2]，把最小值排到最前面。
//    一共操作 3 次。

    public static int minOperations(String str) {
        char[] s = str.toCharArray();
        int n = s.length;
        boolean isSorted = true;
        for (int i = 1; i < n; i++) {
            if (s[i - 1] > s[i]) {
                isSorted = false;
                break;
            }
        }
        if (isSorted) {
            // 已经升序
            return 0;
        }
        if (n == 2) {
            // 2个长度不能排序
            return -1;
        }
        char min = s[0], max = s[0];
        for (int i = 0; i < n; i++) {
            min = (char) Math.min(min, s[i]);
            max = (char) Math.max(max, s[i]);
        }
        if (s[0] == min || s[n - 1] == max) {
            // 如果 s[0] 是最小值，排序 [1,n-1] 即可
            // 如果 s[n-1] 是最大值，排序 [0,n-2] 即可
            return 1;
        }
        // 如果 [1,n-2] 中有最小值，那么先排序 [0,n-2]，把最小值排在最前面，然后排序 [1,n-1] 即可
        // 如果 [1,n-2] 中有最大值，那么先排序 [1,n-1]，把最大值排在最后面，然后排序 [0,n-2] 即可
        for (int i = 1; i < n - 1; i++) {
            if (s[i] == min || s[i] == max) {
                return 2;
            }
        }
        // 现在只剩下一种情况：s[0] 是最大值，s[n-1] 是最小值，且 [1,n-2] 不含最小值和最大值
        // 先排序 [0,n-2]，把最大值排到 n-2
        // 然后排序 [1,n-1]，把最大值排在最后面，且最小值排在 1
        // 最后排序 [0,n-2]，把最小值排在最前面
        return 3;
    }

    public static void main(String[] args) {
        String str = "klo";
        System.out.println(minOperations(str));
    }
}
