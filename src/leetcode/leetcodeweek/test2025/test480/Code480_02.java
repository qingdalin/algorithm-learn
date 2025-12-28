package leetcode.leetcodeweek.test2025.test480;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/12/14 9:47
 * https://leetcode.cn/contest/weekly-contest-480/problems/reverse-words-with-same-vowel-count/
 */
public class Code480_02 {
    public static String reverseWords(String str) {
        String[] arr = str.split(" ");
        int n = arr.length;
        int firstCnt = getYuanYinCnt(arr[0]);
        StringBuilder ans = new StringBuilder(arr[0]);
        for (int i = 1; i < n; i++) {
            int curYuanYinCnt = getYuanYinCnt(arr[i]);
            String curStr = arr[i];
            if (curYuanYinCnt == firstCnt) {
               curStr = reverse(arr[i]);
            }
            ans.append(" ");
            ans.append(curStr);
        }
        return ans.toString();
    }

    private static String reverse(String s) {
        char[] arr = s.toCharArray();
        int n = arr.length;
        for (int l = 0, r = n - 1; l <= r; l++, r--) {
            char tmp = arr[l];
            arr[l] = arr[r];
            arr[r] = tmp;
        }
        return String.valueOf(arr);
    }

    public static int getYuanYinCnt(String s) {
        int cnt = 0;
        for (char c : s.toCharArray()) {
            if (isYuanYin(c)) {
                cnt++;
            }
        }
        return cnt;
    }

    public static boolean isYuanYin(char c) {
        return c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u';
    }

    public static void main(String[] args) {
        String s = "banana healthy";
        System.out.println(reverseWords(s));
    }
}
