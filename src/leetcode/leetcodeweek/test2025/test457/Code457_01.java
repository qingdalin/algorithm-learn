package leetcode.leetcodeweek.test2025.test457;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/7/6 9:50
 * https://leetcode.cn/contest/weekly-contest-457/problems/coupon-code-validator/
 */
public class Code457_01 {
    public static List<String> validateCoupons(String[] code, String[] businessLine, boolean[] isActive) {
        int n = code.length;
        List<String[]> ans = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            boolean c = isActive[i];
            if (!c) {
                continue;
            }
            String a = code[i];
            if (!check(a)) {
                continue;
            }
            String b = businessLine[i];
            if (b.equals("electronics") || b.equals("grocery") || b.equals("pharmacy") || b.equals("restaurant")) {
                ans.add(new String[] {a, b});
            }
        }
        ans.sort((a, b) -> !a[1].equals(b[1]) ? a[1].compareTo(b[1]) : a[0].compareTo(b[0]));
        List<String> res = new ArrayList<>();
        for (String[] cur : ans) {
            res.add(cur[0]);
        }
        return res;
    }

    public static boolean check(String str) {
        char[] s = str.toCharArray();
        int n = s.length;
        if (n == 0) {
            return false;
        }
        for (int i = 0; i < n; i++) {
            if ((s[i] >= 'a' && s[i] <= 'z') || (s[i] >= 'A' && s[i] <= 'Z') || (s[i] >= '0' && s[i] <= '9') || s[i] == '_') {
                continue;
            }
            return false;
        }
        return true;
    }

    public static void main(String[] args) {
        String[] code = new String[] {
            "SAVE20","","PHARMA5","SAVE@20"
        };
        String[] b = new String[] {
            "restaurant","grocery","pharmacy","restaurant"
        };
        boolean[] c = new boolean[] {
            true,true,true,true
        };
        System.out.println(validateCoupons(code, b, c));
    }
}
