package algorithm.duishuqi42;

/**
 * 一个数是否能被连续自然数相加得到
 * @author 汪大鹏
 * @version 1.0
 * @date 2024-01-27 13:18
 */
public class ContinueNumberAdd {
    public static void main(String[] args) {
        for (int i = 0; i < 200; i++) {
            System.out.println(i + "是否是神奇数字：" + f(i));
            if (is2(i) != f(i)) {
                System.out.println("出错了");
            }
        }
    }
    public static boolean is2(int num) {
        return (num & (num - 1)) != 0;
    }
    public static boolean f(int num) {
        for (int start = 1, sum = 0; start <= num ; start++) {
            sum = start;
            for (int i = start + 1; i <= num; i++) {
                if (sum + i > num) {
                    break;
                }
                if (sum + i == num) {
                    return true;
                }
                sum += i;
            }
        }
        return false;
    }
}
