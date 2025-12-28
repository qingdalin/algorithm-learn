package leetcode.study.leetcdoe201_400;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/7/27 14:27
 * https://leetcode.cn/problems/counting-bits/
 */
public class Leetcode338CountBits {

    public static int[] countBits(int n) {
        int[] ans = new int[n + 1];
        ans[0] = 0;
        for (int i = 1; i <= n; i++) {
            ans[i] = ans[i >> 1] + (i & 1);
        }
        return ans;
    }

    public static int[] countBits1(int n) {
        int[] ans = new int[n + 1];
        ans[0] = 0;
        for (int i = 1; i <= n; i++) {
            ans[i] = cntOne(i);
        }
        return ans;
    }

    private static int cntOne(int num) {
        int ans = 0;
        while (num > 0) {
            ans++;
            num &= (num - 1);
        }
        return ans;
    }

    private static int cntOne1(int num) {
        int ans = 0;
        while (num > 0) {
            if ((num & 1) == 1) {
                ans++;
            }
            num >>= 1;
        }
        return ans;
    }

    public static void main(String[] args) {
        int n = 20;
        int[] ans = countBits(n);
        for (int i = 0; i < ans.length; i++) {
            System.out.println(i + "的二进制1的数量：" + ans[i]);
        }
    }
}
