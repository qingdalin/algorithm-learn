package leetcode.study.leetcode401_600;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/12/28 15:38
 * https://leetcode.cn/problems/brick-wall/description/
 */
public class Leetcode554LeastBricks {
    public int leastBricks(List<List<Integer>> wall) {
        Map<Integer, Integer> map = new HashMap<>();
        for (List<Integer> list : wall) {
            int n = list.size();
            int sum = 0;
            for (int i = 0; i < n - 1; i++) {
                sum += list.get(i);
                map.put(sum, map.getOrDefault(sum, 0) + 1);
            }
        }
        int ans = 0;
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            ans = Math.max(ans, entry.getValue());
        }
        return wall.size() - ans;
    }
}
