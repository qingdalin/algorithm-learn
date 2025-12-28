package algorithm.duishuqi42;

/**
 * 吃草问题，一份草，A和B轮流吃，，一次只能吃4的次方份，谁最先没草吃谁输
 * @author 汪大鹏
 * @version 1.0
 * @date 2024-01-27 13:02
 */
public class EatGrass {
    public static void main(String[] args) {
        for (int i = 0; i < 50; i++) {
            // System.out.println(i + "份草，赢家：" + win2(i, "A"));
            if (!win1(i, "A").equals(win2(i, "A"))) {
                System.out.println("出错了，草的份数：" + i);
            }
        }
    }
    public static String win2(int n, String cur) {
        return (n % 5 == 0 || n % 5 == 2) ? "B" : "A";
    }


    public static String win1(int n, String cur) {
        String enemy = cur.equals("A") ? "B" : "A";
        /*     1   2   3   4   5
            A  w       w   w
            B      w           w
         */
        if (n < 5) {
            return (n == 0 || n == 2) ? enemy : cur;
         }
        int pick = 1;
        while (pick <= n) {
            if (win1(n - pick, enemy).equals(cur)) {
                return cur;
            }
            pick *= 4;
        }
        return enemy;
    }
}
