package algorithm.class84dp19;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2024/7/13 9:52
 * 给定一个按 非递减顺序 排列的数字数组 digits 。你可以用任意次数 digits[i] 来写的数字。
 * 例如，如果 digits = ['1','3','5']，我们可以写数字，如 '13', '551', 和 '1351315'。
 *
 * 返回 可以生成的小于或等于给定整数 n 的正整数的个数 。
 * https://leetcode.cn/problems/numbers-at-most-n-given-digit-set/description/
 */
public class AtMostNGivenDigitSet {
    public int atMostNGivenDigitSet1(String[] str, int num) {
        int tmp = num / 10;
        int len = 1;
        int offset = 1;
        // len: 数字有几位
        // offset:整数和num等位
        // num / offset % 10，可以取到num某一位数字是什么,随后len--,offset / 10
        // num : 45623
        // len : 5
        // offset: 10000
        while (tmp > 0) {
            tmp /= 10;
            len++;
            offset *= 10;
        }
        int m = str.length;
        int[] digits = new int[m];
        for (int i = 0; i < m; i++) {
            digits[i] = Integer.parseInt(str[i]);
        }
        return f1(digits, num, len, offset, 0, 0);
    }

    // free == 1: 表示可以自由选择数字
    // free == 0: 表示不能自由选择数字，需要小于等于当前num的数字
    // fix == 0: 表示前边没有选择任何数字
    // fix == 1: 表示前边已经选择数字
    private int f1(int[] digits, int num, int len, int offset, int free, int fix) {
        if (len == 0) {
            return fix == 1 ? 1 : 0;
        }
        int ans = 0;
        int cur = num / offset % 10;
        if (fix == 0) {
            // 前边没选，舍弃当前位，往下自由选择
            ans += f1(digits, num, len - 1, offset / 10, 1, 0);
        }
        if (free == 0) {
            for (int i : digits) {
                if (i < cur) {
                    ans += f1(digits, num, len - 1, offset / 10, 1, 1);
                } else if (i == cur){
                    ans += f1(digits, num, len - 1, offset / 10, 0, 1);
                } else {
                    break;
                }
            }
        } else {
             ans += digits.length * f1(digits, num, len - 1, offset / 10, 1, 1);
        }
        return ans;
    }

    private int f2(int[] digits, int num, int len, int offset, int free, int fix) {
        if (len == 0) {
            return fix == 1 ? 1 : 0;
        }
        int ans = 0;
        int cur = num / offset % 10;
        if (fix == 0) {
            // 前边没选，舍弃当前位，往下自由选择
            ans += f2(digits, num, len - 1, offset / 10, 1, 0);
        }
        if (free == 0) {
            for (int i : digits) {
                if (i < cur) {
                    ans += f2(digits, num, len - 1, offset / 10, 1, 1);
                } else if (i == cur){
                    ans += f2(digits, num, len - 1, offset / 10, 0, 1);
                } else {
                    break;
                }
            }
        } else {
            // ans += digits.length * f1(digits, num, len - 1, offset / 10, 1, 1);
            ans += Math.pow(digits.length, len);
        }
        return ans;
    }

    public int atMostNGivenDigitSet(String[] str, int num) {
        int tmp = num / 10;
        int len = 1;
        int offset = 1;
        // len: 数字有几位
        // offset:整数和num等位
        // num / offset % 10，可以取到num某一位数字是什么,随后len--,offset / 10
        // num : 45623
        // len : 5
        // offset: 10000
        while (tmp > 0) {
            tmp /= 10;
            len++;
            offset *= 10;
        }
        int m = str.length;
        int[] digits = new int[m];
        for (int i = 0; i < m; i++) {
            digits[i] = Integer.parseInt(str[i]);
        }
        int ans = 0;
        int[] cnt = new int[len];
        cnt[0] = 1;
        // cnt[i]:表示前边小于num的数字，还有i位数没选，有多少可能
        for (int i = m, k = 1; k < len; i *= m, k++) {
            cnt[k] = i;
            ans += i;
        }
        return ans + f3(digits, num, len, offset, cnt);
    }

    private int f3(int[] digits, int num, int len, int offset, int[] cnt) {
        if (len == 0) {
            return 1;
        }
        int cur = num / offset % 10;
        int ans = 0;
        for (int i : digits) {
            if (i < cur) {
                ans += cnt[len - 1];
            } else if (i == cur) {
                ans += f3(digits, num, len - 1, offset / 10, cnt);
            } else {
                break;
            }
        }
        return ans;
    }

}
