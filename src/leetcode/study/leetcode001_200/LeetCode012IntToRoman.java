package leetcode.study.leetcode001_200;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/4/9 19:45
 * https://leetcode.cn/problems/integer-to-roman/
 */
public class LeetCode012IntToRoman {
    public static void main(String[] args) {
        System.out.println(intToRoman(3749));
    }
    public static Map<Integer, Map<Integer, String>> map = new HashMap<>();
    public static StringBuilder ans = new StringBuilder();
    static {
        Map<Integer, String> four = new HashMap<>();
        four.put(1, "M");
        four.put(2, "MM");
        four.put(3, "MMM");
        map.put(4, four);
        Map<Integer, String> three = new HashMap<>();
        three.put(1, "C");
        three.put(2, "CC");
        three.put(3, "CCC");
        three.put(4, "CD");
        three.put(5, "D");
        three.put(6, "DC");
        three.put(7, "DCC");
        three.put(8, "DCCC");
        three.put(9, "CM");
        three.put(0, "");
        map.put(3, three);
        Map<Integer, String> two = new HashMap<>();
        two.put(1, "X");
        two.put(2, "XX");
        two.put(3, "XXX");
        two.put(4, "XL");
        two.put(5, "L");
        two.put(6, "LX");
        two.put(7, "LXX");
        two.put(8, "LXXX");
        two.put(9, "XC");
        two.put(0, "");
        map.put(2, two);
        Map<Integer, String> one = new HashMap<>();
        one.put(1, "I");
        one.put(2, "II");
        one.put(3, "III");
        one.put(4, "IV");
        one.put(5, "V");
        one.put(6, "VI");
        one.put(7, "VII");
        one.put(8, "VIII");
        one.put(9, "IX");
        one.put(0, "");
        map.put(1, one);
    }
    public static String intToRoman(int num) {
        int tmp = num / 10;
        int len = 1;
        int offset = 1;
        while (tmp > 0) {
            len++;
            offset *= 10;
            tmp /= 10;
        }
        ans.setLength(0);
        for (int i = len; i > 0; i--) {
            Map<Integer, String> numMap = map.get(i);
            int cur = num / offset % 10;
            ans.append(numMap.get(cur));
            offset /= 10;
        }
        return ans.toString();
    }


}
