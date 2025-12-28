package leetcode.study.leetcode401_600;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/8/16 9:46
 * https://leetcode.cn/problems/maximum-69-number/?envType=daily-question&envId=2025-08-16
 */
public class Maximum69Number20250816 {
    public static int maximum69Number (int num) {
        String s = String.valueOf(num);
        StringBuilder ans = new StringBuilder();
        boolean flag = true;
        for (int i = 0; i < s.length(); i++) {
            if (flag && s.charAt(i) == '6') {
                ans.append("9");
                flag = false;
            } else {
                ans.append(s.charAt(i));
            }
        }
        return Integer.parseInt(ans.toString());
    }
}
