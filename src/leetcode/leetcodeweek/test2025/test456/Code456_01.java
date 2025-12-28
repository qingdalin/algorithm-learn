package leetcode.leetcodeweek.test2025.test456;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/6/29 7:50
 * https://leetcode.cn/contest/weekly-contest-456/problems/partition-string/
 */
public class Code456_01 {
    public static List<String> partitionString(String str) {
        List<String> ans = new ArrayList<>();
        HashSet<String> set = new HashSet<>();
        char[] s = str.toCharArray();
        int n = s.length;
        String cur;
        for (int i = 0; i < n; i++) {
            cur = s[i] + "";
            while (i + 1 < n && set.contains(cur)) {
                cur += s[i + 1];
                i++;
            }
            if (!set.contains(cur)) {
                ans.add(cur);
                set.add(cur);
            }
        }
        // ["a","b","bc","c","cc","d"]
        return ans;
    }

    public static void main(String[] args) {
//        String s = "abbccccd";
        String s = "aaaa";
        System.out.println(partitionString(s));
    }
}
