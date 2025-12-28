package leetcode.study.leetcode401_600;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/9/21 14:52
 * https://leetcode.cn/problems/detect-capital/
 */
public class Leetcode520DetectCapitalUse {
    public boolean detectCapitalUse1(String word) {
        int length = word.length();
        if (length == 1) {
            return true;
        }
        if (Character.isUpperCase(word.charAt(0))) {
            // 第一个是大写，后边只能是大写或者小写
            return isUpper(word) || isLower(word);
        } else {
            // 第一个小写后边只能全是小写
            return isLower(word);
        }
    }

    private boolean isLower(String str) {
        for (int i = 1; i < str.length(); i++) {
            if (Character.isUpperCase(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public boolean isUpper(String str) {
        for (int i = 1; i < str.length(); i++) {
            if (Character.isLowerCase(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public boolean detectCapitalUse(String word) {
        int cnt = 0;
        int n = word.length();
        for (int i = 0; i < n; i++) {
            if (Character.isUpperCase(word.charAt(i))) {
                cnt++;
            }
        }
        return cnt == 0 || cnt == n || (cnt == 1 && Character.isUpperCase(word.charAt(0)));
    }
}
