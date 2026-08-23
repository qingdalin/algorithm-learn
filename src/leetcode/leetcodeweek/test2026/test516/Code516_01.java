package leetcode.leetcodeweek.test2026.test516;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2026/8/23 8:13
 * https://leetcode.cn/contest/weekly-contest-516/problems/check-ascii-palindromic/description/
 */
public class Code516_01 {
    // 128 64 32 16 8 4 2 1
    //  0   1  1  0 0 1 1 0
    // 64 + 32 + 4 + 2 = 102
    public static boolean isPalindromic(String str) {
        char[] s = str.toCharArray();
        StringBuilder binaryStr = new StringBuilder();
        for (char c : s) {
            String cur = getBinaryStr(c);
            binaryStr.append(cur);
        }
        return isPal(binaryStr.toString());
    }

    public static boolean isPal(String str) {
        for (int l = 0, r = str.length() - 1; l <= r; l++, r--) {
            if (str.charAt(l) != str.charAt(r)) {
                return false;
            }
        }
        return true;
    }

    public static String getBinaryStr(int num) {
        StringBuilder ans = new StringBuilder();
        for (int i = 7; i >= 0; i--) {
            if (num >= (1 << i)) {
                ans.append("1");
                num -= (1 << i);
            } else {
                ans.append("0");
            }
        }
        return ans.toString();
    }

    public static void main(String[] args) {
        String s = "leet";
        System.out.println(isPalindromic(s));
    }
}
