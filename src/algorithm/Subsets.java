package algorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author 汪大鹏
 * @version 1.0
 * @date 2024-01-14 13:26
 */
public class Subsets {
    public static void main(String[] args) {
        int[] nums = {1,1,1,1,2,2,2,4,4};
        List<List<Integer>> lists = subsetsWithDup(nums);
        System.out.println("lists = " + lists);
    }
    public static List<List<Integer>> subsetsWithDup(int[] nums) {
        List<List<Integer>> ans = new ArrayList<>();
        int[] path = new int[nums.length];
        Arrays.sort(nums);
        f(nums, 0, path, 0, ans);
        return ans;
    }
    public static void f(int[] nums, int i, int[] path, int size, List<List<Integer>> ans) {
        if (i == nums.length) {
            List<Integer> list = new ArrayList<>();
            for(int j = 0; j < size; j++) {
                list.add(path[j]);
            }
            ans.add(list);
        } else {
            int j = i + 1;
            while (j < nums.length && nums[j] == nums[i]) {
                j++;
            }
            f(nums, j, path, size, ans);

            for(; i < j; i++) {
                path[size++] = nums[i];
                f(nums, j, path, size, ans);
            }

        }
    }
}
