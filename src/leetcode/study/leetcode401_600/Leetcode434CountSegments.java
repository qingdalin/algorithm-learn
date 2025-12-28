package leetcode.study.leetcode401_600;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/8/13 20:38
 * https://leetcode.cn/problems/number-of-segments-in-a-string/
 */
public class Leetcode434CountSegments {
    public static int countSegments1(String str) {
        char[] s = str.toCharArray();
        int n = s.length;
        int cnt = 0, len = 0;
        for (int i = 0; i < n;) {
            if (Character.isLetter(s[i])) {
                i++;
                len++;
            } else {
                cnt += (len == 0 ? 0 : 1);
                len = 0;
                i++;
            }
        }
        cnt += (len == 0 ? 0 : 1);
        return cnt;
    }

    public static int countSegments(String str) {
        if ("".equals(str)) {
            return 0;
        }
        String[] s = str.split(" ");
        int n = s.length;
        int cnt = 0, len = 0;
        for (int i = 0; i < n; i++) {
            if (!s[i].equals("")) {
                cnt++;
            }
        }
        return cnt;
    }


    public static void main(String[] args) {
//        String s = "apple is good enough";
        String s = ", , , ,        a, eaefa";
        System.out.println(countSegments(s));
    }
}
