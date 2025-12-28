package algorithm.class64Dijkstra;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

/**
 * @author 汪大鹏
 * @version 1.0
 * @date 2024-04-17 19:29
 * 有 n 个网络节点，标记为 1 到 n。
 *
 * 给你一个列表 times，表示信号经过 有向 边的传递时间。 times[i] = (ui, vi, wi)，
 * 其中 ui 是源节点，vi 是目标节点， wi 是一个信号从源节点传递到目标节点的时间。
 *
 * 现在，从某个节点 K 发出一个信号。需要多久才能使所有节点都收到信号？如果不能使所有节点收到信号，返回 -1 。
 * https://leetcode.cn/problems/network-delay-time/description/
 */
public class NetworkDelayTime {
    public int networkDelayTime(int[][] times, int n, int s) {
        List<List<int[]>> graph = new ArrayList<>();
        for (int i = 0; i <= n; i++) {
            graph.add(new ArrayList<>());
        }
        for (int[] time : times) {
            graph.get(time[0]).add(new int[] {time[1], time[2]});
        }
        int[] distance = new int[n + 1];
        boolean[] visited = new boolean[n + 1];
        for (int i = 0; i <= n; i++) {
            distance[i] = Integer.MAX_VALUE;
        }
        distance[s] = 0;
        PriorityQueue<int[]> heap = new PriorityQueue<>((a, b) -> a[1] - b[1]);
        heap.add(new int[] {s, 0});
        while (!heap.isEmpty()) {
            int u = heap.poll()[0];
            if (visited[u]) {
                continue;
            }
            visited[u] = true;
            for (int[] edge : graph.get(u)) {
                int v = edge[0];
                int w = edge[1];
                if (!visited[v] && distance[v] > distance[u] + w) {
                    distance[v] = distance[u] + w;
                    heap.add(new int[] {v, distance[u] + w });
                }
            }
        }
        int ans = Integer.MIN_VALUE;
        for (int i = 1; i <= n; i++) {
            if (Integer.MAX_VALUE == distance[i]) {
                return -1;
            }
            ans = Math.max(ans, distance[i]);
        }
        return ans;
    }

    public static int MAXN = 101;
    public static int MAXM = 6001;
    public static int[] head = new int[MAXN];
    public static int[] next = new int[MAXM];
    public static int[] to = new int[MAXM];
    public static int[] wight = new int[MAXM];
    public static int cnt;

    public static int[] heap = new int[MAXN];
    public static int size;
    public static int[] where = new int[MAXN];

    public static int[] distance = new int[MAXN];


    public int networkDelayTime1(int[][] times, int n, int s) {
        build(n);
        for (int[] edge : times) {
            addEdge(edge[0], edge[1], edge[2]);
        }
        addOrUpdateOrIgnore(s, 0);
        while (!isEmpty()) {
            int u = pop();
            for (int ei = head[u]; ei > 0; ei = next[ei]) {
                addOrUpdateOrIgnore(to[ei], distance[u] + wight[ei]);
            }
        }
        int ans = Integer.MIN_VALUE;
        for (int i = 1; i <= n; i++) {
            if (Integer.MAX_VALUE == distance[i]) {
                return -1;
            }
            ans = Math.max(ans, distance[i]);
        }
        return ans;
    }

    private int pop() {
        int ans = heap[0];
        swap(0, --size);
        heapify(0);
        where[ans] = -2;
        return ans;
    }

    private void heapify(int i) {
        int l = 1;
        while (l < size) {
            int best = l + 1 < size && distance[heap[l + 1]] < distance[heap[l]] ? l + 1 : l;
            best = distance[heap[best]] < distance[heap[i]] ? best : i;
            if (best == i) {
                break;
            }
            swap(i, best);
            i = best;
            l = 2 * i + 1;
        }
    }

    private void addOrUpdateOrIgnore(int v, int c) {
        if (where[v] == -1) {
            heap[size] = v;
            where[v] = size++;
            distance[v] = c;
            heapInsert(where[v]);
        } else if (where[v] >= 0) {
            distance[v] = Math.min(distance[v], c);
            heapInsert(where[v]);
        }

    }

    private void heapInsert(int i) {
        while (distance[heap[i]] < distance[heap[(i - 1) / 2]]) {
            swap(i, (i - 1) / 2);
            i = (i - 1) / 2;
        }
    }

    private void swap(int i, int j) {
        int tmp = heap[i];
        heap[i] = heap[j];
        heap[j] = tmp;
        where[heap[i]] = i;
        where[heap[j]] = j;
    }

    private boolean isEmpty() {
        return size == 0;
    }

    private void addEdge(int u, int v, int w) {
        next[cnt] = head[u];
        to[cnt] = v;
        wight[cnt] = w;
        head[u] = cnt++;
    }

    public static void build(int n) {
        cnt = 1;
        size = 0;
        Arrays.fill(head, 0, n + 1, 0);
        Arrays.fill(where, 0, n + 1, -1);
        Arrays.fill(distance, 0, n + 1, Integer.MAX_VALUE);
    }
}
