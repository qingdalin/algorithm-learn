package algorithm.class117;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2024/9/20 22:13
 * // 出现次数最多的数有几个
 * // 给定一个长度为n的数组arr，该数组一定是有序的
 * // 一共有m次查询，每次查询arr[l~r]上出现次数最多的数有几个
 * // 对数器验证
 * https://www.luogu.com.cn/problem/UVA11235
 * https://vjudge.net/problem/UVA-11235
 */
public class Code04_FrequentValues1 {
    public static int MAXN = 100001;
    public static int LIMIT = 17;
    public static int[] arr = new int[MAXN];
    public static int[] log2 = new int[MAXN];
    // 原数组对应的值，是桶的编号
    public static int[] bucket = new int[MAXN];
    // 桶编号对应的原数据左右边界
    public static int[] left = new int[MAXN];
    public static int[] right = new int[MAXN];
    // 维护的是桶的左右编号，切记
    public static int[][] stmax = new int[MAXN][LIMIT];

    public static void build(int n) {
        // 设置一个超出题目给定范围的值
        arr[0] = -233333;
        int cnt = 0;
        for (int i = 1; i <= n; i++) {
            // 数组 - 1 1 1 4 6
            // 下标 0 1 2 3 4 5
            // 桶号 0 1 1 1 2 3
            if (arr[i - 1] != arr[i]) {
                // 设置前一个桶的右边界和下一个桶的左边界
                right[cnt] = i - 1;
                // 首先设置当前第一个桶的左边界
                left[++cnt] = i;
            }
            bucket[i] = cnt;
        }
        right[cnt] = n;
        log2[0] = -1;
        for (int i = 1; i <= n; i++) {
            log2[i] = log2[i >> 1] + 1;
            stmax[i][0] = right[i] - left[i] + 1;
        }
        for (int p = 1; p <= log2[n]; p++) {
            for (int i = 1; i + (1 << (p - 1)) <= n; i++) {
                stmax[i][p] = Math.max(stmax[i][p - 1], stmax[i + (1 << (p - 1))][p - 1]);
            }
        }
    }

    public static int query(int l, int r) {
        if (l > r) {
            int tmp = l;
            l = r;
            r = tmp;
        }
        // 桶的左右编号
        int lbucket = bucket[l];
        int rbucket = bucket[r];
        if (lbucket == rbucket) {
            // 是一个桶，直接返回区间个数
            return r - l + 1;
        }
        int a = right[lbucket] - l + 1, b = r - left[rbucket] + 1, c = 0;
        if (lbucket + 1 < rbucket) {
            // 如果桶编号有中间的值
            int from = lbucket + 1, to = rbucket - 1, p = log2[to - from + 1];
            c = Math.max(stmax[from][p], stmax[to - (1 << p) + 1][p]);
        }
        return Math.max(Math.max(a, b), c);
    }

    public static void main(String[] args) {
        System.out.println("测试开始");
        int n = 10000;
        int v = 100;
        int m = 5000;
        randomArray(n, v);
        build(n);
        for (int i = 1, l, r; i <= m; i++) {
            l = (int) (Math.random() * n) + 1;
            r = (int) (Math.random() * n) + 1;
            if (query(l, r) != checkQuery(l, r)) {
                System.out.println("出错了!");
            }
        }
        System.out.println("测试结束");
    }

    private static int checkQuery(int l, int r) {
        if (l > r) {
            int tmp = l;
            l = r;
            r = tmp;
        }
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = l; i <= r; i++) {
            map.put(arr[i], map.getOrDefault(arr[i], 0) + 1);
        }
        int ans = 0;
        for (Integer v : map.values()) {
            ans = Math.max(ans, v);
        }
        return ans;
    }

    private static void randomArray(int n, int v) {
        for (int i = 1; i <= n; i++) {
            arr[i] = (int) (Math.random() * 2 * v) - v;
        }
        Arrays.sort(arr, 1, n + 1);
    }
}
