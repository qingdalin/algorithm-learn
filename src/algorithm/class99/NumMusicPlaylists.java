package algorithm.class99;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2024/8/4 17:41
 * 你的音乐播放器里有 n 首不同的歌，在旅途中，你计划听 goal 首歌（不一定不同，即，允许歌曲重复）。你将会按如下规则创建播放列表：
 *
 * 每首歌 至少播放一次 。
 * 一首歌只有在其他 k 首歌播放完之后才能再次播放。
 * 给你 n、l 和 k ，返回可以满足要求的播放列表的数量。由于答案可能非常大，请返回对 109 + 7 取余 的结果。
 * https://leetcode.cn/problems/number-of-music-playlists/description/
 */
public class NumMusicPlaylists {
    public static int limit = 100;
    public static int mod = 1000000007;
    public static long[] fac = new long[limit + 1];
    public static long[] inv = new long[limit + 1];
    static {
        fac[0] = 1;
        for (int i = 1; i <= limit; i++) {
            fac[i] = ((long) i * fac[i - 1]) % mod;
        }
        inv[limit] = power(fac[limit], mod - 2);
        for (int i = limit - 1; i >= 0; i--) {
            inv[i] = ((long) (i + 1) * inv[i + 1]) % mod;
        }
    }
    private static long power(long x, long n) {
        long ans = 1;
        while (n > 0) {
            if ((n & 1) == 1) {
                ans = (ans * x) % mod;
            }
            x = (x * x) % mod;
            n >>= 1;
        }
        return ans;
    }
    public int numMusicPlaylists(int n, int l, int k) {
        long ans = 0;
        int sigh = 1;
        for (int i = 0; i < n - k; i++, sigh = sigh == 1 ? (mod - 1) : 1) {
            long cur = fac[n] * sigh % mod;
            cur = (cur  * power(n - k - i, l - k)) % mod;
            cur = (cur * inv[i]) % mod;
            cur = (cur * inv[n - k - i]) % mod;
            ans = (cur + ans) % mod;
        }
        return (int) ans;
    }
}
