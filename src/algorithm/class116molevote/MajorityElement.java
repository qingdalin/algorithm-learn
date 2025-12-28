package algorithm.class116molevote;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2024/9/17 7:59
 * https://leetcode.cn/problems/majority-element/description/
 * 给定一个大小为 n 的数组 nums ，返回其中的多数元素。多数元素是指在数组中出现次数 大于 ⌊ n/2 ⌋ 的元素。
 *
 * 你可以假设数组是非空的，并且给定的数组总是存在多数元素。
 * 摩尔投票问题
 */
public class MajorityElement {
    public int majorityElement(int[] nums) {
        // 1.候选不存在，设置候选和血量
        // 2.候选存在，如果和候选相等，血量加1
        // 3.候选存在，和候选不等，血量减1
        int cand = 0;
        int hp = 0;
        for (int num : nums) {
            if (hp == 0) {
                cand = num;
                hp++;
            } else if (num != cand) {
                hp--;
            } else {
                hp++;
            }
        }
        //return cand;
        if (hp == 0) {
            // 如果候选不存在返回-1
            return -1;
        }
        hp = 0;
        // 最后遍历数组，确认候选是否真的大于一半
        for (int num : nums) {
            if (cand == num) {
                hp++;
            }
        }
        return hp > nums.length / 2 ? cand : -1;
    }
}
