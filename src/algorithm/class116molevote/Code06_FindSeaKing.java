package algorithm.class116molevote;

import java.util.Arrays;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2024/9/17 9:32
 * https://leetcode.cn/problems/online-majority-element-in-subarray/description/
 * 设计一个数据结构，有效地找到给定子数组的 多数元素 。
 * 子数组的 多数元素 是在子数组中出现 threshold 次数或次数以上的元素。
 * 实现 MajorityChecker 类:
 * MajorityChecker(int[] arr) 会用给定的数组 arr 对 MajorityChecker 初始化。
 * int query(int left, int right, int threshold)
 * 返回子数组中的元素  arr[left...right] 至少出现 threshold 次数，如果不存在这样的元素则返回 -1
 */
public class Code06_FindSeaKing {
    class MajorityChecker {
        public int MAXN = 20001;
        public int n;
        // 将原数组改为值和下标的形式
        public int[][] nums = new int[MAXN][2];
        // 维护线段树一段范围，候选是谁
        public int[] cands = new int[MAXN << 2];
        public int[] hp = new int[MAXN << 2];
        public MajorityChecker(int[] arr) {
            n = arr.length;
            buildCnt(arr);
            buildTree(arr, 1, n, 1);
        }

        private void buildTree(int[] arr, int l, int r, int i) {
            if (l == r) {
                cands[i] = arr[l - 1];
                hp[i] = 1;
            } else {
                int mid = (l + r) >> 1;
                buildTree(arr, l, mid, i << 1);
                buildTree(arr, mid + 1, r, i << 1 | 1);
                up(i);
            }
        }

        private void up(int i) {
            int lc = cands[i << 1], lh = hp[i << 1];
            int rc = cands[i << 1 | 1], rh = hp[i << 1 | 1];
            cands[i] = lc == rc || lh > rh ? lc : rc;
            hp[i] = lc == rc ? lh + rh : Math.abs(lh - rh);
        }

        private void buildCnt(int[] arr) {
            for (int i = 0; i < n; i++) {
                nums[i][0] = arr[i];
                nums[i][1] = i;
            }
            Arrays.sort(nums, 0, n, (a, b) -> a[0] != b[0] ? a[0] - b[0] : a[1] - b[1]);
        }
        // 查询l-r范围上数值为v的个数
        // 2-6上7的个数
        // 先查询 小于7的个数，如果等于7在查询下标小于等于1的个数
        // 在查询 小于7的个数，如果等于7在查询下标小于等于6的个数
        public int cnt(int l, int r, int v) {
            return bs(r, v) - bs(l - 1, v);
        }

        private int bs(int i, int v) {
            int left = 0, right = n - 1, m;
            int find = -1;
            while (left <= right) {
                m = (left + right) / 2;
                if (nums[m][0] < v || (nums[m][0] == v && nums[m][1] <= i)) {
                    find = m;
                    left = m + 1;
                } else {
                    right = m - 1;
                }
            }
            return find + 1;
        }

        public int query(int left, int right, int threshold) {
            // 查询l-r范围的候选人数
            int[] lch = findCandidate(left + 1, right + 1, 1, n, 1);
            int cand = lch[0];
            return cnt(left, right, cand) >= threshold ? cand : -1;
        }

        private int[] findCandidate(int jobl, int jobr, int l, int r, int i) {
            if (jobl <= l && r <= jobr) {
                return new int[] {cands[i], hp[i]};
            } else {
                int mid = (l + r) >> 1;
                if (jobr <= mid) {
                    return findCandidate(jobl, jobr, l, mid, i << 1);
                }
                if (jobl > mid) {
                    return findCandidate(jobl, jobr, mid + 1, r,i << 1 | 1);
                }
                int[] lch = findCandidate(jobl, jobr, l, mid, i << 1);
                int[] rch = findCandidate(jobl, jobr, mid + 1, r, i << 1 | 1);
                int lc = lch[0], lh = lch[1];
                int rc = rch[0], rh = rch[1];
                int cand = lc == rc || lh >= rh ? lc : rc;
                int hp = lc == rc ? lh + rh : Math.abs(lh - rh);
                return new int[] {cand, hp};
            }
        }
    }
}
