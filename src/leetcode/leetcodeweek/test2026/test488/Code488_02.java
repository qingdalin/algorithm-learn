package leetcode.leetcodeweek.test2026.test488;

import com.sun.jmx.remote.internal.ArrayQueue;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.Objects;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2026/2/8 10:35
 * https://leetcode.cn/contest/weekly-contest-488/problems/merge-adjacent-equal-elements/
 */
public class Code488_02 {
    public static List<Long> mergeAdjacent(int[] nums) {
        int n = nums.length;
        List<Long> ans = new ArrayList<>(n);

        for (int i = 0; i < n; i++) {
            ans.add((long) nums[i]);
            while (ans.size() > 1 && Objects.equals(ans.get(ans.size() - 2), ans.get(ans.size() - 1))) {
                long cur = ans.remove(ans.size() - 2) + ans.remove(ans.size() - 1);
                ans.add(cur);
            }
        }
        return ans;
    }

    public static void main(String[] args) {
//        int[] arr = {3,7,5};
//        int[] arr = {3,1,1,2};
//        int[] arr = {2,2,4};
//        int[] arr = {2,1,1,2};
        int[] arr = {1,1,2,4,8,16,32,64,128,256,512,1024,2048,4096,8192,16384,32768,65536};
        System.out.println(mergeAdjacent(arr));
    }
}
