package algorithm.class59map;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author 汪大鹏
 * @version 1.0
 * @date 2024-03-20 20:02
 * 创建邻接矩阵、邻接表、链式前向星
 */
public class CreateGraph {
    // 点的数量
    public static int MAXN = 11;
    // 链式前向星需要边的数量，一条无向边等于两条有向边
    public static int MAXM = 21;
    // 邻接矩阵
    public static int[][] graph = new int[MAXN][MAXN];
    // 邻接表，有向，不带权重邻接表，无向，不带权重
    // List<List<Integer>> graph1 = new ArrayList<>();
    // 邻接表，有向，带权重和邻接表，无向，带权重
    public static List<List<int[]>> graph1 = new ArrayList<>();
    // 链式前向星建图
    public static int[] head = new int[MAXN];
    public static int[] next = new int[MAXM];
    public static int[] to = new int[MAXM];
    public static int[] weight = new int[MAXM];
    public static int cnt;

    public static void build(int n) {
        // 邻接矩阵清空
        for (int i = 1; i <= n; i++) {
            for (int j = 0; j < n; j++) {
                graph[i][j] = 0;
            }
        }
        // 邻接表清空
        graph1.clear();
        // 下标需要支持1-n，假如1 + n个列表
        for (int i = 0; i <= n; i++) {
            graph1.add(new ArrayList<>());
        }
        // 链式前向星
        cnt = 1;
        Arrays.fill(head, 1, n + 1, 0);
    }
    // 三种方式建立有向，带权重的图
    public static void directGraph(int[][] edges) {
        // 邻接矩阵
        for (int[] edge : edges) {
            graph[edge[0]][edge[1]] = edge[2];
        }
        // 邻接表
        for (int[] edge : edges) {
            // 无权重
            // graph1.get(edge[0]).add(edge[1]);
            graph1.get(edge[0]).add(new int[]{edge[1], edge[2]});
        }
        // 链式前向星
        for (int[] edge : edges) {
            addEdge(edge[0], edge[1], edge[2]);
        }
    }
    // 三种方式建立无向、带权重的图
    public static void unDirectGraph(int[][] edges) {
        // 邻接矩阵
        for (int[] edge : edges) {
            graph[edge[0]][edge[1]] = edge[2];
            graph[edge[1]][edge[0]] = edge[2];
        }
        // 邻接表
        for (int[] edge : edges) {
            // 无权重
            // graph1.get(edge[0]).add(edge[1]);
            graph1.get(edge[0]).add(new int[]{edge[1], edge[2]});
            graph1.get(edge[1]).add(new int[]{edge[0], edge[2]});
        }
        // 链式前向星
        for (int[] edge : edges) {
            addEdge(edge[0], edge[1], edge[2]);
            addEdge(edge[1], edge[0], edge[2]);
        }
    }

    public static void addEdge(int u, int v, int w) {
        // u -> v 权重w
        // 下一条边是老头部边
        next[cnt] = head[u];
        // 去往的点
        to[cnt] = v;
        // 权重
        weight[cnt] = w;
        // 新头部边是cnt
        head[u] = cnt++;
    }

    // 遍历图
    public static void traversal(int n) {
        System.out.println("邻接矩阵遍历 :");
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                System.out.print(graph[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println("邻接表遍历 :");
        for (int i = 1; i <= n; i++) {
            System.out.print(i + "（邻居，边权）:");
            for (int[] edge : graph1.get(i)) {
                System.out.print("（" + edge[0] + "," + edge[1] + "）");
            }
            System.out.println();
        }
        System.out.println("链式前向星遍历 :");
        for (int i = 1; i <= n; i++) {
            System.out.print(i + "（邻居，边权）:");
            for (int ei = head[i]; ei > 0 ; ei = next[ei]) {
                System.out.print("（" + to[ei] + "," + weight[ei] + "）");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        int[][] edges = {
            new int[] {1, 3, 6},
            new int[] {1, 2, 7},
            new int[] {2, 4, 2},
            new int[] {2, 3, 5},
            new int[] {3, 1, 1},
            new int[] {4, 3, 4},
        };
        build(4);
        directGraph(edges);
        traversal(4);
        System.out.println("==========================");
        int[][] edges2 = {
            new int[] {1, 4, 1},
            new int[] {1, 5, 5},
            new int[] {2, 5, 4},
            new int[] {2, 3, 7},
            new int[] {2, 4, 6},
            new int[] {3, 5, 4},
            new int[] {3, 4, 2}
        };
        build(5);
        unDirectGraph(edges2);
        traversal(5);
    }
}
