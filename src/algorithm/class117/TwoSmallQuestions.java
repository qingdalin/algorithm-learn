package algorithm.class117;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2024/9/18 19:50
 */
public class TwoSmallQuestions {
    // 给定一个正数x
    //	// 已知用m个二进制位一定能表示x
    //	// 从高位到低位打印x每一位的状态
    public static void show1(int x, int m) {
        for (int p = m - 1, t = x; p >= 0; p--) {
            if ((1 << p) <= t) {
                t -= 1 << p;
                System.out.println(x + "的第" + p + "位是1");
            } else {
                System.out.println(x + "的第" + p + "位是0");
            }
        }
    }

    // 给定一个正数x
    // 打印<=x最大的2的幂
    // 到底是2的几次方
    public static void show2(int x) {
        // 错误写法，会溢出
//        int power = 0;
//        while ((1 << power) <= x) {
//            power++;
//        }
//        power--;
        // 正确写法，防溢出
        int power = 0;
        while ((1 << power) <= (x >> 1)) {
            power++;
        }
        System.out.println("小于等于" + x + "最大的2次方是" + power);
    }

    public static void main(String[] args) {
        int x = 101;
        int m = 10;
        show1(x, m);
        int n = 13;
        show2(n);
        n  = 16;
        show2(n);
        n = 2000000000;
        show2(n);
    }
}
