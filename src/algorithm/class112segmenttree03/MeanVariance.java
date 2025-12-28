package algorithm.class112segmenttree03;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.StringTokenizer;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2024/9/7 10:52
 * https://www.luogu.com.cn/problem/P1471
 */
public class MeanVariance {
    public static int MAXN = 100001;
    public static double[] arr = new double[MAXN];
    public static double[] sum1 = new double[MAXN << 2]; // 累加和
    public static double[] sum2 = new double[MAXN << 2]; // 平方和
    public static double[] addv = new double[MAXN << 2];

    public static void up(int i) {
        sum1[i] = sum1[i << 1] + sum1[i << 1 | 1];
        sum2[i] = sum2[i << 1] + sum2[i << 1 | 1];
    }

    public static void down(int i, int ln, int rn) {
        if (addv[i] != 0) {
            lazy(i << 1, addv[i], ln);
            lazy(i << 1 | 1, addv[i], rn);
            addv[i] = 0;
        }
    }

    private static void lazy(int i, double v, int n) {
        // 平方和更新
        // (a1 + v)^2 + (a2 + v)^2 + ....+ (an + v)^2
        // a1^2+a2^2+..+an^2 + 2 * v(a1 + a2 +..+ an) + v * v * n
        sum2[i] += sum1[i] * v * 2 + v * v * n;
        sum1[i] += v * n;
        addv[i] += v;
    }

    public static void build(int l, int r, int i) {
        if (l == r) {
            sum1[i] = arr[l];
            sum2[i] = arr[l] * arr[l];
        } else {
            int mid = (l + r) >> 1;
            build(l, mid, i << 1);
            build(mid + 1, r, i << 1 | 1);
            up(i);
        }
        addv[i] = 0;
    }

    public static void add(int jobl, int jobr, double jobv, int l, int r, int i) {
        if (jobl <= l && r <= jobr) {
            lazy(i, jobv, r - l + 1);
        } else {
            int mid = (l + r) >> 1;
            down(i, mid - l + 1, r - mid);
            if (jobl <= mid) {
                add(jobl, jobr, jobv, l, mid, i << 1);
            }
            if (jobr > mid) {
                add(jobl, jobr, jobv, mid + 1, r, i << 1 | 1);
            }
            up(i);
        }
    }

    public static double query(double[] sum, int jobl, int jobr, int l, int r, int i) {
        if (jobl <= l && r <= jobr) {
            return sum[i];
        }
        int mid = (l + r) >> 1;
        down(i, mid - l + 1, r - mid);
        double ans = 0;
        if (jobl <= mid) {
            ans += query(sum, jobl, jobr, l, mid, i << 1);
        }
        if (jobr > mid) {
            ans += query(sum, jobl, jobr,  mid + 1, r, i << 1 | 1);
        }
        return ans;
    }

    public static void main(String[] args) throws IOException {
        Kattio io = new Kattio();
        int n = io.nextInt();
        int m = io.nextInt();
        for (int i = 1; i <= n; i++) {
            arr[i] = io.nextDouble();
        }
        build(1, n, 1);
        double jobv;
        for (int i = 1, op, jobl, jobr; i <= m; i++) {
            op = io.nextInt();
            if (op == 1) {
                jobl = io.nextInt();
                jobr = io.nextInt();
                jobv = io.nextDouble();
                // 先将l位置加一个首项
                add(jobl, jobr, jobv, 1, n, 1);
            } else if (op == 2){
                jobl = io.nextInt();
                jobr = io.nextInt();
                double ans = query(sum1, jobl, jobr, 1, n, 1) / (jobr - jobl + 1);
                io.println(String.format("%.4f", ans));
            } else {
                // 平方和更新
                // ((a1 - avg)^2 + (a2 - avg)^2 + ....+ (an - avg)^2) / n
                // a1^2+a2^2+..+an^2 - 2 * avg * (a1 + a2 +..+ an) / n + avg * avg * n / n
                // a1^2+a2^2+..+an^2 - 2 * avg * avg + avg * avg
                // a1^2+a2^2+..+an^2 - avg * avg
                jobl = io.nextInt();
                jobr = io.nextInt();
                double a = query(sum1, jobl, jobr, 1, n, 1);
                double b = query(sum2, jobl, jobr, 1, n, 1);
                double size = jobr - jobl + 1;
                double ans = b / size - (a / size) * (a / size);
                System.out.println(String.format("%.4f", ans));
                //io.println(String.format("%.4f", b / m - (a / m) * (a / m)));
            }
        }
        io.flush();
        io.close();
    }

    // Kattio类IO效率很好，但还是不如StreamTokenizer
    // 只有StreamTokenizer无法正确处理时，才考虑使用这个类
    // 参考链接 : https://oi-wiki.org/lang/java-pro/
    public static class Kattio extends PrintWriter {
        private BufferedReader r;
        private StringTokenizer st;

        public Kattio() {
            this(System.in, System.out);
        }

        public Kattio(InputStream i, OutputStream o) {
            super(o);
            r = new BufferedReader(new InputStreamReader(i));
        }

        public Kattio(String intput, String output) throws IOException {
            super(output);
            r = new BufferedReader(new FileReader(intput));
        }

        public String next() {
            try {
                while (st == null || !st.hasMoreTokens())
                    st = new StringTokenizer(r.readLine());
                return st.nextToken();
            } catch (Exception e) {
            }
            return null;
        }

        public int nextInt() {
            return Integer.parseInt(next());
        }

        public double nextDouble() {
            return Double.parseDouble(next());
        }

        public long nextLong() {
            return Long.parseLong(next());
        }
    }
}
