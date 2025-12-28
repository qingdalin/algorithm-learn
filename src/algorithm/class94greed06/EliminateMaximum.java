package algorithm.class94greed06;

import java.util.Arrays;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2024/7/28 9:28
 * 你正在玩一款电子游戏，在游戏中你需要保护城市免受怪物侵袭。
 * 给定一个 下标从 0 开始 且大小为 n 的整数数组 dist ，其中 dist[i] 是第 i 个怪物与城市的 初始距离（单位：千米）。
 *
 * 怪物以 恒定 的速度走向城市。每个怪物的速度都以一个长度为 n 的整数数组 speed 表示，
 * 其中 speed[i] 是第 i 个怪物的速度（单位：千米/分）。
 *
 * 你有一种武器，一旦充满电，就可以消灭 一个 怪物。但是，武器需要 一分钟 才能充电。
 * 武器在游戏开始时是充满电的状态，怪物从 第 0 分钟 时开始移动。
 *
 * 一旦任一怪物到达城市，你就输掉了这场游戏。如果某个怪物 恰好 在某一分钟开始时到达城市（距离表示为0），
 * 这也会被视为 输掉 游戏，在你可以使用武器之前，游戏就会结束。
 *
 * 返回在你输掉游戏前可以消灭的怪物的 最大 数量。如果你可以在所有怪物到达城市前将它们全部消灭，返回  n
 * https://leetcode.cn/problems/eliminate-maximum-number-of-monsters/description/
 */
public class EliminateMaximum {
    public int eliminateMaximum(int[] dist, int[] speed) {
        int n = dist.length;
        int[] time = new int[n];
        for (int i = 0; i < n; i++) {
            time[i] = (dist[i] + speed[i] - 1) / speed[i];
        }
        Arrays.sort(time);
        // 到达时间向上取整
        for (int i = 0; i < n; i++) {
            if (time[i] <= i) {
                return i;
            }
        }
        return n;
    }
}
