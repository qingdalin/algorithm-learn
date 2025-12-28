package leetcode.study.leetcdoe201_400;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/8/2 17:40
 * https://leetcode.cn/problems/random-pick-index/description/
 */
public class RandomIndex {
    Map<Integer, List<Integer>> map;
    public RandomIndex(int[] nums) {
        map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            int num = nums[i];
//            if (map.containsKey(num)) {
//                map.get(num).add(i);
//            } else {
//                List<Integer> list = new ArrayList<>();
//                list.add(i);
//                map.put(num, list);
//            }
            List<Integer> list = map.computeIfAbsent(num, key -> new ArrayList<>());
            list.add(i);
        }
    }

    public int pick(int target) {
        List<Integer> list = map.get(target);
        int size = list.size();
        int index = (int) (Math.random() * size);
        return list.get(index);
    }
}
