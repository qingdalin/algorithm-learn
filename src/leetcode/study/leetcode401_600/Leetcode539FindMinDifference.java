package leetcode.study.leetcode401_600;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/9/23 21:00
 * https://leetcode.cn/problems/minimum-time-difference/
 */
public class Leetcode539FindMinDifference {
    public static int findMinDifference(List<String> timePoints) {
        List<Integer> list = new ArrayList<>();
        for (String time : timePoints) {
            list.add(timeToMin(time));
        }
        list.sort((a, b) -> a - b);
        int min = Integer.MAX_VALUE;
        int size = list.size();
        for (int i = 1; i < size; i++) {
//            min = Math.min(min, list.get(i - 1) + 1440 - list.get(i));
            min = Math.min(min, list.get(i)- list.get(i - 1));
        }
//        min = Math.min(min, list.get(size - 1) - list.get(0));
        min = Math.min(min, list.get(0) + 1440 - list.get(size - 1));
        return min;
    }

    public static int getMinutes(String t) {
        return ((t.charAt(0) - '0') * 10 + (t.charAt(1) - '0')) * 60 + (t.charAt(3) - '0') * 10 + (t.charAt(4) - '0');
    }


    public static int timeToMin(String time) {
        String[] arr = time.split(":");
        return Integer.parseInt(arr[0]) * 60 + Integer.parseInt(arr[1]);
    }

    public static void main(String[] args) {
        String[] arr = {"02:39","10:26","21:43"};
        System.out.println(findMinDifference(Arrays.asList(arr)));
    }
}
