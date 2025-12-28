package leetcode.study.leetcdoe201_400;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/8/2 10:56
 * https://leetcode.cn/problems/utf-8-validation/
 */
public class Leetcode393ValidUtf8 {
    public static boolean validUtf8(int[] data) {
        int n = data.length;
        for (int i = 0; i < n;) {
            int t = data[i], j = 7;
            while (j >= 0 && ((t >> j) & 1) == 1) {
                j--;
            }
            int cnt = 7 - j;
            if (cnt == 1 || cnt > 4) {
                return false;
            }
            if (i + cnt - 1 >= n) {
                return false;
            }
            for (int k = i + 1; k < i + cnt; k++) {
                if ((data[k] >> 7 & 1) == 1 && ((data[k] >> 6) & 1) == 0) {
                    continue;
                }
                return false;
            }
            if (cnt == 0) {
                i++;
            } else {
                i += cnt;
            }
        }
        return true;
    }

    public static boolean validUtf81(int[] data) {
        int n = data.length;
        int cnt = 0;
        int tmp = 0;
        for (int i = 0; i < n; i++) {
            String str = getBin(data[i]);
            if (i == 0) {
                cnt = getCnt(cnt, str);
                tmp = cnt - 1;
                if (cnt > 4 || cnt == 1) {
                    return false;
                }
            } else {
                tmp--;
                if (i < cnt && !str.startsWith("10")) {
                    return false;
                }
            }
        }
        return tmp <= 0;
    }

    private static int getCnt(int cnt, String str) {
        char[] s = str.toCharArray();
        for (int j = 0; j < s.length; j++) {
            if (j == 0 && s[j] == '0') {
                cnt = 0;
                break;
            }
            if (s[j] == '1') {
                cnt++;
            } else {
                break;
            }
        }
        return cnt;
    }

    public static String getBin(int num) {
        StringBuilder ans = new StringBuilder();
        while (num > 0) {
            if ((num & 1) == 1) {
                ans.insert(0, 1);
            } else {
                ans.insert(0, 0);
            }
            num >>= 1;
        }
        if (ans.length() < 8) {
            for (int i = 0; i < 8 - ans.length(); i++) {
                ans.insert(0, 0);
            }
        }
        return ans.toString();
    }

    public static void main(String[] args) {
        // 11100110 10001000 10010001
        int[] data = new int[]{197,130,1};
        System.out.println(validUtf8(data));
    }
}
