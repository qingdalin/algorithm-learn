package leetcode.leetcodeweek.test2025.test479;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/12/7 10:00
 * https://leetcode.cn/contest/weekly-contest-479/problems/largest-prime-from-consecutive-prime-sum/
 */
public class Code479_02 {
    public static int MAXN = 500000;
    public static int MAXM = 50000;
    public static int cnt = 0;
    public static int len = 0;
    public static boolean[] prime = new boolean[MAXN + 1];
    public static int[] arr = new int[MAXM];
    public static int[] sum = new int[MAXM];
    static {
        prime[1] = true;
        for (int i = 2; i * i <= MAXN; i++) {
            if (!prime[i]) {
                for (int j = i * i; j < MAXN; j += i) {
                    prime[j] = true;
                }
            }
        }
        for (int i = 2; i <= MAXN; i++) {
            if (!prime[i]) {
                arr[++cnt] = i;
            }
        }
        sum[++len] = arr[1];
        int tmp = sum[1];
        for (int i = 2; i <= cnt; i++) {
            tmp = arr[i] + tmp;
            if (tmp > MAXN) {
                break;
            }
            if (!prime[tmp]) {
                sum[++len] = tmp;
            }
        }
    }
    public static int largestPrime(int n) {
        int l = 1, r = len, mid, ans = 0;
        while (l <= r) {
            mid = (l + r) >> 1;
            if (sum[mid] <= n) {
                ans = (int) sum[mid];
                l = mid + 1;
            } else {
                r = mid - 1;
            }
        }
        return ans;
    }

    public static void main(String[] args) {
        System.out.println(largestPrime(15));
    }
}
