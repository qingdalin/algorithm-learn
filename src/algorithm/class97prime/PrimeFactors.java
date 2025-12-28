package algorithm.class97prime;

import java.util.Arrays;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2024/8/3 10:10
 * 质因子的分解
 */
public class PrimeFactors {
    // https://www.nowcoder.com/practice/196534628ca6490ebce2e336b47b3607
    public static void main(String[] args) {
        int n = 180;
        f(n);
    }

    private static void f(int n) {
        for (int i = 2; i * i <= n; i++) {
            if (n % i == 0) {
                System.out.println(i);
                while (n % i == 0) {
                    System.out.println(i);
                    n /= i;
                }
            }
        }
        if (n > 1) {
            System.out.println(n);
        }
    }

    /**
     * https://leetcode.cn/problems/largest-component-size-by-common-factor/
     * 给定一个由不同正整数的组成的非空数组 nums ，考虑下面的图：
     *
     * 有 nums.length 个节点，按从 nums[0] 到 nums[nums.length - 1] 标记；
     * 只有当 nums[i] 和 nums[j] 共用一个大于 1 的公因数时，nums[i] 和 nums[j]之间才有一条边。
     * 返回 图中最大连通组件的大小 。
     * @param nums
     * @return
     */
    public static int MAXN = 20001;
    public static int MAXV = 100001;
    public static int n;
    // factors[x] = i 因子x的下标是i
    public static int[] factors = new int[MAXV];
    public static int[] father = new int[MAXN];
    public static int[] size = new int[MAXN];
    public int largestComponentSize(int[] nums) {
        n = nums.length;
        build();
        for (int i = 0, x; i < n; i++) {
            x = nums[i];
            for (int j = 2; j * j <= x; j++) {
                if (x % j == 0) {
                    if (factors[j] == -1) {
                        factors[j] = i;
                    } else {
                        union(factors[j], i);
                    }
                    while (x % j == 0) {
                        x /= j;
                    }
                }
            }
            if (x > 1) {
                if (factors[x] == -1) {
                    factors[x] = i;
                } else {
                    union(factors[x], i);
                }
            }
        }
        return maxSize();
    }

    public static int find(int i) {
        if (father[i] != i) {
            father[i] = find(father[i]);
        }
        return father[i];
    }

    public static void union(int x, int y) {
        int fx = find(x);
        int fy = find(y);
        if (fx != fy) {
            father[fx] = fy;
            size[fy] += size[fx];
        }
    }

    public static int maxSize() {
        int ans = 0;
        for (int i = 0; i < n; i++) {
            ans = Math.max(ans, size[i]);
        }
        return ans;
    }

    private void build() {
        for (int i = 0; i < n; i++) {
            father[i] = i;
            size[i] = 1;
        }
        Arrays.fill(factors, -1);
    }
}
