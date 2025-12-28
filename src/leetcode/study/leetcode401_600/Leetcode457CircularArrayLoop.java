package leetcode.study.leetcode401_600;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/8/27 19:33
 * https://leetcode.cn/problems/circular-array-loop/
 */
public class Leetcode457CircularArrayLoop {
    public static boolean circularArrayLoop(int[] arr) {
        int n = arr.length;
        boolean ans = false;
        int[] vis = new int[n];
        for (int start = 0, idx = 1; start < n; start++, idx++) {
            // 0 1 2 3 4
            // -1 3 4 2 5
            if (vis[start] != 0) {
                continue;
            }
            int cur = start;
            while (true) {
                int next = ((cur + arr[cur]) % n + n) % n;
                if (next == cur) {
                    break;
                }
                if (vis[next] != 0) {
                    if (vis[next] != idx) {
                        break;
                    } else {
                        return true;
                    }
                }
                if (arr[start] * arr[next] < 0) {
                    break;
                }
                vis[next] = idx;
                cur = next;
            }
        }
        return false;
    }

    public static boolean circularArrayLoop1(int[] arr) {
        int n = arr.length;
        boolean ans = false;
        for (int i = 0; i < n; i++) {
            // 0 1 2 3 4
            // -1 3 4 2 5
            int k = 1;
            boolean flag = true;
            for (int j = (i + arr[i] + n * 1000)  % n, lne = 0;; j = (j + arr[j] + n * 1000)  % n, k++, lne++) {
                if (j == i) {
                    break;
                }
                if (lne == 2 * n || arr[i] * arr[j] < 0) {
                    flag = false;
                    break;
                }
            }
            if (k > 1 && flag) {
                ans = true;
                break;
            }
        }
        return ans;
    }

    public static void main(String[] args) {
        int[] arr = {2,-1,1,2,2};
//        int[] arr = {-2,-3,-9};
//        int[] arr = {-8,-1,1,7,2};
        System.out.println(circularArrayLoop(arr));
    }
}
