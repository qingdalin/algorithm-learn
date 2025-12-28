package leetcode.study.leetcdoe201_400;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/7/26 9:32
 * https://leetcode.cn/problems/bulls-and-cows/
 */
public class Leetcode299GetHint {
    public static String getHint(String secret, String guess) {
        char[] s1 = secret.toCharArray();
        char[] s2 = guess.toCharArray();
        int n = s1.length;
        int bulls = 0, cows = 0;
        int[] cntBulls = new int[10];
        int[] cntCows = new int[10];
        for (int i = 0; i < n; i++) {
            if (s1[i] == s2[i]) {
                bulls++;
            } else {
                cntBulls[s1[i] - '0']++;
                cntCows[s2[i] - '0']++;
            }
        }
        for (int i = 0; i < 10; i++) {
            cows += Math.min(cntBulls[i], cntCows[i]);
        }
        return bulls + "A" + cows + "B";
    }

    public static void main(String[] args) {
        String s1 = "11";
        String s2 = "10";
        System.out.println(getHint(s1, s2));
    }
}
