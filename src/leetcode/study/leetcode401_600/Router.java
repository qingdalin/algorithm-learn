package leetcode.study.leetcode401_600;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/9/20 10:34
 * https://leetcode.cn/problems/implement-router/?envType=daily-question&envId=2025-09-20
 */
public class Router {
    public int[] tree;
    public int limit;
    public Deque<int[]> deque = new ArrayDeque<>();
    public Set<Long> set = new HashSet<>();
    public int hash = 499;
    public int hash2 = hash * hash;
    public Router(int memoryLimit) {
        limit = memoryLimit;
        tree = new int[memoryLimit + 1];
        deque.clear();
        set.clear();
    }

    public void add(int i, int v) {
        while (i <= limit) {
            tree[i] += v;
            i += lowbit(i);
        }
    }

    public int sum(int i) {
        int ret = 0;
        while (i > 0) {
            i += tree[i];
            i -= lowbit(i);
        }
        return ret;
    }

    private int lowbit(int i) {
        return i & -i;
    }

    public boolean addPacket(int source, int destination, int timestamp) {
        long hashVal = getHash(source, destination, timestamp);
        if (set.contains(hashVal)) {
            return false;
        }
        if (deque.size() >= limit) {
            deque.pollLast();
        }
        set.add(hashVal);
        deque.addFirst(new int[] {source, destination, timestamp});
        add(timestamp, 1);
        return true;
    }

    public long getHash(long source, long destination, long timestamp) {
        return source * hash2 + destination * hash + timestamp;
    }

    public int[] forwardPacket() {
        if (deque.isEmpty()) {
            return new int[] {};
        }
        int[] cur = deque.pollLast();
        long hashVal = getHash(cur[0], cur[1], cur[2]);
        set.remove(hashVal);
        return cur;
    }

    public int getCount(int destination, int startTime, int endTime) {
        int cnt = 0;
        int size = deque.size();
        List<int[]> list = new ArrayList<>();
        for (int[] cur : deque) {
            list.add(cur);
        }
        int l = 0, r = list.size() - 1, mid;
        while (l <= r) {
            mid = (l + r) / 2;

        }
        for (int[] cur : deque) {
            if (cur[1] != destination) {
                continue;
            }
            if (cur[2] >= startTime && cur[2] <= endTime) {
                cnt++;
            }
        }
        return cnt;
    }
}
