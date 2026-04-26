package leetcode.leetcodeweek.test2026.test499;

import java.util.Arrays;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2026/4/26 9:16
 * https://leetcode.cn/contest/weekly-contest-499/problems/sort-vowels-by-frequency/
 */
public class Code499_02 {
    public static String sortVowels(String str) {
        char[] s = str.toCharArray();
        int n = s.length;
        int[][] cnt = new int[26][3];
        for (int i = 0; i < 26; i++) {
            cnt[i][1] = -1;
        }
        String yuanyin = "aeiou";
        for (int i = 0; i < n; i++) {
            int idx = yuanyin.indexOf(s[i]);
            if (idx != -1) {
                cnt[s[i] - 'a'][0]++;
                if (cnt[s[i] - 'a'][1] == -1) {
                    cnt[s[i] - 'a'][1] = i;
                }
                cnt[s[i] - 'a'][2] = s[i];
            }
        }
        Arrays.sort(cnt, (a, b) -> b[0] != a[0] ? b[0] - a[0] : a[1] - b[1]);
        for (int i = 0; i < n; i++) {
            if (yuanyin.indexOf(s[i]) != -1) {
                for (int j = 0; j < 26; j++) {
                    if (cnt[j][0] > 0) {
                        s[i] = (char) (cnt[j][2]);
                        cnt[j][0]--;
                        break;
                    }
                }
            }
        }
        return String.valueOf(s);
    }

    public static void main(String[] args) {
        String str = "pymmijoozi";
        System.out.println(sortVowels(str));
    }
}
