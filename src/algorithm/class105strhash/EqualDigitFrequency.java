package algorithm.class105strhash;

import java.util.Arrays;
import java.util.HashSet;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2024/8/17 15:53
 */
public class EqualDigitFrequency {
    public static int equalDigitFrequency(String str) {
        long base = 499;
        char[] s = str.toCharArray();
        HashSet<Long> set = new HashSet<>();
        int[] cnt = new int[10];
        for (int i = 0; i < s.length; i++) {
            long hashCode = 0;
            Arrays.fill(cnt, 0);
            // 当前字符      最大词频计数   最大词频种类        所有词频种类
            int curVal = 0, maxCnt = 0, maxCntKinds = 0, allCntKinds = 0;
            for (int j = i; j < s.length; j++) {
                curVal = s[j] - '0';
                hashCode = hashCode * base + curVal + 1;
                cnt[curVal]++;
                if (cnt[curVal] == 1) {
                    // 当前字符的数量是1，那么所有种类加1
                    allCntKinds++;
                }
                if (cnt[curVal] > maxCnt) {
                    // 当前字符的最大词频大于之前最大词频，
                    // 那么最大词频种类变为1，maxCnt 更新为当前的
                    maxCnt = cnt[curVal];
                    maxCntKinds = 1;
                } else if (cnt[curVal] == maxCnt) {
                    // 如果当前词频等于最大词频，最大词频种类加1
                    maxCntKinds++;
                }
                if (allCntKinds == maxCntKinds) {
                    set.add(hashCode);
                }
            }
        }return set.size();
    }

    public static int equalDigitFrequency1(String str) {

        char[] s = str.toCharArray();
        HashSet<String> set = new HashSet<>();
        for (int i = 0; i < s.length; i++) {
            String subStr;
            for (int j = i + 1; j <= s.length; j++) {
                subStr = str.substring(i, j);
                if (isEqual(subStr)) {
                    set.add(subStr);
                }
            }
        }
        return set.size();
    }

    private static boolean isEqual(String str) {
        char[] s = str.toCharArray();
        int[] cnt = new int[10];
        // 当前字符      最大词频计数   最大词频种类        所有词频种类
        int curVal = 0, maxCnt = 0, maxCntKinds = 0, allCntKinds = 0;
        for (int i = 0; i < s.length; i++) {
            curVal = s[i] - '0';
            cnt[curVal]++;
            if (cnt[curVal] == 1) {
                // 当前字符的数量是1，那么所有种类加1
                allCntKinds++;
            }
            if (cnt[curVal] > maxCnt) {
                // 当前字符的最大词频大于之前最大词频，
                // 那么最大词频种类变为1，maxCnt 更新为当前的
                maxCnt = cnt[curVal];
                maxCntKinds = 1;
            } else if (cnt[curVal] == maxCnt) {
                // 如果当前词频等于最大词频，最大词频种类加1
                maxCntKinds++;
            }
        }
        return maxCntKinds == allCntKinds;
    }

    public static void main(String[] args) {
        int maxn = 1000;
        System.out.println("测试开始");
        for (int i = 0; i < 10000; i++) {
            int n = (int) (Math.random() * maxn + 1);
            String str = randomStr(n);
            long start = System.currentTimeMillis();
            int ans1 = equalDigitFrequency(str);
            long end = System.currentTimeMillis();
            System.out.println("字符串hash" + (end - start));
            start = System.currentTimeMillis();
            int ans2 = equalDigitFrequency1(str);
            end = System.currentTimeMillis();
            System.out.println("普通的" + (end - start));
            if (ans1 != ans2) {
                System.out.println("错了");
            }
        }
        System.out.println("测试结束");
        String str = "1563213";
        long start = System.currentTimeMillis();
        int ans1 = equalDigitFrequency(str);
        long end = System.currentTimeMillis();
        System.out.println("字符串hash" + (end - start));
        start = System.currentTimeMillis();
        int ans2 = equalDigitFrequency1(str);
        end = System.currentTimeMillis();
        System.out.println("普通的" + (end - start));
        if (ans1 != ans2) {
            System.out.println("错了");
        }
    }

    private static String randomStr(int n) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            sb.append((int)(Math.random() * 10));
        }
        return sb.toString();
    }
}
