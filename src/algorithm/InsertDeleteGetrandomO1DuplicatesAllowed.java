package algorithm;

import java.util.*;

/**
 * @author 汪大鹏
 * @version 1.0
 * @date 2024-01-05 20:38
 */
public class InsertDeleteGetrandomO1DuplicatesAllowed {
    class RandomizedCollection {
        Map<Integer, Set<Integer>> map;
        ArrayList<Integer> arr;

        public RandomizedCollection() {
            map = new HashMap<>();
            arr = new ArrayList<>();
        }

        public boolean insert(int val) {
            arr.add(val);
            Set<Integer> set = map.getOrDefault(val, new HashSet<>());
            set.add(arr.size() - 1);
            map.put(val, set);
            return set.size() == 1;
//            if (map.containsKey(val)) {
//                Set<Integer> set = map.get(val);
//                set.add(arr.size());
//                map.put(val, set);
//                arr.add(val);
//                return false;
//            } else {
//                Set<Integer> set = new HashSet<>();
//                set.add(arr.size());
//                map.put(val, set);
//                arr.add(val);
//                return true;
//            }
        }

        public boolean remove(int val) {
            if (map.containsKey(val)) {
                Set<Integer> set = map.get(val);
                int valIndex = set.iterator().next();
                int endVal = arr.get(arr.size() - 1);
                if (val == endVal) {
                    set.remove(arr.size() - 1);
                } else {
                    Set<Integer> endSet = map.getOrDefault(endVal, new HashSet<>());
                    endSet.add(valIndex);
                    arr.set(valIndex, endVal);
                    endSet.remove(arr.size() - 1);
                    set.remove(valIndex);
                }

                arr.remove(arr.size() - 1);
                if (set.isEmpty()) {
                    map.remove(val);
                }
                return true;
            }
            return false;
        }

        public int getRandom() {
            return arr.get((int) (Math.random() * arr.size()));
        }
    }
}
