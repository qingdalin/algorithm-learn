package leetcode.study.leetcdoe201_400;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/7/26 10:31
 * https://leetcode.cn/problems/range-sum-query-mutable/
 */
public class NumArray {
    public int[] tree;
    public int[] arr;
    public int size;
    public NumArray(int[] nums) {
        arr = nums;
        size = nums.length;
        tree = new int[size + 1];
        for (int i = 0; i < size; i++) {
            add(i + 1, nums[i]);
        }
    }

    public int lowbit(int i) {
        return i & -i;
    }

    public void add(int i, int v) {
        while (i <= size) {
            tree[i] += v;
            i += lowbit(i);
        }
    }

    public int sum(int i) {
        int ans = 0;
        while (i > 0) {
            ans += tree[i];
            i -= lowbit(i);
        }
        return ans;
    }

    public void update(int index, int val) {
        add(index + 1, -arr[index]);
        add(index + 1, val);
        arr[index] = val;
    }

    public int sumRange(int left, int right) {
        return sum(right + 1) - sum(left);
    }
}
