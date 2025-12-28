package algorithm.class60map2;

/**
 * @author 汪大鹏
 * @version 1.0
 * @date 2024-03-24 11:46
 * https://leetcode.cn/problems/maximum-employees-to-be-invited-to-a-meeting/
 */
public class MaximumInvitations {
    public int maximumInvitations(int[] favorite) {
        int n = favorite.length;
        int[] indegree = new int[n];
        for (int i = 0; i < n; i++) {
            indegree[favorite[i]]++;
        }
        int[] queue = new int[n];
        int l = 0, r = 0;
        for (int i = 0; i < n; i++) {
            if (indegree[i] == 0) {
                queue[r++] = i;
            }
        }
        // 有中心环的深度是多少
        int[] deep = new int[n];
        while (l < r) {
            int cur = queue[l++];
            int next = favorite[cur];
            deep[next] = Math.max(deep[next], deep[cur] + 1);
            if (--indegree[next] == 0) {
                queue[r++] = next;
            }
        }
        // 小环：中心点 == 2，加上两端连接的长度 + 2
        // 大环：中心点 > 2，中心点数量
        int sumOfSmallRing = 0;
        int maxRing = 0;
        for (int i = 0; i < n; i++) {
            if (indegree[i] > 0) {
                int ringSize = 1;
                indegree[i] = 0;
                for (int j = favorite[i]; i != j; j = favorite[j]) {
                    ringSize++;
                    indegree[j] = 0;
                }
                if (ringSize == 2) {
                    sumOfSmallRing += 2 + deep[i] + deep[favorite[i]];
                } else {
                    maxRing = Math.max(maxRing, ringSize);
                }
            }
        }
        return Math.max(sumOfSmallRing, maxRing);
    }
}
