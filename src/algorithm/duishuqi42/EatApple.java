package algorithm.duishuqi42;

/**
 * 装苹果，只能用装6或8个苹果的袋子，如果能装下返回最少的袋子个数，否则返回-1
 * @author 汪大鹏
 * @version 1.0
 * @date 2024-01-27 11:17
 */
public class EatApple {
    public static void main(String[] args) {
        for (int i = 1; i < 100; i++) {
            //System.out.println(i + "个苹果，需要" + bags(i) + "个袋子：");
            if (bags(i) != bags2(i)) {
                System.out.println("出错了，苹果个数：" + i);
            }
        }
    }

    public static int bags2(int apple) {
        if ((apple & 1) != 0) {
            return -1;
        }
        if (apple < 18) {
            if (apple < 6) {
                return -1;
            }
            if (apple == 6 || apple == 8) {
                return 1;
            }
            if (apple == 14 || apple == 12 || apple == 16) {
                return 2;
            }
            return -1;
        }
        return (apple - 18) / 8 + 3;

    }

    public static int bags(int apple) {
        int ans = f(apple);
        return ans == Integer.MAX_VALUE ? -1 : ans;
    }

    private static int f(int apple) {
        if (apple < 0) {
            return Integer.MAX_VALUE;
        }
        if (apple == 0) {
            return 0;
        }
        int p1 = f(apple - 8);
        int p2 = f(apple - 6);
        p1 += p1 != Integer.MAX_VALUE ? 1 : 0;
        p2 += p2 != Integer.MAX_VALUE ? 1 : 0;
        return Math.min(p1, p2);
    }
}
