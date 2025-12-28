package algorithm.class83dp18;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2024/7/10 20:13
 * 电子游戏“辐射4”中，任务 “通向自由” 要求玩家到达名为 “Freedom Trail Ring” 的金属表盘，并使用表盘拼写特定关键词才能开门。
 *
 * 给定一个字符串 ring ，表示刻在外环上的编码；给定另一个字符串 key ，表示需要拼写的关键词。您需要算出能够拼写关键词中所有字符的最少步数。
 *
 * 最初，ring 的第一个字符与 12:00 方向对齐。您需要顺时针或逆时针旋转 ring 以使 key 的一个字符在 12:00 方向对齐，
 * 然后按下中心按钮，以此逐个拼写完 key 中的所有字符。
 *
 * 旋转 ring 拼出 key 字符 key[i] 的阶段中：
 *
 * 您可以将 ring 顺时针或逆时针旋转 一个位置 ，计为1步。旋转的最终目的是将字符串 ring 的一个字符与 12:00 方向对齐，
 * 并且这个字符必须等于字符 key[i] 。
 * 如果字符 key[i] 已经对齐到12:00方向，您需要按下中心按钮进行拼写，这也将算作 1 步。
 * 按完之后，您可以开始拼写 key 的下一个字符（下一阶段）, 直至完成所有拼写。
 * https://leetcode.cn/problems/freedom-trail/description/
 */
public class FindRotateSteps {
    public static int MAXN = 101;
    public static int MAXC = 26;
    public static int[] ring = new int[MAXN];
    public static int[] key = new int[MAXN];
    public static int[] size = new int[MAXN];
    public static int[][] where = new int[MAXC][MAXN];
    public static int[][] dp = new int[MAXN][MAXN];
    public static int n, m;
    public int findRotateSteps(String r, String k) {
        build(r, k);
        return f(0, 0);
    }

    // 转盘指向i位置，单词指向j位置
    private int f(int i, int j) {
        if (j == m) {
            // 当单词已经搞定
            return 0;
        }
        if (dp[i][j] != -1) {
            return dp[i][j];
        }
        int ans = 0;
        if (ring[i] == key[j]) {
            // 当不需要转动单词相等，直接按下
            ans = 1 + f(i, j + 1);
        } else {
            // 顺时针转到最近
            int jump1 = clock(i, key[j]);
            int dance1 = jump1 > i ? (jump1 - i) : (n - (i - jump1));
            // 逆时针最近
            int jump2 = counterClock(i, key[j]);
            int dance2 = i > jump2 ? (i - jump2) : (n - (jump2 - i));
            ans = Math.min(dance1 + f(jump1, j), dance2 + f(jump2, j));
        }
        dp[i][j] = ans;
        return ans;
    }

    private int counterClock(int i, int v) {
        int l = 0;
        int r = size[v] - 1;
        int m = 0;
        int find = -1;
        int[] sorted = where[v];
        while (l <= r) {
            m = (l + r) / 2;
            if (sorted[m] < i) {
                find = m;
                l = m + 1;
            } else {
                r = m - 1;
            }
        }
        return find != -1 ? sorted[find] : sorted[size[v] - 1];
    }

    private int clock(int i, int v) {
        int l = 0;
        int r = size[v] - 1;
        int m = 0;
        int find = -1;
        int[] sorted = where[v];
        while (l <= r) {
            m = (l + r) / 2;
            if (sorted[m] > i) {
                find = m;
                r = m - 1;
            } else {
                l = m + 1;
            }
        }
        return find != -1 ? sorted[find] : sorted[0];
    }

    private void build(String r, String k) {
        n = r.length();
        m = k.length();
        for (int i = 0; i < MAXC; i++) {
            size[i] = 0;
        }
        for (int i = 0, v; i < n; i++) {
            v = r.charAt(i) - 'a';
            where[v][size[v]++] = i;
            ring[i] = v;
        }
        for (int i = 0; i < m; i++) {
            key[i] = k.charAt(i) - 'a';
        }
        for (int i = 0; i < MAXN; i++) {
            for (int j = 0; j < MAXN; j++) {
                dp[i][j] = -1;
            }
        }
    }
}
