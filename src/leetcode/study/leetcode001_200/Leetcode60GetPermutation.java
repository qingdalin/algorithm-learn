package leetcode.study.leetcode001_200;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/5/18 19:58
 */
public class Leetcode60GetPermutation {
    public static int[] cnt;
    static {
        cnt = new int[] {1,1,2,6,24,120,720,5040,40320,362880};
    }
    public static String getPermutation(int n, int k) {
        boolean[] visit = new boolean[n + 1];
        StringBuilder ans = new StringBuilder();
        for (int i = n; i >= 1; i--) {
            for (int j = 1; j <= n; j++) {
                // 当前数已经占用
                if (visit[j]) {
                    continue;
                }
                if (cnt[i - 1] < k) {
                    // 需要找第k小的数，如果k大于后边的所有选择可能性，
                    // 说明当前i数字选了也满足不了第k小，那么k减去当前i数字的可能性，去下一个数字判断
                    k -= cnt[i - 1];
                } else {
                    // 后边的可能性可以满足第k小，那么选当前第一个满足的数字，肯定是最小的
                    // 将当前数字标为true选择了，跳出循环去下一个数字判断
                    ans.append(j);
                    visit[j] = true;
                    break;
                }
            }
        }
        return ans.toString();
    }

    private static int[] getFactorial(int n) {
        int[] res = new int[n + 1];
        res[1] = res[0] = 1;
        for (int i = 2; i <= n; i++) {
            res[i] = res[i - 1] * i;
        }
        return res;
    }

    public static void main(String[] args) {
        int n = 3;
        System.out.println(getPermutation(n, 3));
    }
}
