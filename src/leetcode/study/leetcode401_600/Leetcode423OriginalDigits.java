package leetcode.study.leetcode401_600;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/8/3 15:46
 * https://leetcode.cn/problems/reconstruct-original-digits-from-english/
 */
public class Leetcode423OriginalDigits {
    public String originalDigits(String str) {
        // zero   z
        // one        o
        // two    w
        // three      t
        // four   u
        // five       f
        // six    x
        // seven      s
        // eight  g
        // nine
        char[] s = str.toCharArray();
        Map<Character, Integer> map = new HashMap<>();
        for (char c : s) {
            map.put(c, map.getOrDefault(c, 0) + 1);
        }
        int[] cnt = new int[10];
        cnt[0] = map.getOrDefault('z', 0);
        cnt[2] = map.getOrDefault('w', 0);
        cnt[4] = map.getOrDefault('u', 0);
        cnt[6] = map.getOrDefault('x', 0);
        cnt[8] = map.getOrDefault('g', 0);
        cnt[3] = map.getOrDefault('h', 0) - cnt[8];
        cnt[5] = map.getOrDefault('f', 0) - cnt[4];
        cnt[7] = map.getOrDefault('s', 0) - cnt[6];
        cnt[1] = map.getOrDefault('o', 0) - cnt[0] - cnt[2] - cnt[4];
        cnt[9] = map.getOrDefault('i', 0) - cnt[5] - cnt[6] - cnt[8];
        StringBuilder ans = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < cnt[i]; j++) {
                ans.append((char) (i + '0'));
            }
        }
        return ans.toString();
    }
}
