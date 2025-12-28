package leetcode.study.leetcode401_600;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/8/16 15:15
 * https://leetcode.cn/problems/string-compression/
 */
public class Leetcode443Compress {
    public static int compress(char[] arr) {
        int ans = 0, n = arr.length;
        for (int i = 0; i < n;) {
            int cnt = 0;
            char c = arr[i];
            while (i < n && c == arr[i]) {
                cnt++;
                i++;
            }
            arr[ans++] = c;
            if (cnt > 1) {
                char[] s = String.valueOf(cnt).toCharArray();
                for (int j = 0; j < s.length; j++) {
                    arr[ans++] = s[j];
                }
            }
        }
        return ans;
    }

    public static int compress1(char[] arr) {
        int ans = 0, n = arr.length;
        for (int l = 0, r = -1; r < n && l < n; l = r + 1) {
            int cnt = 0;
            char c = arr[l];
            while (r + 1 < n && c == arr[r + 1]) {
                cnt++;
                r++;
            }
            arr[ans++] = c;
            if (cnt > 1) {
                char[] s = String.valueOf(cnt).toCharArray();
                for (int i = 0; i < s.length; i++) {
                    arr[ans++] = s[i];
                }
            }
        }
        return ans;
    }

    public static void main(String[] args) {
        // char[] arr = {'a'};
        char[] arr = {'a', 'a', 'b', 'b', 'c', 'c', 'c'};
        int res = compress(arr);
        System.out.println(res);
        for (int i = 0; i < res; i++) {
            System.out.print(arr[i]);
        }
        System.out.println();
    }
}
