package algorithm.class91greedy03;

import java.util.List;
import java.util.TreeSet;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2024/7/24 20:11
 * 你有 k 个 非递减排列 的整数列表。找到一个 最小 区间，使得 k 个列表中的每个列表至少有一个数包含在其中。
 *
 * 我们定义如果 b-a < d-c 或者在 b-a == d-c 时 a < c，则区间 [a,b] 比 [c,d] 小。
 * https://leetcode.cn/problems/smallest-range-covering-elements-from-k-lists/description/
 */
public class SmallestRange {
    public int[] smallestRange(List<List<Integer>> nums) {
        int k = nums.size();
        // 有序表只会保留一个相同的值
        // 如果v相等，那么按照来自数组下标升序即可
        TreeSet<Node> set = new TreeSet<>((a, b) -> a.v != b.v ? (a.v - b.v) : (a.i - b.i));
        for (int i = 0; i < k; i++) {
            // 将每个数组的第一个元素加入
            set.add(new Node(nums.get(i).get(0), i, 0));
        }
        int a = 0, b = 0; // 记录开头和结尾
        Node min, max;
        int r = Integer.MAX_VALUE; // 范围区间
        while (set.size() == k) {
            // 如果size不等于k就结束
            max = set.last(); //最大值
            min = set.pollFirst(); // 最小值并弹出，下一个进来
            if (max.v - min.v < r) {
                // 小于当前r的范围更新
                r = max.v - min.v;
                a = min.v;
                b = max.v;
            }
            if (min.j + 1 < nums.get(min.i).size()) {
                set.add(new Node(nums.get(min.i).get(min.j + 1), min.i, min.j + 1));
            }
        }
        return new int[] {a, b};
    }

    class Node {
        int v; // 值
        int i; // 来自哪个数组
        int j; // 来自i号数组的哪个下标

        public Node(int v, int i, int j) {
            this.v = v;
            this.i = i;
            this.j = j;
        }
    }
}
