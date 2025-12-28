package algorithm.class91greedy03;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2024/7/27 10:24
 * // 两个0和1数量相等区间的最大长度
 * // 给出一个长度为n的01串，现在请你找到两个区间
 * // 使得这两个区间中，1的个数相等，0的个数也相等
 * // 这两个区间可以相交，但是不可以完全重叠，即两个区间的左右端点不可以完全一样
 * // 现在请你找到两个最长的区间，满足以上要求
 * // 返回区间最大长度
 * // 来自真实大厂笔试，没有在线测试，对数器验证
 */
public class LongestSameZerosOnes {
    public static int len1(int[] arr) {
        Map<Integer, Map<Integer, Integer>> map = new HashMap<>();
        for (int i = 0; i < arr.length; i++) {
            int zero = 0, one = 0;
            for (int j = i; j < arr.length; j++) {
                zero += arr[j] == 0 ? 1 : 0;
                one += arr[j] == 1? 1 : 0;
                // 获取到所有子串01的长度
                map.putIfAbsent(zero, new HashMap<>());
                map.get(zero).put(one, map.get(zero).getOrDefault(one, 0) + 1);
            }
        }
        int ans = 0;
        for (Integer zero : map.keySet()) {
            for (Integer one : map.get(zero).keySet()) {
                int num = map.get(zero).get(one);
                if (num > 1) {
                    ans = Math.max(ans, zero + one);
                }
            }
        }
        return ans;
    }

    public static int len2(int[] arr) {
        int n = arr.length;
        int leftZero = 0, leftOne = 0, rightZero = 0, rightOne = 0;
        for (int i = 0; i < n; i++) {
            if (arr[i] == 0) {
                leftZero = i;
                break;
            }
        }
        for (int i = 0; i < n; i++) {
            if (arr[i] == 1) {
                leftOne = i;
                break;
            }
        }
        for (int i = n - 1; i >= 0; i--) {
            if (arr[i] == 0) {
                rightZero = i;
                break;
            }
        }
        for (int i = n - 1; i >= 0; i--) {
            if (arr[i] == 1) {
                rightOne = i;
                break;
            }
        }
        int p1 = rightZero - leftZero;
        int p2 = rightOne - leftOne;
        return Math.max(p1, p2);
    }

    public static void main(String[] args) {
        int N = 500;
        System.out.println("测试开始");
        for (int i = 0; i < 2000; i++) {
            int n = (int) (Math.random() * N + 1);
            int[] arr = randomArray(n);
            int ans1 = len1(arr);
            int ans2 = len2(arr);
            if (ans1 != ans2) {
                System.out.println("出错了");
            }
            if (i % 100 == 0) {
                System.out.println("测试到第" + i + "组");
            }
        }
        System.out.println("测试结束");
    }

    private static int[] randomArray(int n) {
        int[] ans = new int[n];
        for (int i = 0; i < n; i++) {
            ans[i] = (int) (Math.random() * 2);
        }
        return ans;
    }
}
