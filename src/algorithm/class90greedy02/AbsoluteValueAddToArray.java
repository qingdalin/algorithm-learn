package algorithm.class90greedy02;

import algorithm.ArrayUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2024/7/22 19:43
 * // 加入差值绝对值直到长度固定
 * // 给定一个非负数组arr，计算任何两个数差值的绝对值
 * // 如果arr中没有，都要加入到arr里，但是只加一份
 * // 然后新的arr继续计算任何两个数差值的绝对值，
 * // 如果arr中没有，都要加入到arr里，但是只加一份
 * // 一直到arr大小固定，返回arr最终的长度
 * // 来自真实大厂笔试，没有在线测试，对数器验证
 */
public class AbsoluteValueAddToArray {
    public static int way1(int[] nums) {
        List<Integer> list = new ArrayList<>();
        Set<Integer> set = new HashSet<>();
        for (Integer num : nums) {
            list.add(num);
            set.add(num);
        }
        while (!finish(list, set));
        return list.size();
    }

    private static boolean finish(List<Integer> list, Set<Integer> set) {
        int len = list.size();
        for (int i = 0; i < len; i++) {
            for (int j = i + 1; j < len; j++) {
                int num = Math.abs(list.get(i) - list.get(j));
                if (!set.contains(num)) {
                    list.add(num);
                    set.add(num);
                }
            }
        }
        return len == list.size();
    }


    public static int way2(int[] nums) {
        int gcd = 0;
        int max = 0;
        for (int num : nums) {
            if (num != 0) {
                gcd = num;
            }
            max = Math.max(num, max);
        }
        if (gcd == 0) {
            return nums.length;
        }
        Map<Integer, Integer> cnts = new HashMap<>();
        for (int num : nums) {
            if (num != 0) {
                // 求最大公约数
                gcd = gcd(gcd, num);
            }
            // 统计数字出现的词频
            cnts.put(num, cnts.getOrDefault(num, 0) + 1);
        }
        int ans = max / gcd;
        int maxCnt = 0;
        for (Integer key : cnts.keySet()) {
            if (key != 0) {
                ans += cnts.get(key) - 1;
            }
            maxCnt = Math.max(maxCnt, cnts.get(key));
        }
        // 如果本身含有0，那么加上0的数量，如果不含0，最大词频大于等于2，加1个0
        ans += cnts.getOrDefault(0, maxCnt > 1 ? 1 : 0);
        return ans;
    }

    public static int gcd(int a, int b) {
        return b == 0 ? a : gcd(b, a % b);
    }

    public static void main(String[] args) {
        int N = 50;
        int V = 100;
        System.out.println("测试开始了");
        for (int i = 0; i < 2000; i++) {
            int n = (int) (Math.random() * N + 1);
            int[] nums = ArrayUtil.randomPositiveNumArray(n, V);
            int ans1 = way1(nums);
            int ans2 = way2(nums);
            if (ans1 != ans2) {
                System.out.println("出错了");
            }
        }
        System.out.println("测试结束了");
    }
}
