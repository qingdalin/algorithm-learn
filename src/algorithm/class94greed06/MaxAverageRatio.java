package algorithm.class94greed06;

import java.util.PriorityQueue;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2024/7/28 10:29
 * 一所学校里有一些班级，每个班级里有一些学生，现在每个班都会进行一场期末考试。
 * 给你一个二维数组 classes ，其中 classes[i] = [passi, totali] ，
 * 表示你提前知道了第 i 个班级总共有 totali 个学生，其中只有 passi 个学生可以通过考试。
 *
 * 给你一个整数 extraStudents ，表示额外有 extraStudents 个聪明的学生，
 * 他们 一定 能通过任何班级的期末考。你需要给这 extraStudents 个学生每人都安排一个班级，使得 所有 班级的 平均 通过率 最大 。
 *
 * 一个班级的 通过率 等于这个班级通过考试的学生人数除以这个班级的总人数。平均通过率 是所有班级的通过率之和除以班级数目。
 *
 * 请你返回在安排这 extraStudents 个学生去对应班级后的 最大 平均通过率。与标准答案误差范围在 10-5 以内的结果都会视为正确结果。
 * https://leetcode.cn/problems/maximum-average-pass-ratio/description/
 */
public class MaxAverageRatio {
    public double maxAverageRatio(int[][] classes, int extraStudents) {
        // 按照通过率提升最大的排序
        // a : 通过学生
        // b : 总学生
        // c : 再来一个提升的通过率 (a + 1) / (b + 1) - (a / b)
        PriorityQueue<double[]> heap = new PriorityQueue<>((c1, c2) -> c1[2] >= c2[2] ? -1 : 1);
        for (int[] c : classes) {
            double a = c[0];
            double b = c[1];
            heap.add(new double[] {a, b, (a + 1) / (b + 1) - (a / b)});
        }
        while (extraStudents-- > 0) {
            double[] cur = heap.poll();
            double a = cur[0] + 1;
            double b = cur[1] + 1;
            heap.add(new double[] {a, b, (a + 1) / (b + 1) - (a / b)});
        }
        double ans = 0.0;
        while (!heap.isEmpty()) {
            double[] cur = heap.poll();
            ans += cur[0] / cur[1];
        }
        return ans / classes.length;
    }
}
