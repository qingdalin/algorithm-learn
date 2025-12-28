package leetcode.study.leetcdoe201_400;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/7/14 19:27
 */
public class Leetcode228SummaryRanges {
    public List<String> summaryRanges(int[] arr) {
        int n = arr.length;
        List<String> ans = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n;) {
            sb.setLength(0);
            sb.append(arr[i]);
            int t = i;
            while (i + 1 < n && arr[i] + 1 == arr[i + 1]) {
                i++;
            }
            if (i > t) {
                sb.append("->").append(arr[i]);
            }
            ans.add(sb.toString());
            i++;
        }
        return ans;
    }
}
