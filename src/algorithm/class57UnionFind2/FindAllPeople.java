package algorithm.class57UnionFind2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author 汪大鹏
 * @version 1.0
 * @date 2024-03-17 12:46
 * https://leetcode.cn/problems/find-all-people-with-secret/
 * 并查集,打标签的技巧
 */
public class FindAllPeople {
    public static int MAXN = 100001;
    public static int[] father = new int[MAXN];
    public static boolean[] secret = new boolean[MAXN];
    public List<Integer> findAllPeople(int n, int[][] meetings, int firstPerson) {
        build(n, firstPerson);
        Arrays.sort(meetings, (a, b) -> a[2] - b[2]);
        int m = meetings.length;
        for (int l = 0, r; l < m; ) {
            r = l;
            while (r + 1 < m && meetings[l][2] == meetings[r + 1][2]) {
                // 将开会时间在在一起的专家分为一组
                r++;
            }
            for (int i = l; i <= r; i++) {
                union(meetings[i][0], meetings[i][1]);
            }
            // 将不知道秘密的专家解散
            for (int i = l, a, b; i <= r; i++) {
                a = meetings[i][0];
                b = meetings[i][1];
                if (!secret[find(a)]) {
                    father[a] = a;
                }
                if (!secret[find(b)]) {
                    father[b] = b;
                }
            }
            l = r + 1;
        }
        List<Integer> ans = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            if (secret[find(i)]) {
                ans.add(i);
            }
        }
        return ans;
    }

    public int find(int i) {
        if (i != father[i]) {
            father[i] = find(father[i]);
        }
        return father[i];
    }

    public void union(int x, int y) {
        int fx = find(x);
        int fy = find(y);
        if (fx != fy) {
            father[fx] = fy;
            secret[fy] |= secret[fx];
        }
    }

    private void build(int n, int firstPerson) {
        for (int i = 0; i < n; i++) {
            father[i] = i;
            secret[i] = false;
        }
        father[firstPerson] = 0;
        secret[0] = true;
    }
}
