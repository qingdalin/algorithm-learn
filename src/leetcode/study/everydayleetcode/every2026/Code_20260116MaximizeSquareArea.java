package leetcode.study.everydayleetcode.every2026;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2026/1/16 19:08
 * https://leetcode.cn/problems/maximum-square-area-by-removing-fences-from-a-field/?envType=daily-question&envId=2026-01-16
 */
public class Code_20260116MaximizeSquareArea {
    public static int MOD = 1000000007;
    public static int maximizeSquareArea(int m, int n, int[] hFences, int[] vFences) {
        long maxEdge = Long.MIN_VALUE;
        List<Integer> shuiPingList = build(m, hFences);
        List<Integer> shuZhiList = build(n, vFences);
        int size1 = shuiPingList.size();
        int size2 = shuZhiList.size();
        Set<Integer> set = new HashSet<>();
        for (int k = 0; k < size2; k++) {
            for (int l = k + 1; l < size2; l++) {
                int edge2 = shuZhiList.get(l) - shuZhiList.get(k);
                set.add(edge2);
            }
        }
        for (int i = 0; i < size1; i++) {
            for (int j = i + 1; j < size1; j++) {
                int edge1 = shuiPingList.get(j) - shuiPingList.get(i);
                if (set.contains(edge1)) {
                    maxEdge = Math.max(maxEdge, edge1);
                }
            }
        }
        if (maxEdge == Long.MIN_VALUE) {
            return  -1;
        }
        return (int) ((maxEdge * maxEdge) % MOD);
    }

    private static List<Integer> build(int len, int[] arr) {
        Arrays.sort(arr);
        List<Integer> res = new ArrayList<>(arr.length + 2);
        res.add(1);
        for (int i : arr) {
            res.add(i);
        }
        res.add(len);
        return res;
    }
}
