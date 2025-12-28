package algorithm.prefixtree44And45;

import java.util.HashSet;
import java.util.Set;

/**
 * https://leetcode.cn/problems/maximum-xor-of-two-numbers-in-an-array/
 * 寻找数组中最个数最大异或值
 * @author 汪大鹏
 * @version 1.0
 * @date 2024-01-28 17:56
 */
public class FindMaximumXOR {
    Set<Integer> set = new HashSet<>();
    public static int MAX = 150001;
    public static int[][] tree = new int[MAX][2];
    public static int cnt;
    public static int high;
    public int findMaximumXOR(int[] nums) {
        build(nums);
        int ans = 0;
        for (int num : nums) {
            ans = Math.max(ans, maxOrx(num));
        }
        clear();
        return ans;
    }

    private void clear() {
        for (int i = 0; i < cnt; i++) {
            tree[i][0] = 0;
            tree[i][1] = 0;
        }
    }

    private int maxOrx(int num) {
        // 最终异或结果
        int ans = 0;
        // 前缀树节点编号
        int cur = 1;
        for (int i = high, status, want; i >= 0; i--) {
            // num第i为的状态
            status = (num >> i) & 1;
            // num第i为希望遇到的状态
            want = status ^ 1;
            if (tree[cur][want] == 0) { // 询问前缀树能不能达成
                // 不能达成，want复原
                want ^= 1;
            }
            // want变为真的往下走
            ans |= (status ^ want) << i;
            cur = tree[cur][want];
        }
        return ans;
    }

    private void build(int[] nums) {
        cnt = 1;
        int max = Integer.MIN_VALUE;
        for (int num : nums) {
            max = Math.max(num, max);
        }
        high = 31 - Integer.numberOfLeadingZeros(max);
        for (int num : nums) {
            insert(num);
        }
    }
    public static void insert(int num) {
        int cur = 1;
        for (int i = high, path; high >= 0; i--) {
            path = (num >> i) & 1;
            if (tree[cur][path] == 0) {
                tree[cur][path] = ++cnt;
            }
            cur = tree[cur][path];
        }
    }



    private int hashSol(int[] nums) {
        int max = Integer.MIN_VALUE;
        for (int num : nums) {
            max = Math.max(num, max);
        }
        int ans = 0;
        for (int high = 31 - Integer.numberOfLeadingZeros(max); high >= 0; high--) {
            set.clear();
            int better = ans | (1 << high);
            for (int num : nums) {
                num = (num >> high) << high;
                set.add(num);
                if (set.contains(better ^ num)){
                    ans = better;
                    break;
                }
            }
        }
        return ans;
    }
}
