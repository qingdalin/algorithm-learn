package leetcode.study.leetcode001_200;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/7/12 16:36
 * https://leetcode.cn/problems/combination-sum-iii/
 */
public class Leectcdoe216CombinationSum3 {
    public static int k, n;
    public List<List<Integer>> combinationSum3(int kNum, int nNum) {
        k = kNum;
        n = nNum;
        List<List<Integer>> ans = new ArrayList<>();
        List<Integer> path = new ArrayList<>();
        f(1, 0, path, ans);
        return ans;
    }

    private void f(int i,  int sum, List<Integer> path, List<List<Integer>> ans) {
        if (i > 9) {
            if (path.size() == k && sum == n) {
                ans.add(new ArrayList<>(path));
            }
        } else {
            f(i + 1, sum, path, ans);
            path.add(i);
            f(i + 1, sum + i, path, ans);
            path.remove(path.size() - 1);
        }
    }
}
