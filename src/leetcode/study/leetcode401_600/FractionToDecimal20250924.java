package leetcode.study.leetcode401_600;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/9/24 19:40
 * https://leetcode.cn/problems/fraction-to-recurring-decimal/?envType=daily-question&envId=2025-09-24
 */
public class FractionToDecimal20250924 {
    public String fractionToDecimal(int numerator, int denominator) {
        long a = numerator, b = denominator;
        String sign = a * b < 0 ? "-" : "";
        a = Math.abs(a);
        b = Math.abs(b);
        long p = a / b;
        long r = a % b;
        if (r == 0) {
            return sign + p;
        }
        StringBuilder ans = new StringBuilder();
        ans.append(sign).append(p).append(".");
        Map<Long, Integer> map = new HashMap<>();
        map.put(r, ans.length());
        while (r > 0) {
            r *= 10;
            p = r / b;
            r %= b;
            ans.append(p);
            if (map.containsKey(r)) {
                int pos = map.get(r);
                return ans.substring(0, pos) + "(" + (ans.substring(pos)) + ")";
            }
            map.put(r, ans.length());
        }
        return ans.toString();
    }
}
