package algorithm.class79dp14;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;
import java.util.Arrays;

/**
 * @author 汪大鹏
 * @version 1.0
 * @date 2024-06-29 16:01
 * https://www.luogu.com.cn/problem/P2014
 * // 最优解，链式前向星建图 + dfn序的利用 + 巧妙定义下的尝试
 * // 时间复杂度O(n*m)，觉得难可以跳过，这个最优解是非常巧妙和精彩的！
 */
public class CourseSelect2 {
    public static int n, m;
    public static int MAXN = 301;
    public static int[][] dp = new int[MAXN + 2][MAXN];
    public static int[] nums = new int[MAXN];

    public static int[] head = new int[MAXN];
    public static int[] next = new int[MAXN];
    public static int[] to = new int[MAXN];

    public static int[] val = new int[MAXN + 1];
    public static int[] size = new int[MAXN + 1];
    public static int edgeCnt, dfnCnt;

    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StreamTokenizer st = new StreamTokenizer(bf);
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        while (st.nextToken() != StreamTokenizer.TT_EOF) {
            n = (int) st.nval;
            st.nextToken();
            m = (int) st.nval;
            build(n, m);
            for (int i = 1, pre; i <= n; i++) {
                st.nextToken();
                pre = (int) st.nval;
                st.nextToken();
                addEdge(pre, i);
                nums[i] = (int) st.nval;
            }
            out.println(compute());
        }
        out.flush();
        out.close();
        bf.close();
    }

    public static void addEdge(int u, int v) {
        next[edgeCnt] = head[u];
        to[edgeCnt] = v;
        head[u] = edgeCnt++;
    }

    private static int compute() {
        f(0);
        // 节点编号0 ~ n，dfn序号范围1 ~ n+1
        // 接下来的逻辑其实就是01背包！不过经历了很多转化
        // 整体的顺序是根据dfn序来进行的，从大的dfn序，遍历到小的dfn序
        // dp[i][j] : i ~ n+1 范围的节点，选择j个节点一定要形成有效结构的情况下，最大的累加和
        // 怎么定义有效结构？重点！重点！重点！
        // 假设i ~ n+1范围上，目前所有头节点的上方，有一个总的头节点
        // i ~ n+1范围所有节点，选出来j个节点的结构，
        // 挂在这个假想的总头节点之下，是一个连续的结构，没有断开的情况
        // 那么就说，i ~ n+1范围所有节点，选出来j个节点的结构是一个有效结构
        for (int i = n + 1; i >= 2; i--) {
            for (int j = 1; j <= m; j++) {
                dp[i][j] = Math.max(dp[i + size[i]][j], dp[i + 1][j - 1] + val[i]);
            }
        }
        // dp[2][m] : 2 ~ n范围上，选择m个节点一定要形成有效结构的情况下，最大的累加和
        // 最后来到dfn序为1的节点，一定是原始的0号节点
        // 原始0号节点下方一定挂着有效结构
        // 并且和补充的0号节点一定能整体连在一起，没有任何跳跃连接
        // 于是整个问题解决
        return nums[0] + dp[2][m];
    }


    private static int f(int u) {
        int i = ++dfnCnt;
        val[i] = nums[u];
        size[i] = 1;
        for (int ei = head[u], v; ei > 0; ei = next[ei]) {
            v = to[ei];
            size[i] += f(v);
        }
        return size[i];
    }

    private static void build(int n, int m) {
        dfnCnt = 0;
        edgeCnt = 1;
        Arrays.fill(head, 0, n + 1, 0);
        Arrays.fill(dp[n + 2], 0, m + 1, 0);
    }
}
