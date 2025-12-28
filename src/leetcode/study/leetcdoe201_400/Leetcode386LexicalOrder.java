package leetcode.study.leetcdoe201_400;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/8/2 8:57
 * https://leetcode.cn/problems/lexicographical-numbers/
 */
public class Leetcode386LexicalOrder {

    public static List<Integer> lexicalOrder3(int n) {
        List<Integer> ans = new ArrayList<>();
        for (int i = 1; i <= 9; i++) {
            f(i, n, ans);
        }
        return ans;
    }

    private static void f(int i, int limit, List<Integer> ans) {
        if (i > limit) {
            return;
        }
        ans.add(i);
        for (int j = 0; j <= 9; j++) {
            f(i * 10 + j, limit, ans);
        }
    }

    public static List<Integer> lexicalOrder1(int n) {
        List<Integer> ans = new ArrayList<>();
        List<String> list = new ArrayList<>();
        for (int i = 1; i <= n; i++) {
            list.add(i + "");
        }
        list.sort((a, b) -> a.compareTo(b));
        for (String s : list) {
            ans.add(Integer.parseInt(s));
        }
        return ans;
    }

    public static List<Integer> lexicalOrder2(int n) {
        List<Integer> ret = new ArrayList<Integer>();
        int number = 1;
        for (int i = 0; i < n; i++) {
            ret.add(number);
            if (number * 10 <= n) {
                number *= 10;
            } else {
                while (number % 10 == 9 || number + 1 > n) {
                    number /= 10;
                }
                number++;
            }
        }
        return ret;
    }

    public static void main(String[] args) {
        int n = 13;
        System.out.println(lexicalOrder2(n));
    }
}
