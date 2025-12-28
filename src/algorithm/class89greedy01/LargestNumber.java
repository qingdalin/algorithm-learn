package algorithm.class89greedy01;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2024/7/21 10:21
 * 给定一组非负整数 nums，重新排列每个数的顺序（每个数不可拆分）使之组成一个最大的整数。
 *
 * 注意：输出结果可能非常大，所以你需要返回一个字符串而不是整数。
 * https://leetcode.cn/problems/largest-number/description/
 */
public class LargestNumber {
    public String largestNumber(int[] nums) {
        String[] strs = new String[nums.length];
        for (int i = 0; i < strs.length; i++) {
            strs[i] = String.valueOf(nums[i]);
        }
        if (strs[0].equals("0")) {
            return "0";
        }
        Arrays.sort(strs, (a, b) -> (b + a).compareTo(a + b));
        StringBuilder sb = new StringBuilder();
        for (String str : strs) {
            sb.append(str);
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        int n = 9; // 数组长度
        int m = 5; // 字符长度
        int v = 4; // 字符种类
        System.out.println("测试开始");
        for (int i = 1; i <= 2000; i++) {
            String[] strs = randomStrArray(n, m, v);
            String ans1 = way1(strs);
            String ans2 = way2(strs);
            if (!ans1.equals(ans2)) {
                System.out.println("出错了");
            }
            if (i % 100 == 0) {
                System.out.println("测试到第"+ i +"组");
            }
         }
        System.out.println("测试结束");
    }

    private static String[] randomStrArray(int n, int m, int v) {
        String[] ans = new String[(int) (Math.random() * n) + 1];
        for (int i = 0; i < ans.length; i++) {
            ans[i] = randomString(m, v);
        }
        return ans;
    }

    private static String randomString(int m, int v) {
        char[] ans = new char[m];
        for (int i = 0; i < m; i++) {
            ans[i] = (char) ((Math.random() * v + 1) + 'a');
        }
        return String.valueOf(ans);
    }

    public static String way2(String[] strs) {
        Arrays.sort(strs, (a, b) -> (a + b).compareTo(b + a));
        StringBuilder sb = new StringBuilder();
        for (String str : strs) {
            sb.append(str);
        }
        return sb.toString();
    }

    public static String way1(String[] strs) {
        List<String> ans = new ArrayList<>();
        f(strs, 0, ans);
        ans.sort((a, b) -> a.compareTo(b));
        return ans.get(0);
    }

    private static void f(String[] strs, int i, List<String> ans) {
        if (i == strs.length) {
            StringBuilder sb = new StringBuilder();
            for (String str : strs) {
                sb.append(str);
            }
            ans.add(sb.toString());
        } else {
            for (int j = i; j < strs.length; j++) {
                swap(strs, i, j);
                f(strs, i + 1, ans);
                swap(strs, i, j);
            }
        }
    }

    private static void swap(String[] strs, int i, int j) {
        String tem = strs[i];
        strs[i] = strs[j];
        strs[j] = tem;
    }
}
