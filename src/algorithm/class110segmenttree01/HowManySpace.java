package algorithm.class110segmenttree01;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2024/8/26 20:47
 */
public class HowManySpace {
    public static int maxi(int l, int r, int i) {
        if (l == r) {
            return i;
        } else {
            int mid = (l + r) >> 1;
            // i * 2 --> i << 1
            // i * 2 + 1 --> i << 1 | 1
            return Math.max(maxi(l, mid, i << 1), maxi(mid + 1, r, i << 1 | 1));
        }
    }

    public static void main(String[] args) {
        int n = 10000;
        int a = 0;
        int b = 0;
        double t = 0;
        for (int i = 1; i <= n; i++) {
            int space = maxi(1, i, 1);
            double times = (double) space / (double) i;
            System.out.println("从[1, " + i + "]范围上，需要的空间是:" + space + ", 倍数是=" + times);
            if (times > t) {
                t = times;
                a = i;
                b = space;
            }
        }
        System.out.println("其中最大的倍数是，从[1, " + a + "]范围上，需要空间是:" + b + ", 倍数是:" + t);
    }
}
