package leetcode.study.leetcode401_600;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/8/31 16:24
 * https://leetcode.cn/problems/base-7/
 */
public class Leetcode504ConvertToBase7 {
    public static String convertToBase7(int num) {
        // 100 / 7: 2
        // 14 / 7   0 2
        StringBuilder ans = new StringBuilder();
        boolean flag = false;
        if (num < 0) {
            num = -num;
            flag = true;
        }
        while (num >= 7) {
            int mod = num % 7;
            ans.insert(0, mod);
            num /= 7;
        }
        ans.insert(0, num);
        if (flag) {
            ans.insert(0, "-");
        }
        return ans.toString();
    }

    public static void main(String[] args) {
        System.out.println(convertToBase7(100));
    }
}
