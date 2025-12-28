package algorithm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 汪大鹏
 * @version 1.0
 * @date 2024-01-06 14:13
 */
public class MaximumFrequencyStack {
    class FreqStack {
        // 每层节点数量
        Map<Integer, ArrayList<Integer>> cntTimes;
        Map<Integer, Integer> valTimes;
        int maxTime = 0;
        public FreqStack() {
            cntTimes = new HashMap<>();
            valTimes = new HashMap<>();
        }

        public void push(int val) {
            int curTimes = valTimes.getOrDefault(val, 0) + 1;
            valTimes.put(val, curTimes);
            if (!cntTimes.containsKey(curTimes)) {
                cntTimes.put(curTimes, new ArrayList<>());
            }
            ArrayList<Integer> valArr = cntTimes.get(curTimes);
            valArr.add(val);
            maxTime = Math.max(curTimes, maxTime);
        }

        public int pop() {
            ArrayList<Integer> valArr = cntTimes.get(maxTime);
            int ans = valArr.remove(valArr.size() - 1);
            if (valArr.size() == 0) {
                cntTimes.remove(maxTime--);
            }
            int time = valTimes.get(ans);
            if (time == 1) {
                valTimes.remove(ans);
            } else {
                valTimes.put(ans, time - 1);
            }
            return ans;
        }
    }
}
