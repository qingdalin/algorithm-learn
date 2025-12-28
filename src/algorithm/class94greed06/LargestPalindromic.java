package algorithm.class94greed06;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2024/7/28 10:00
 * 给你一个仅由数字（0 - 9）组成的字符串 num 。
 *
 * 请你找出能够使用 num 中数字形成的 最大回文 整数，并以字符串形式返回。该整数不含 前导零 。
 *
 * 注意：
 *
 * 你 无需 使用 num 中的所有数字，但你必须使用 至少 一个数字。
 * 数字可以重新排序。
 * https://leetcode.cn/problems/largest-palindromic-number/description/
 */
public class LargestPalindromic {
    public String largestPalindromic(String num) {
        int n = num.length();
        // '0' - '9'的ASCII码对应48-57
        int[] cnts = new int[58];
        for (char c : num.toCharArray()) {
            cnts[c]++;
        }
        char[] ans = new char[n];
        int leftSize = 0;
        char mid = 0;
        // 找到中点和左侧的大小
        for (char i = '9'; i >= '1'; i--) {
            // 如果mid==0且词频是奇数，设置mid
            if (mid == 0 && cnts[i] % 2 == 1) {
                mid = i;
            }
            for (int j = cnts[i] / 2; j > 0; j--) {
                ans[leftSize++] = i;
            }
        }
        if (leftSize == 0) {
            // 左侧没有
            if (mid == 0) {
                // 中间数是0
                return "0";
            } else {
                return String.valueOf(mid);
            }
        }
        // 设置0
        for (int i = cnts['0'] / 2; i > 0; i--) {
            ans[leftSize++] = '0';
        }
        // 中间数没有，且0是奇数
        int len = leftSize;
        if (mid == 0 && cnts['0'] % 2 == 1) {
            mid = '0';
        }
        if (mid != 0) {
            ans[len++] = mid;
        }
        // 设置右边
        for (int i = leftSize - 1; i >= 0; i--) {
            ans[len++] = ans[i];
        }
        return String.valueOf(ans, 0, len);
    }
}
