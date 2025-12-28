package leetcode.study.leetcode401_600;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/9/23 20:21
 * https://leetcode.cn/problems/complex-number-multiplication/
 */
public class Leetcode537ComplexNumberMultiply {
    public String complexNumberMultiply(String num1, String num2) {
        // (1 + 1i) * (1 + 1i) = 1 + i2 + 2 * i = 2i
        // 1 + 1i + 1i + 1i*1i
        // 1 + 2i - 1
        // 0+2i
        // (a + bi) * (c + di)
        // ac + adi + bci + bi*di
        // ac + (ad+bc)i - bd
        // (ac - bd) + (ad + bc)i
        String[] arr1 = num1.split("\\+|i");
        String[] arr2 = num2.split("\\+|i");
        int a = Integer.parseInt(arr1[0]);
        int b = Integer.parseInt(arr1[1]);
        int c = Integer.parseInt(arr2[0]);
        int d = Integer.parseInt(arr2[1]);
        return (a * c - b * d) + "+" + (a * d + b * c) + "i";
    }
}
