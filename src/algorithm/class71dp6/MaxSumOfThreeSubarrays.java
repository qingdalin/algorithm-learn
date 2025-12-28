package algorithm.class71dp6;

/**
 * @author 汪大鹏
 * @version 1.0
 * @date 2024-06-01 13:44
 * 给你一个整数数组 nums 和一个整数 k ，找出三个长度为 k 、互不重叠、且全部数字和（3 * k 项）最大的子数组，并返回这三个子数组。
 *
 * 以下标的数组形式返回结果，数组中的每一项分别指示每个子数组的起始位置（下标从 0 开始）。如果有多个结果，返回字典序最小的一个。
 */
public class MaxSumOfThreeSubarrays {
    public int[] maxSumOfThreeSubarrays(int[] nums, int k) {
        int n = nums.length;
        int[] sums = new int[n];
        for (int l = 0, r = 0, sum = 0; r < n; r++) {
            sum += nums[r];
            if (r - l + 1 == k) {
                sums[l] = sum;
                sum -= nums[l];
                l++;
            }
        }
        // prefix[i] 0 - i范围上长度为k的子数组，拥有最大累加和的子数组开头下标
        int[] prefix = new int[n];
        for (int l = 1, r = k; r < n; l++, r++) {
            if (sums[l] > sums[prefix[r - 1]]) {
                prefix[r] = l;
            } else {
                prefix[r] = prefix[r - 1];
            }
        }
        // i - n-1 范围上长度为k的子数组，拥有最大累加和的子数组以什么开头
        int[] suffix = new int[n];
        suffix[n - k] = n - k;
        for (int l = n - 1 - k; l >= 0; l--) {
            if (sums[l] >= sums[suffix[l + 1]]) {
                suffix[l] = l;
            } else {
                suffix[l] = suffix[l + 1];
            }
        }
        int a = 0, b = 0, c = 0, max = 0;
        for (int p, s, i = k, sum, j = 2 * k - 1; j < n - k; i++, j++) {
            p = prefix[i - 1];
            s = suffix[j + 1];
            sum = sums[p] + sums[i] + sums[s];
            if (sum > max) {
                max = sum;
                a = p;
                b = i;
                c = s;
            }
        }
        return new int[] {a, b, c};
    }
}
