package leetcode.leetcodeweek.test2025.test460;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/7/27 9:21
 */
public class Code460_02 {
    public static long numOfSubsequences(String str) {
        char[] s = str.toCharArray();
        int n = s.length;
        int[][] preCnt = new int[n][3];
        int[][] sufCnt = new int[n][3];
        for (int i = 0, j = n - 1; i < n; i++, j--) {
            if (s[i] == 'L') {
                preCnt[i][0]++;
            }
            if (s[i] == 'C') {
                preCnt[i][1]++;
            }
            if (s[i] == 'T') {
                preCnt[i][2]++;
            }
            if (s[j] == 'L') {
                sufCnt[j][0]++;
            }
            if (s[j] == 'C') {
                sufCnt[j][1]++;
            }
            if (s[j] == 'T') {
                sufCnt[j][2]++;
            }
        }
        for (int i = 1, j = n - 2; i < n; i++, j--) {
            preCnt[i][0] += preCnt[i - 1][0];
            preCnt[i][1] += preCnt[i - 1][1];
            preCnt[i][2] += preCnt[i - 1][2];
            sufCnt[j][0] += sufCnt[j + 1][0];
            sufCnt[j][1] += sufCnt[j + 1][1];
            sufCnt[j][2] += sufCnt[j + 1][2];
        }
        // L C T K L C L T
        long ans = Long.MIN_VALUE, cntl, cntc, cntt;
        for (int i = 0; i < n; i++) {
            cntl = preCnt[i][0];
            cntc = getNum(preCnt, i, n, 1);
            cntt = getNum(preCnt, i, n, 2);
            ans = Math.max(ans, (cntl + 1) * cntc * cntt);
            ans = Math.max(ans, cntl * (cntc + 1) * cntt);
            ans = Math.max(ans, cntl * cntc * (cntt + 1));
        }
        return ans;
    }

    public static int getNum(int[][] cnt, int l, int r, int i) {
        if (l == 0) {
            return cnt[r - 1][i];
        }
        return cnt[r - 1][i] - cnt[l - 1][i];
    }

    public static void main(String[] args) {
        // L C T K L C L T
        // 0 1 2 3 4 5 6 7
        //
        String s ="LCTKLCLT";
//        String s ="LT";
        System.out.println(numOfSubsequences(s));
    }
}
