package leetcode.study.leetcdoe201_400;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/8/2 17:17
 * https://leetcode.cn/problems/integer-replacement/
 */
public class Leetcode397IntegerReplacement {
    public int integerReplacement(int n) {
        Map<Long, Integer> dp = new HashMap<>();
        return f(n, dp);
    }

    private int f(long num, Map<Long, Integer> dp) {
        if (num == 1) {
            return 0;
        }
        if (dp.containsKey(num)) {
            return dp.get(num);
        }
        int ans = (num & 1) == 0 ? f(num / 2, dp) :
            Math.min(f(num + 1, dp), f(num - 1, dp));
        dp.put(num, ans + 1);
        return ans + 1;
    }

    public int integerReplacement1(int n) {
        int cnt = 0;
        long num = n;
        while (num > 1) {
            cnt++;
            if ((num & 1) == 1) {
                // 如果小于等于3就减1
                if (num != 3 && ((num >> 1) & 1) == 1) {
                    // num不等于3，且第1位是1，就++,至少减少两个1
                    num++;
                } else {
                    // 没有有连续两个11或者等于3，就--
                    num--;
                }
//                if (num == 3) {
//                    num--;
//                } else {
//                    // 奇数如果有连续两个11就+1,否则-1
//                    if (((num >> 1) & 1) == 1) {
//                        num++;
//                    } else {
//                        num--;
//                    }
//                }
            } else {
                // 偶数初一2
                num /= 2;
            }
        }
        return cnt;
    }
}
