package leetcode.leetcodeweek.test2025.test464;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/8/24 8:52
 * https://leetcode.cn/contest/weekly-contest-464/problems/partition-array-into-k-distinct-groups/
 */
public class Code464_02 {
    public static int MAXN = 100001;
//    public static int[] cnts = new int[MAXN];
    public static boolean partitionArray(int[] nums, int k) {
        // Set<Integer> set = new HashSet<>();
        // 3,5,2,2,2,4, k = 3
        // 2 3 4/ 2 2 5,
        int n = nums.length;
        if (n % k != 0) {
            return false;
        }
        int[] cnts = new int[MAXN];
//        Arrays.fill(cnts, 0);
        // 一共分几组，最大次数不能超过m
        int m = n / k;
        int maxCnt = 0;
        for (int num : nums) {
            maxCnt = Math.max(maxCnt, ++cnts[num]);
        }
        return maxCnt <= m;
    }

    public static void main(String[] args) {
//        int[] arr = {1,5,2,3};
        int[] arr = {3,5,2,2};
//        int k = 3;
        int k = 2;
        System.out.println(partitionArray(arr, k));
    }
}
