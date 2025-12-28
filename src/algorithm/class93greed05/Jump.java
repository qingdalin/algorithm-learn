package algorithm.class93greed05;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2024/7/27 19:58
 * 给定一个长度为 n 的 0 索引整数数组 nums。初始位置为 nums[0]。
 *
 * 每个元素 nums[i] 表示从索引 i 向前跳转的最大长度。换句话说，如果你在 nums[i] 处，你可以跳转到任意 nums[i + j] 处:
 *
 * 0 <= j <= nums[i]
 * i + j < n
 * 返回到达 nums[n - 1] 的最小跳跃次数。生成的测试用例可以到达 nums[n - 1]。
 * https://leetcode.cn/problems/jump-game-ii/description/
 */
public class Jump {

    public int jump(int[] nums) {
        int cur = 0; // 当前步最大跨到哪
        int next = 0; // 下一步最大跨到哪
        int ans = 0; // 步数
        for (int i = 0; i < nums.length; i++) {
            if (cur < i) {
                // 当前步数跨不到i
                ans++;
                cur = next; // x下一步的最大赋值cur
            }
            next = Math.max(next, i + nums[i]); // 下一步的最大等于i + 每一步能走的 最大值
        }
        return ans;
    }
}
