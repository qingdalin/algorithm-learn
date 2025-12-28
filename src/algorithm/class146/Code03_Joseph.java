package algorithm.class146;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/1/6 19:16
 * // 约瑟夫环问题
 * // 一共有1~n这些点，组成首尾相接的环
 * // 从1号点从数字1开始报数，哪个节点报到数字k，就删除该节点
 * // 然后下一个节点从数字1开始重新报数，最终环上会剩下一个节点
 * // 返回该节点的编号
 * // 1 <= n, k <= 10^6
 * // 测试链接 : https://www.luogu.com.cn/problem/P8671
 * // 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例
 */
public class Code03_Joseph {
    public static int n, k;
    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StreamTokenizer in = new StreamTokenizer(bf);
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        in.nextToken(); n = (int) in.nval;
        in.nextToken(); k = (int) in.nval;
        out.println(compute());
        out.flush();
        out.close();
        bf.close();
    }

    private static int compute() {
        int ans = 1;
        for (int c = 2; c <= n; c++) {
            // 老编号 = (新编号 + k - 1) % 老环长度 + 1
            ans = (ans + k - 1) % c + 1;
        }
        return ans;
    }
}
