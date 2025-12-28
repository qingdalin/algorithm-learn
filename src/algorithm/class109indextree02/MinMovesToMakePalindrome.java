package algorithm.class109indextree02;

import java.util.Arrays;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2024/8/25 14:57
 * 给你一个只包含小写英文字母的字符串 s 。
 *
 * 每一次 操作 ，你可以选择 s 中两个 相邻 的字符，并将它们交换。
 *
 * 请你返回将 s 变成回文串的 最少操作次数 。
 *
 * 注意 ，输入数据会确保 s 一定能变成一个回文串。
 * https://leetcode.cn/problems/minimum-number-of-moves-to-make-palindrome/description/
 */
public class MinMovesToMakePalindrome {
    public static int MAXN = 2001;
    public static int MAXV = 26;
    public static char[] s;
    public static int n;
    // 归并分治
    public static int[] arr = new int[MAXN];
    public static int[] help = new int[MAXN];


    public static int[] tree = new int[MAXN];

    //记录某个字符的出现下标
    public static int[] end = new int[MAXV];
    public static int[] pre = new int[MAXN];
    public int minMovesToMakePalindrome(String str) {
        s = str.toCharArray();
        n = s.length;
        build();
        for (int i = 0; i < n; i++) {
            push(s[i] - 'a', i + 1);
        }
        for (int i = 0, l = 1, k, r; i < n; i++, l++) {
            if (arr[l] == 0) {
                // arr[l] == 0 说明没发送交换进行处理
                r = pop(s[i] - 'a');
                if (l < r) {
                    // l < r说明需要交换，sum(l)，是l需要放置的位置
                    k = sum(l);
                    arr[l] = k;
                    arr[r] = n - k + 1;
                } else {
                    arr[r] = (1 + n) / 2;
                }
                // 右侧的树状数组减一
                add(r, -1);
            }
        }
        // 最后求arr数组的逆序对即可，本节课第一题
        return number(1, n);
    }

    private int number(int l, int r) {
        if (l == r) {
            return 0;
        }
        int m = (l + r) / 2;
        return number(l, m) + number(m + 1, r) + merge(l, m, r);
    }

    private int merge(int l, int m, int r) {
        // l......m
        // m+1....r
        int a = l, b = m + 1, ans = 0;
        for (int i = m, j = r; i >= l; i--) {
            while (j >= m + 1 && arr[i] <= arr[j]) {
                j--;
            }
            ans += j - m;
        }
        int i = l;
        while (a <= m && b <= r) {
            help[i++] = arr[a] <= arr[b] ? arr[a++] : arr[b++];
        }
        while (a <= m) {
            help[i++] = arr[a++];
        }
        while (b <= r) {
            help[i++] = arr[b++];
        }
        for (int j = l; j <= r; j++) {
            arr[j] = help[j];
        }
        return ans;
    }

    private int sum(int i) {
        int ans = 0;
        while (i > 0) {
            ans += tree[i];
            i -= lowBit(i);
        }
        return ans;
    }

    private int pop(int v) {
        int ans = end[v];
        end[v] = pre[end[v]];
        return ans;
    }

    private void build() {
        Arrays.fill(end, 0, MAXV, 0);
        Arrays.fill(tree, 0, n + 1, 0);
        Arrays.fill(arr, 0, n + 1, 0);
        for (int i = 1; i <= n; i++) {
            add(i, 1);
        }
    }

    private void add(int i, int v) {
        while (i <= n) {
            tree[i] += v;
            i += lowBit(i);
        }
    }

    private int lowBit(int i) {
        return i & -i;
    }

    private void push(int v, int i) {
        pre[i] = end[v];
        end[v] = i;
    }


}
