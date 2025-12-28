package algorithm;

/**
 * @author 汪大鹏
 * @version 1.0
 * @date 2024-01-14 15:21
 */
public class HanNuoTa {
    public static void main(String[] args) {
        System.out.println("大");
        hanoi(3);
    }
    public static void hanoi(int n) {
        if (n > 0) {
            f(n, "左", "右", "中");
        }
    }

    private static void f(int i, String from, String to, String other) {
        if (i == 1) {
            System.out.println("移动圆盘 1 从" + from + "到" + to);
        } else {
            f(i - 1, from, other, to);
            System.out.println("移动圆盘 "+ i +" 从" + from + "到" + to);
            f(i - 1, other, to, from);
        }
        StringBuilder sb = new StringBuilder();
    }
}
