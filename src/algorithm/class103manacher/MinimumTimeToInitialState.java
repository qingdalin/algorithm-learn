package algorithm.class103manacher;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2024/8/14 19:45
 * 给你一个下标从 0 开始的字符串 word 和一个整数 k 。
 *
 * 在每一秒，你必须执行以下操作：
 *
 * 移除 word 的前 k 个字符。
 * 在 word 的末尾添加 k 个任意字符。
 * 注意 添加的字符不必和移除的字符相同。但是，必须在每一秒钟都执行 两种 操作。
 *
 * 返回将 word 恢复到其 初始 状态所需的 最短 时间（该时间必须大于零）。
 * https://leetcode.cn/problems/minimum-time-to-revert-word-to-initial-state-ii/description/
 */
public class MinimumTimeToInitialState {
    public static int MAXN = 1000001;
    public static int[] z = new int[MAXN];
    public static char[] s;
    public static int n;
    public int minimumTimeToInitialState(String word, int k) {
        s = word.toCharArray();
        n = s.length;
        zArray();
        for (int i = k; i < n; i += k) {
            if (z[i] + i == n) {
                return i / k;
            }
        }
        return (n + k - 1) / k;
    }

    private void zArray() {
        z[0] = n;
        for (int i = 1, r = 1, c = 1, len; i < n; i++) {
            len = r > i ? Math.min(r - i, z[i - c]) : 0;
            while (i + len < n && s[i + len] == s[len]) {
                len++;
            }
            if (i + len > r) {
                r = i + len;
                c = i;
            }
            z[i] = len;
        }
    }
}
