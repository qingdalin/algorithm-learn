package leetcode.study.leetcode401_600;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/8/3 16:09
 * https://leetcode.cn/problems/longest-repeating-character-replacement/
 */
public class Leetcode424CharacterReplacement {
    public static int characterReplacement(String str, int k) {
        char[] s = str.toCharArray();
        int n = s.length;
        int ans = Integer.MIN_VALUE;
        int[] cnt = new int[26];
        int max = 0;
        for (int l = 0, r = 0; r < n; r++) {
            cnt[s[r] - 'A']++;
            max = Math.max(max, cnt[s[r] - 'A']);
            if (r - l + 1 - max > k) {
                cnt[s[l] - 'A']--;
                l++;
            }
            ans = Math.max(ans, r - l + 1);
        }
        return ans;
    }

    public static void main(String[] args) {
        String s = "ABBB";
        int k = 2;
        System.out.println(characterReplacement(s, k));
    }
}
