package algorithm.class96game02;

import algorithm.ArrayUtil;

import java.util.Arrays;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2024/7/31 19:21
 *
 * // 尼姆博弈(SG定理简单用法展示)
 * // 一共有 n 堆石头，两人轮流进行游戏
 * // 在每个玩家的回合中，玩家需要 选择任一 非空 石头堆，从中移除任意 非零 数量的石头
 * // 如果不能移除任意的石头，就输掉游戏
 * // 返回先手是否一定获胜
 * // 对数器验证
 */
public class NimGameSG {
    public static String nim1(int[] arr) {
        int eor = 0;
        for (int i : arr) {
            eor ^= i;
        }
        return eor != 0 ? "先手" : "后手";
    }

    public static String nim2(int[] arr) {
        int max = 0;
        for (int i : arr) {
            max = Math.max(max, i);
        }
        int[] sg = new int[max + 1];
        boolean[] appear = new boolean[max + 1];
        for (int i = 1; i <= max; i++) {
            Arrays.fill(appear, false);
            for (int j = 0; j < i; j++) {
                appear[j] = true;
            }
            for (int s = 0; s <= max; s++) {
                if (!appear[s]) {
                    sg[i] = s;
                    break;
                }
            }
        }
        int eor = 0;
        for (int i = 0; i < arr.length; i++) {
            eor ^= arr[i];
        }
        return eor != 0 ? "先手" : "后手";
    }

    public static void main(String[] args) {
        int v = 400;
        int N = 1000;
        System.out.println("测试开始");
        for (int i = 0; i < 10000; i++) {
            int n = (int) (Math.random() * N + 1);
            int[] arr = ArrayUtil.randomPositiveNumArray(n, v);
            String ans1 = nim1(arr);
            String ans2 = nim2(arr);
            if (!ans1.equals(ans2)) {
                System.out.println("出错了");
            }
        }
        System.out.println("测试结束");
    }

}
