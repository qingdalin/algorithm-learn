package leetcode.study.leetcode001_200;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/7/7 19:48
 * https://leetcode.cn/problems/excel-sheet-column-title/
 */
public class Leetcode168ConvertToTitle {
    public static String convertToTitle(int num) {
        StringBuilder ans = new StringBuilder();
        while (num > 0) {
            int mod = num % 26;
            if (mod == 0) {
                mod += 26;
                num -= 1;
            }
            char c = (char) (mod  + 'A' - 1);
            ans.insert(0, c);
            num /= 26;
        }
        return ans.toString();
    }

    public static void main(String[] args) {
        int num = 701;
        System.out.println(convertToTitle(num));
    }
}
