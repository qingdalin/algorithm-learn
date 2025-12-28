package leetcode.study.leetcode401_600;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/12/28 14:04
 * https://leetcode.cn/problems/student-attendance-record-i/
 */
public class Leetcode551CheckRecord {
    public boolean checkRecord(String str) {
        char[] s = str.toCharArray();
        int n = s.length;
        int absentCnt = 0, lateCnt = 0;
        for (int i = 0; i < n; i++) {
            if (s[i] == 'A') {
                absentCnt++;
                if (absentCnt > 1) {
                    return false;
                }
                lateCnt = 0;
            } else if (s[i] == 'L') {
                lateCnt++;
                if (lateCnt == 3) {
                    return false;
                }
            } else {
                lateCnt = 0;
            }

        }
        return true;
    }
}
