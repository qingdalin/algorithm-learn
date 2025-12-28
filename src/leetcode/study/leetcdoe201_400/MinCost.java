package leetcode.study.leetcdoe201_400;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/8/2 7:41
 * https://leetcode.cn/problems/rearranging-fruits/?envType=daily-question&envId=2025-08-02
 */
public class MinCost {
    public long minCost(int[] arr1, int[] arr2) {
        int n = arr1.length;
        TreeMap<Integer, Integer> map = new TreeMap<>();
        int m = Integer.MAX_VALUE;
        for (int i = 0; i < n; i++) {
            map.put(arr1[i], map.getOrDefault(arr1[i], 0) + 1);
            m = Math.min(arr1[i], m);
        }
        for (int i = 0; i < n; i++) {
            map.put(arr2[i], map.getOrDefault(arr2[i], 0) - 1);
            m = Math.min(arr2[i], m);
        }
        List<Integer> merge = new ArrayList<>();
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            int cnt = entry.getValue();
            if (cnt % 2 != 0) {
                return -1;
            }
            for (int i = 0; i < Math.abs(cnt) / 2; i++) {
                merge.add(entry.getKey());
            }
        }
        merge.sort((a, b) -> a - b);
        // 1 1 2 4
        // 2 2 2 4
        long ans = 0;
        for (int i = 0; i < merge.size() / 2; i++) {
            ans += Math.min(2 * m, merge.get(i));
        }
        return ans;
    }
}
