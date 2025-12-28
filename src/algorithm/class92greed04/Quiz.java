package algorithm.class92greed04;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;
import java.util.Arrays;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2024/7/27 13:10
 * // 知识竞赛
 * // 最近部门要选两个员工去参加一个需要合作的知识竞赛，
 * // 每个员工均有一个推理能力值ai，以及一个阅读能力值bi
 * // 如果选择第i个人和第j个人去参加竞赛，
 * // 两人在推理方面的能力为X = (ai + aj)/2
 * // 两人在阅读方面的能力为Y = (bi + bj)/2
 * // 现在需要最大化他们表现较差一方面的能力
 * // 即让min(X,Y) 尽可能大，问这个值最大是多少
 * // 测试链接 : https://www.nowcoder.com/practice/2a9089ea7e5b474fa8f688eae76bc050
 * // 请同学们务必参考如下代码中关于输入、输出的处理
 * // 这是输入输出处理效率很高的写法
 * // 提交以下的code，提交时请把类名改成"Main"，可以直接通过
 */
public class Quiz {
    public static int MAXN = 200001;
    public static int n;
    public static int[][] nums = new int[MAXN][2];
    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StreamTokenizer st = new StreamTokenizer(bf);
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        while (st.nextToken() != StreamTokenizer.TT_EOF) {
            n = (int) st.nval;
            for (int i = 0; i < n; i++) {
                st.nextToken();
                nums[i][0] = (int) st.nval;
                st.nextToken();
                nums[i][1] = (int) st.nval;
            }
            double ans = compute();
            System.out.println(ans);
        }
        out.flush();
        out.close();
        bf.close();
    }

    private static double compute() {
        // 按照推理和阅读能力的绝对值排序
        Arrays.sort(nums, 0, n, (a, b) -> Math.abs(a[0] - a[1]) - Math.abs(b[0] - b[1]));
        int maxThink = nums[0][0], maxRead = nums[0][1];
        double ans = 0.0;
        for (int i = 1; i < n; i++) {
            int curThink = nums[i][0];
            int curRead = nums[i][1];
            if (curThink > curRead) {
                ans = Math.max(ans, curRead + maxRead);
            } else {
                ans = Math.max(ans, curThink + maxThink);
            }
            maxRead = Math.max(maxRead, curRead);
            maxThink = Math.max(maxThink, curThink);

        }
        return ans / 2;
    }
}
