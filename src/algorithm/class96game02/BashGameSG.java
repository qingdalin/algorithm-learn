package algorithm.class96game02;

import java.util.Arrays;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2024/7/29 20:13
 */
public class BashGameSG {
    public static String bash1(int n, int m) {
        return n % (m + 1) == 0 ? "后手" : "先手";
    }

    public static String bash2(int n, int m) {
        int[] sg = new int[n + 1];
        boolean[] appear = new boolean[m + 1];
        for (int i = 1; i <= n; i++) {
            Arrays.fill(appear, false);
            for (int j = 1; j <= m && i - j >= 0; j++) {
                appear[sg[i - j]] = true;
            }
            for (int s = 1; s <= m; s++) {
                if (!appear[s]) {
                    sg[i] = s;
                    break;
                }
            }
        }
        System.out.println("n = " + n + ", m = " + m);
        for (int i = 1; i <= n; i++) {
            System.out.println("sg("+i+") == "+sg[i]);
        }
        return sg[n] == 0 ? "后手" : "先手";
    }
    public static void main(String[] args) {
//        int v = 1000;
//        System.out.println("测试开始");
//        for (int i = 0; i < 2000; i++) {
//            int n = (int) (Math.random() * v + 1);
//            int m = (int) (Math.random() * v + 1);
//            String ans1 = bash1(n, m);
//            String ans2 = bash2(n, m);
//            if (!ans1.equals(ans2)) {
//                System.out.println("出错了");
//            }
//        }
//        System.out.println("测试结束");
        int n = 100;
        int m = 6;
        bash2(n, m);
    }
}
