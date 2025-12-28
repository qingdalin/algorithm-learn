package leetcode.leetcodeweek.test2025.test454;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/6/15 10:23
 * https://leetcode.cn/contest/weekly-contest-454/problems/generate-tag-for-video-caption/
 */
public class Code454_01 {
    public static String generateTag(String str) {
        String[] s = str.trim().split(" ");
        int n = s.length;
        StringBuilder ans = new StringBuilder("#");
        for (int i = 0; i < n; i++) {
            if (i == 0) {
                ans.append(s[i].toLowerCase());
            } else {
                String cur = s[i].toLowerCase();
                if (cur.isEmpty()) {
                    continue;
                }
                ans.append(Character.toUpperCase(cur.charAt(0)));
                ans.append(cur.substring(1));
            }
        }
        if (ans.length() <= 100) {
            return ans.toString();
        }
        return ans.substring(0, 100);
    }

    public static void main(String[] args) {
//        String s = "Leetcode daily streak achieved";
//        String s = "can I Go There";
//        String s = "hhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh";
//        String s = "FkVsgzfhQxPYKr WtaCvDuHQeo roeVHIoPorZuIuMDxhYVs jlfCrXCDqBVjgmzxxmZOpUpCc  eHMpZrNxilBPngylMcS";
        String s = " fPysaRtLQLiMKVvRhMkkDLNedQKffPnCjbITBTOVhoVjiKbfSawvpisDaNzXJctQkn";
        System.out.println(generateTag(s));
    }
}
