package algorithm.class105strhash;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2024/8/18 15:57
 * 给定一个字符串 s 和一个字符串数组 words。 words 中所有字符串 长度相同。
 *
 *  s 中的 串联子串 是指一个包含  words 中所有字符串以任意顺序排列连接起来的子串。
 *
 * 例如，如果 words = ["ab","cd","ef"]， 那么 "abcdef"， "abefcd"，"cdabef"， "cdefab"，"efabcd"， 和 "efcdab" 都是串联子串。
 * "acdbef" 不是串联子串，因为他不是任何 words 排列的连接。
 * 返回所有串联子串在 s 中的开始索引。你可以以 任意顺序 返回答案。
 * https://leetcode.cn/problems/substring-with-concatenation-of-all-words/description/
 */
public class FindSubstring {
    public static int MAXN = 10001;
    public static int base = 499;
    public static long[] power = new long[MAXN];
    public static long[] hash = new long[MAXN];
    public List<Integer> findSubstring(String s, String[] words) {
        List<Integer> ans = new ArrayList<>();
        Map<Long, Integer> map = new HashMap<>();
        int wordNum = words.length;
        // 统计欠债的词频
        for (int i = 0; i < wordNum; i++) {
            long hash = hash(words[i].toCharArray());
            map.put(hash, map.getOrDefault(hash, 0) + 1);
        }
        // 窗口的词频
        Map<Long, Integer> window = new HashMap<>();
        int n = s.length();
        build(s, n);
        int wordLen = words[0].length();
        int allWordLen = wordLen * wordNum;
        // 所有单词的加起来的长度，和单个单词的长度
        for (int init = 0; init < wordLen && init + allWordLen <= n; init++) {
            int debt = wordNum;
            for (int l = init, r = init + wordLen, part = 0; part < wordNum; part++, l += wordLen, r += wordLen) {
                long curHash = hash(l, r);
                window.put(curHash, window.getOrDefault(curHash, 0) + 1);
                if (window.get(curHash) <= map.getOrDefault(curHash, 0)) {
                    debt--;
                }
            }
            if (debt == 0) {
                ans.add(init);
            }
            for (int l1 = init, r1 = init + wordLen, l2 = init + allWordLen, r2 = init + wordLen + allWordLen;
                 r2 <= n; l1 += wordLen, r1 += wordLen, l2 += wordLen, r2 += wordLen) {
                long out = hash(l1, r1);
                long in = hash(l2, r2);
                window.put(out, window.get(out) - 1);
                if (window.get(out) < map.getOrDefault(out, 0)) {
                    debt++;
                }
                window.put(in, window.getOrDefault(in, 0) + 1);
                if (window.get(in) <= map.getOrDefault(in, 0)) {
                    debt--;
                }
                if (debt == 0) {
                    ans.add(r1);
                }
            }
            window.clear();
        }
        return ans;
    }

    private long hash(int l, int r) {
        long ans = hash[r - 1];
        if (l > 0) {
            ans -= hash[l - 1] * power[r - l];
        }
        return ans;
    }

    private void build(String s, int n) {
        power[0] = 1;
        for (int i = 1; i < n; i++) {
            power[i] = power[i - 1] * base;
        }
        hash[0] = s.charAt(0) - 'a' + 1;
        for (int i = 1; i < n; i++) {
            hash[i] = hash[i - 1] * base + s.charAt(i) - 'a' + 1;
        }
    }

    private long hash(char[] s) {
        long ans = s[0] - 'a' + 1;
        for (int i = 1; i < s.length; i++) {
            ans = ans * base + s[i] - 'a' + 1;
        }
        return ans;
    }
}
