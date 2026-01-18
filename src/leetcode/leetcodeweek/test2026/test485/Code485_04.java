package leetcode.leetcodeweek.test2026.test485;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2026/1/18 9:28
 * https://leetcode.cn/problems/lexicographically-smallest-string-after-deleting-duplicate-characters/
 */
public class Code485_04 {
    public static String lexSmallestAfterDeletion(String str) {
        int[] cnts = new int[26];
        char[] s = str.toCharArray();
        int n = s.length;
        char[] stack = new char[n];
        int r = 0;
        for (char c : s) {
            cnts[c - 'a']++;
        }
        for (char cur : s) {
            while (r > 0 && stack[r - 1] > cur && cnts[stack[r - 1] - 'a'] > 1) {
                cnts[stack[r - 1] - 'a']--;
                r--;
            }
            stack[r++] = cur;
        }
        while (cnts[stack[r - 1] - 'a'] > 1) {
            cnts[stack[r - 1] - 'a']--;
            r--;
        }
        return String.valueOf(stack, 0, r);
    }

    public static void main(String[] args) {
        String s = "aaccb";
        System.out.println(lexSmallestAfterDeletion(s));
    }
}
