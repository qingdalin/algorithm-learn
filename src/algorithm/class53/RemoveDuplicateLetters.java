package algorithm.class53;

import java.util.Arrays;

/**
 * @author 汪大鹏
 * @version 1.0
 * @date 2024-03-10 11:08
 * https://leetcode.cn/problems/remove-duplicate-letters/
 * 去除重复的字符
 */
public class RemoveDuplicateLetters {
    public static int MAXN = 26;
    public static int[] cnts = new int[MAXN];
    public static char[] stack = new char[MAXN];
    public static boolean[] enter = new boolean[MAXN];
    public static int r;
    public String removeDuplicateLetters(String s) {
        r = 0;
        char[] chars = s.toCharArray();
        Arrays.fill(cnts, 0);
        Arrays.fill(enter, false);
        for (char cur : chars) {
            cnts[cur - 'a']++;
        }
        for (char cur : chars) {
            if (!enter[cur - 'a']) {
                while (r > 0 && stack[r -1] > cur && cnts[stack[r -1] - 'a'] > 0) {
                    enter[stack[r -1] - 'a'] = false;
                    r--;
                }
                stack[r++] = cur;
                enter[cur - 'a'] = true;
            }
            cnts[cur - 'a']--;
        }
        return String.valueOf(stack, 0 , r);
    }

    public static void main(String[] args) {
        System.out.println(enter);
    }
}
