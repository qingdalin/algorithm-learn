package algorithm.class116molevote;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2024/9/17 8:48
 * https://leetcode.cn/problems/majority-element-ii/description/
 * 给定一个大小为 n 的整数数组，找出其中所有出现超过 ⌊ n/K ⌋ 次的元素。
 * 水王数不超过k-1
 */
public class MajorityElement2 {
    public static void main(String[] args) {
        MajorityElement2 majorityElement2 = new MajorityElement2();
        majorityElement2.majorityElement(new int[] {3, 2, 3});
    }
    public List<Integer> majorityElement(int[] nums) {
        return majority(nums, 3);
    }

    private List<Integer> majority(int[] nums, int k) {
        // 创建词频表，比k少一个
        int[][] cand = new int[--k][2];
        for (int num : nums) {
            update(cand, num, k);
        }
        List<Integer> ans = new ArrayList<>();
        collect(ans, cand, nums, k);
        return ans;
    }

    private void collect(List<Integer> ans, int[][] cand, int[] nums, int k) {
        int n = nums.length;
        for (int i = 0, cur, real = 0; i < k; i++) {
            if (cand[i][1] > 0) {
                cur = cand[i][0];
                real = 0;
                for (int j = 0; j < n; j++) {
                    if (nums[j] == cur) {
                        real++;
                    }
                }
                if (real > n / (k + 1)) {
                    // k已经减一，这里要加1
                    ans.add(cur);
                }
            }
        }
    }

    private void update(int[][] cand, int num, int k) {
        for (int i = 0; i < k; i++) {
            // 在候选表中血量大于0，说明真的在候选表
            // 词频表满了，判断是否有数等于该num,有的话加1结束
            if (cand[i][0] == num && cand[i][1] > 0) {
                cand[i][1]++;
                return;
            }
        }
        for (int i = 0; i < k; i++) {
            if (cand[i][1] == 0) {
                // 如果词频表没满,设置为当前num结束
                cand[i][0] = num;
                cand[i][1]++;
                return;
            }
        }

        for (int i = 0; i < k; i++) {
            // 词频表满了，并且没有收集的数等于num
            // 所有数词频减一
            if (cand[i][1] > 0) {
                cand[i][1]--;
            }
        }
    }
}
