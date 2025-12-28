package algorithm.class114segmenttree05;

import java.util.Arrays;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2024/9/15 7:29
 * https://leetcode.cn/problems/count-integers-in-intervals/description/
 */
public class Code02_CountIntervals {
    class CountIntervals {
        public int LIMIT = 700000;
        int[] left = new int[LIMIT];
        int[] right = new int[LIMIT];
        int[] sum = new int[LIMIT];
        int cnt = 1;
        int n = 1000000000;
        public CountIntervals() {
            Arrays.fill(left, 1, cnt + 1, 0);
            Arrays.fill(right, 1, cnt + 1, 0);
            Arrays.fill(sum, 1, cnt + 1, 0);
            cnt = 1;
        }

        public void up(int i, int l, int r) {
            sum[i] = sum[l] + sum[r];
        }

        public void add(int left, int right) {
            // 设置为1
            setOne(left, right, 1, n, 1);
        }

        private void setOne(int jobl, int jobr, int l, int r, int i) {
            if (sum[i] == r - l + 1) {
                // 题目本身只有更新为1的操作，如果本次范围全部都是1，直接剪枝返回
                return;
            }
            if (jobl <= l && r <= jobr) {
                // 全部包住，也直接设置
                sum[i] = r - l + 1;
            } else {
                int mid = (l + r) >> 1;
                if (jobl <= mid) {
                    if (left[i] == 0) {
                        left[i] = ++cnt;
                    }
                    setOne(jobl, jobr, l, mid, left[i]);
                }
                if (jobr > mid) {
                    if (right[i] == 0) {
                        right[i] = ++cnt;
                    }
                    setOne(jobl, jobr, mid + 1, r, right[i]);
                }
                up(i, left[i], right[i]);
            }
        }

        public int count() {
            return sum[1];
        }
    }


}
