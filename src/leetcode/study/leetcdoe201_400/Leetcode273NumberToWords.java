package leetcode.study.leetcdoe201_400;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/7/21 20:00
 * https://leetcode.cn/problems/integer-to-english-words/
 */
public class Leetcode273NumberToWords {
    public static String[] single = {"", "One", "Two", "Three", "Four", "Five", "Six", "Seven","Eight", "Nine"};
    public static String[] teen = {"Ten", "Eleven", "Twelve", "Thirteen", "Fourteen", "Fifteen", "Sixteen", "Seventeen", "Eighteen", "Nineteen"};
    public static String[] ten = {"", "Ten", "Twenty", "Thirty", "Forty", "Fifty", "Sixty", "Seventy", "Eighty", "Ninety"};
    public static String[] thousand = {"", "Thousand", "Million", "Billion"};
    public static String numberToWords(int num) {
        if (num == 0) {
            return "Zero";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 3, cur, offset = 1000000000; i >= 0; i--, offset /= 1000) {
            cur = num / offset;
            if (cur != 0) {
                //num -= cur * offset;
                num %= offset;
                sb.append(f(cur)).append(thousand[i]).append(" ");
            }
        }
        return sb.toString();
    }

    private static String f(int cur) {
        StringBuilder sb = new StringBuilder();
        int hundred = cur / 100;
        cur %= 100;
        if (hundred != 0) {
            sb.append(single[hundred]).append(" Hundred ");
        }
        int tenNum = cur / 10;

        if (tenNum >= 2) {
            sb.append(ten[tenNum]).append(" ");
            cur %= 10;
        }
        if (cur > 0 && cur < 10) {
            sb.append(single[cur]).append(" ");
        } else if (cur >= 10) {
            sb.append(teen[cur - 10]).append(" ");
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        System.out.println(numberToWords(20));
    }
}
