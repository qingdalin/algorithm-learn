package algorithm.class97prime;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2024/8/3 11:06
 * 给定整数 n ，返回 所有小于非负整数 n 的质数的数量 。
 * 埃氏筛
 */
public class CountPrimes {
    public int countPrimes(int n) {
        return ehrlich(n);
    }

    private int ehrlich(int n) {
        boolean[] visited = new boolean[n + 1];
        for (int i = 2; i * i <= n; i++) {
            if (!visited[i]) {
                for (int j = i * i; j <= n; j += i) {
                    visited[j] = true;
                }

            }
        }
        int ans = 0;
        for (int i = 2; i <= n; i++) {
            if (!visited[i]) {
                ans++;
            }
        }
        return ans;
    }

    public static int euler(int n) {
        // 欧拉筛
        boolean[] visited = new boolean[n + 1];
        int[] prime = new int[n / 2 + 1];
        int cnt = 0;
        for (int i = 2; i <= n; i++) {
            if (!visited[i]) {
                prime[cnt++] = i;
            }
            for (int j = 0; j < cnt; j++) {
                if (i * prime[j] > n) {
                    break;
                }
                visited[i * prime[j]] = true;
                if (i % prime[j] == 0) {
                    break;
                }
            }
        }
        return cnt;
    }
    // 只求计数埃氏筛优化
    private int ehrlich2(int n) {
        if (n <= 1) {
            return 0;
        }
        boolean[] visited = new boolean[n + 1];
        int cnt = (n + 1) / 2; // 默认所有的奇数都是质数
        for (int i = 3; i * i <= n; i+=2) {
            if (!visited[i]) {
                for (int j = i * i; j <= n; j += 2 * i) {
                    if (!visited[j]) {
                        visited[j] = true;
                        cnt--;
                    }
                }
            }
        }
        return cnt;
    }
}
