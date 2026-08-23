package leetcode.leetcodeweek.test2026.test516;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2026/8/23 8:14
 * https://leetcode.cn/contest/weekly-contest-516/problems/find-all-numbers-disappeared-in-an-array-ii/
 */
public class Code516_02 {
    public static List<List<Integer>> findDisappearedNumbers(int[] nums, int lower, int upper) {
        List<List<Integer>> ans = new ArrayList<>();
        int[] a = Arrays.copyOf(nums, nums.length + 2);
        a[nums.length] = lower - 1;
        a[nums.length + 1] = upper + 1;
        Arrays.sort(a);
        int l = Arrays.binarySearch(a, lower);
        if (l < 0) {
            l = ~l;
        }
        int r = Arrays.binarySearch(a, upper + 1);
        if (r < 0) {
         r = ~r;
        }
        for (int i = l; i <= r; i++) {
            if (a[i] - a[i - 1] > 1) {
                ans.add(Arrays.asList(a[i - 1] + 1, a[i] - 1));
            }
        }
        return ans;
    }

    public static List<List<Integer>> findDisappearedNumbers3(int[] nums, int lower, int upper) {
        Arrays.sort(nums);
        List<List<Integer>> ans = new ArrayList<>();
        boolean flag = true;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] >= lower && nums[i] <= upper) {
                flag = false;
            }
        }
        if (flag) {
            ans.add(Arrays.asList(lower, upper));
            return ans;
        }
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] > lower &&  nums[i] < upper && nums[i] - lower >= 1) {
                ans.add(Arrays.asList(lower, nums[i] - 1));
                break;
            }
        }
        for (int i = 0; i < nums.length - 1 ; i++) {
            int cur = nums[i];
            if (cur < lower || cur > upper) {
                continue;
            }
            int next = nums[i + 1];
            if (next <= upper && next - cur > 1) {
                ans.add(Arrays.asList(cur + 1, next - 1));
            }
        }
        for (int i = nums.length - 1; i >= 0; i--) {
            if (nums[i] > lower &&  nums[i] < upper && upper - nums[i] >= 1) {
                ans.add(Arrays.asList(nums[nums.length - 1] + 1, upper));
                break;
            }
        }
        return ans;
    }

    public static void main(String[] args) {
        int[] arr = {3,9,7};
        int lower = 1, upper = 12;
//        int[] arr = {1,1};
//        int lower = 5, upper = 7;
//        int[] arr = {2,3,5};
//        int lower = 2, upper = 3;
//        int[] arr = {2,219};
//        int lower = 80, upper = 489;
//        int[] arr = {319,565,15};
//        int lower = 418, upper = 566;
        System.out.println(findDisappearedNumbers(arr, lower, upper));
    }

    public static List<List<Integer>> findDisappearedNumbers2(int[] nums, int lower, int upper) {
        List<Integer> tmp = new ArrayList<>();
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] > lower && nums[i] < upper) {
                tmp.add(nums[i]);
            }
        }
        tmp.add(lower);
        tmp.add(upper);
        tmp.sort((a, b) -> a - b);
        List<List<Integer>> ans = new ArrayList<>();
        for (int i = 0; i < tmp.size() - 1; i++) {
            int cur = tmp.get(i);
            if (cur < lower || cur > upper) {
                continue;
            }
            int next = tmp.get(i + 1);
            if ((next == lower || next == upper || cur == lower || cur == upper) || (next <= upper && next - cur > 1)) {
                ans.add(Arrays.asList(cur == lower ? lower : cur + 1,
                    next == upper ? upper : next - 1));
            }
        }
        return ans;
    }

    public static List<List<Integer>> findDisappearedNumbers1(int[] nums, int lower, int upper) {
        Arrays.sort(nums);
        List<List<Integer>> ans = new ArrayList<>();
        if (nums[nums.length - 1] < lower || nums[0] > upper) {
            ans.add(Arrays.asList(lower, upper));
            return ans;
        }
        if (nums[0] > lower &&  nums[0] < upper && nums[0] - lower > 1) {
            ans.add(Arrays.asList(lower, nums[0] - 1));
        }
        for (int i = 0; i < nums.length - 1; i++) {
            int cur = nums[i];
            if (cur < lower || cur > upper) {
                continue;
            }
            int next = nums[i + 1];
            if (next <= upper && next - cur > 1) {
                ans.add(Arrays.asList(cur + 1, next - 1));
            }
        }
        if (nums[nums.length - 1] > lower &&  nums[nums.length - 1] < upper && upper - nums[nums.length - 1] > 1) {
            ans.add(Arrays.asList(nums[nums.length - 1] + 1, upper));
        }
        return ans;
    }
}
