package algorithm.class97prime;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;
import java.math.BigInteger;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2024/8/3 9:44
 * https://www.luogu.com.cn/problem/U148828
 * 判断一个较大的数是否是质数，10的9次方以上
 * // 判断较大的数字是否是质数(Miller-Rabin测试)
 * // 测试链接 : https://www.luogu.com.cn/problem/U148828
 * // 请同学们务必参考如下代码中关于输入、输出的处理
 * // 这是输入输出处理效率很高的写法
 * // 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例
 * // 本文件可以搞定任意范围数字的质数检查，时间复杂度O(s * (logn)的三次方)
 * // 为什么不自己写，为什么要用BigInteger中的isProbablePrime方法
 * // 原因在于long类型位数不够，乘法同余的时候会溢出，课上已经做了说明
 */
public class LargeNumberIsPrime2 {
    public static int n;
    // 测试次数，次数越多失误率越低，但速度也越慢
    public static int s = 10;
    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StreamTokenizer st = new StreamTokenizer(bf);
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        n = Integer.parseInt(bf.readLine());
        for (int i = 0; i < n; i++) {
            BigInteger num = new BigInteger(bf.readLine());
            System.out.println(num.isProbablePrime(s) ? "Yes" : "No");
        }
        out.flush();
        out.close();
        bf.close();
    }
}
