package algorithm.class51;

import java.io.*;

/**
 * @author 汪大鹏
 * @version 1.0
 * @date 2024-03-03 17:47
 * https://www.nowcoder.com/practice/7037a3d57bbd4336856b8e16a9cafd71
 * 机器人跳跃问题
 */
public class RobotSkip {
    public static int N = 0;
    public static int[] arr = new int[100000];
    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StreamTokenizer st = new StreamTokenizer(bf);
        PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out));
        while (st.nextToken() != StreamTokenizer.TT_EOF) {
            N = (int) st.nval;
            int l = 0;
            int r = 0;
            for (int i = 0; i < N; i++) {
                st.nextToken();
                arr[i] = (int) st.nval;
                r = Math.max(r, arr[i]);
            }
            out.println(compute(l, r, r));
        }
        out.flush();
        out.close();
        bf.close();
    }
    
    public static int compute(int l, int r, int max) {
        int m = 0;
        int ans = -1;
        while (l <= r) {
            m = (l + r) / 2;
            if (f(m, max)) {
                ans = m;
                r = m - 1;
            } else {
                l = m + 1;
            }
        }
        return ans;
    }

    public static boolean f(int energy, int max) {
        for (int i = 0; i < N; i++) {
            if (energy < arr[i]) {
                energy -= arr[i] - energy;
            } else {
                energy += energy - arr[i];
            }
            if (energy >= max) {
                return true;
            }
            if (energy < 0) {
                return false;
            }
        }
        return true;
    }
}
