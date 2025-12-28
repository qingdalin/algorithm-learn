package algorithm;

import java.util.Arrays;

public class SelectSort {
    public static void main(String[] args) {
        int[] a ={5,4,8,6,7,3,1,2};
        for (int i = 0; i < a.length - 1; i++) {
            // i为每轮最小元素需要交换到的目标索引
            int s = i; // s为最小的索引
            // 从s的下一个对比
            for (int j = s + 1; j < a.length; j++) {
                if (a[s] > a[j]) {
                    // 如果后边的比s小，那么将s赋值为j
                    s = j;
                }
            }
            if (s != i) {
                // 交换位置
                int temp = 0;
                temp = a[i];
                a[i] = a[s];
                a[s] = temp;
            }
            System.out.println(Arrays.toString(a));
        }
    }
}
