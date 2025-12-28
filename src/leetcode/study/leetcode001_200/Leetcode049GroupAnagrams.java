package leetcode.study.leetcode001_200;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/5/14 19:25
 * https://leetcode.cn/problems/group-anagrams/
 */
public class Leetcode049GroupAnagrams {
    public static List<List<String>> groupAnagrams(String[] strs) {
        int n = strs.length;
        Map<String, List<String>> map = new HashMap<>();
        char[] s;
        String key;
        for (int i = 0; i < n; i++) {
            s = strs[i].toCharArray();
            Arrays.sort(s);
            key = String.valueOf(s);
            if (map.containsKey(key)) {
                map.get(key).add(strs[i]);
            } else {
                List<String> list = new ArrayList<>();
                list.add(strs[i]);
                map.put(key, list);
            }
        }
        List<List<String>> ans = new ArrayList<>();
        for (Map.Entry<String, List<String>> entry : map.entrySet()) {
            ans.add(entry.getValue());
        }
        return ans;
    }


    public static List<List<String>> groupAnagrams1(String[] strs) {

        int n = strs.length;
        Map<String, List<String>> map = new HashMap<>();
        int[] cnt = new int[26];
        StringBuilder key;
        for (int i = 0; i < n; i++) {
            Arrays.fill(cnt, 0);
            char[] s = strs[i].toCharArray();
            int length = s.length;
            for (int j = 0; j < length; j++) {
                cnt[s[j] - 'a']++;
            }
            key = new StringBuilder();
            for (int k = 0; k < 26; k++) {
                if (cnt[k] != 0) {
                    key.append("#").append((char) (k + 'a')).append("#").append(cnt[k]);
                }
            }
            if (map.containsKey(key.toString())) {
                map.get(key.toString()).add(strs[i]);
            } else {
                List<String> list = new ArrayList<>();
                list.add(strs[i]);
                map.put(key.toString(), list);
            }
        }
        List<List<String>> ans = new ArrayList<>();
        for (Map.Entry<String, List<String>> entry : map.entrySet()) {
            ans.add(entry.getValue());
        }
        return ans;
    }

    public static void main(String[] args) {
        String[] strs = {"eat", "tea", "tan", "ate", "nat", "bat"};
        System.out.println(groupAnagrams(strs));
    }
}
