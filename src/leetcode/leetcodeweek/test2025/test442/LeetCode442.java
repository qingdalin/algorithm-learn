package leetcode.leetcodeweek.test2025.test442;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/3/23 9:47
 */
public class LeetCode442 {
    public int maxContainers(int n, int w, int maxWeight) {
        int maxN = (int)(Math.floor((double) maxWeight / w));
        return Math.min(n * n, maxN);
    }

//    public static int MAXN = 101;
//    public static int[] father = new int[MAXN];
//    public static int n, m;
//    public static int numberOfComponents(int[][] arr, int k) {
//        n = arr.length;
//        m = arr[0].length;
//        build();
//        for (int i = 0; i < n - 1; i++) {
//            for (int j = i + 1; j < n; j++) {
//                if (check(arr[i], arr[j], k)) {
//                    union(i, j);
//                }
//            }
//        }
//        int ans = 0;
//        for (int i = 0; i < n; i++) {
//            if (father[i] == i) {
//                ans++;
//            }
//        }
//        return ans;
//    }
//
//    private static boolean check(int[] a, int[] b, int k) {
//        HashSet<Integer> seta = new HashSet<>();
//        HashSet<Integer> setb = new HashSet<>();
//        for (int i = 0; i < a.length; i++) {
//            seta.add(a[i]);
//        }
//        for (int i = 0; i < b.length; i++) {
//            setb.add(b[i]);
//        }
//        int cnt = 0;
//        for (Integer i : seta) {
//            if (setb.contains(i)) {
//                cnt++;
//            }
//        }
//        return cnt >= k;
//    }
//
//    private static void build() {
//        for (int i = 0; i <= n; i++) {
//            father[i] = i;
//        }
//    }
//    public static int find(int i) {
//        if (i != father[i]) {
//            father[i] = find(father[i]);
//        }
//        return father[i];
//    }
//
//    public static void union(int x, int y) {
//        int fx = find(x);
//        int fy = find(y);
//        if (fx != fy) {
//            father[fx] = fy;
//        }
//    }
    // skill = [1,5,2,4],
    // mana =  [5,1,4,2]
    // [5, 1, 4, 2]
    // [25,5,20, 10]
    // [10,2, 8, 4]
    // [20,4, 16, 8]
    //
    //public long minTime(int[] skill, int[] mana)

    // 81 / 4 == 20
    // 20 / 4 == 5
    // 5 / 4 == 1
    // 1 / 4 == 0
    public static long minOperations(int[][] queries) {
        int n = queries.length;
        long ans = 0;
        for (int i = 0, l, r; i < n; i++) {
            l = queries[i][0];
            r = queries[i][1];
            long ansr = getAns(r) - getAns(l - 1);
            ans += ansr;
        }
        return ans;
    }

    private static long getAns(int num) {
        if (num == 0) {
            return 0;
        }
        long ans = 0;
        int power = log2(num) / 2;
        for (int j = 1; j <= power; j++) {
            if (j == 1) {
                ans = ans + (long) (Math.pow(4, j) / 2);
            } else {
                ans = ans + (long) ((Math.pow(4, j) - Math.pow(4, j - 1)) * j/ 2 );
            }
        }
        ans = ans + (num - (int) Math.pow(4, power) + 1) / 2;
        return ans;
    }


    public static int log2(int i) {
        int ans = 0;
        while ((1 << ans) <= (i >> 1)) {
            ans++;
        }
        return ans;
    }

    public static void main(String[] args) {
        int[][] arr = new int[1][2];
        arr[0] = new int[] {2, 6};
        System.out.println(minOperations(arr));
        System.out.println(log2(6));
    }
}
