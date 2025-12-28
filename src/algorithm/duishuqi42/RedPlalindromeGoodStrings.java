package algorithm.duishuqi42;

/**
 * 使用 r，e，d 组成n个长度的字符串，如果只存在一个大于等于2长度的回文子串，那么这个字符串称为好串；
 * @author 汪大鹏
 * @version 1.0
 * @date 2024-01-27 13:31
 */
public class RedPlalindromeGoodStrings {
    public static void main(String[] args) {
        for (int i = 1; i < 10; i++) {
            //System.out.println(i + "个长度的字符，有" + f(new char[i], 0) + "好串");
            if (f(new char[i], 0) != f2(i)) {
                System.out.println("出错了, 第" + i + "个");
            }
        }
    }

    public static int f2(int n) {
        if (n == 1) {
            return 0;
        }
        if (n == 2) {
            return 3;
        }
        if (n == 3) {
            return 18;
        }
        return (int) (((long) (n + 1) * 6) % 1000000007);
    }
    public static int f(char[] path, int i) {
        if (i == path.length) {
            int cnt = 0;
            for (int l = 0; l < path.length; l++) {
                for (int r = l + 1; r < path.length; r++) {
                    if (isPlalindrome(path, l, r)) {
                        cnt++;
                    }
                    if (cnt > 1) {
                        return 0;
                    }
                }
            }
            return cnt == 1 ? 1 : 0;
        } else {
            int ans = 0;
            path[i] = 'r';
            ans += f(path, i + 1);
            path[i] = 'e';
            ans += f(path, i + 1);
            path[i] = 'd';
            ans += f(path, i + 1);
            return ans;
        }
    }

    private static boolean isPlalindrome(char[] path, int l, int r) {
        while (l < r) {
            if (path[l] != path[r]) {
                return false;
            }
            l++;
            r--;
        }
        return true;
    }
}
