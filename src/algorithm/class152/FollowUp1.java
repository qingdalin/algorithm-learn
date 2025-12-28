package algorithm.class152;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/1/22 20:48
 * // FHQ-Treap实现普通有序表，不用词频压缩，数据加强的测试，java版
 * // 这个文件课上没有讲，测试数据加强了，而且有强制在线的要求
 * // 基本功能要求都是不变的，可以打开测试链接查看
 * // 测试链接 : https://www.luogu.com.cn/problem/P6136
 * // 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例
 */
public class FollowUp1 {
    public static int MAXN = 1100001;
    public static int cnt = 0;
    public static int head = 0;
    public static int[] key = new int[MAXN];
    public static int[] left = new int[MAXN];
    public static int[] right = new int[MAXN];
    public static int[] count = new int[MAXN];
    public static int[] size = new int[MAXN];
    public static double[] priority = new double[MAXN];

    public static void up(int i) {
        size[i] = size[left[i]] + size[right[i]] + count[i];
    }

    public static void split(int l, int r, int i, int num) {
        if (i == 0) {
            right[l] = left[r] = 0;
        } else {
            if (key[i] <= num) {
                right[l] = i;
                // 错误写法 split(l, r, right[i], num);
                split(i, r, right[i], num);
            } else {
                left[r] = i;
                // 错误写法 split(l, r, left[i], num);
                split(l, i, left[i], num);
            }
            up(i);
        }
    }

    public static int merge(int l, int r) {
        if (l == 0 || r == 0) {
            return l + r;
        }
        if (priority[l] >= priority[r]) {
            right[l] = merge(right[l], r);
            up(l);
            return l;
        } else {
            left[r] = merge(l, left[r]);
            up(r);
            return r;
        }
    }

    public static int find(int i, int num) {
        if (i == 0) {
            return 0;
        }
        if (key[i] == num) {
            return i;
        } else if (key[i] < num) {
            return find(right[i], num);
        } else {
            return find(left[i], num);
        }
    }

    public static void add(int num) {
        if (find(head, num) != 0) {
            changCount(head, num, 1);
        } else {
            split(0, 0, head, num);
            key[++cnt] = num;
            count[cnt] = size[cnt] = 1;
            priority[cnt] = Math.random();
            head = merge(merge(right[0], cnt), left[0]);
        }
    }

    private static void changCount(int i, int num, int change) {
        if (key[i] == num) {
            count[i] += change;
        } else if (key[i] > num) {
            changCount(left[i], num, change);
        } else {
            changCount(right[i], num, change);
        }
        up(i);
    }

    public static void remove(int num) {
        int i = find(head, num);
        if (i != 0) {
            if (count[i] > 1) {
                changCount(head, num, -1);
            } else {
                split(0, 0, head, num);
                int lm = right[0];
                int r = left[0];
                split(0, 0, lm, num - 1);
                int l = right[0];
                // 错误写法merge(l, r);
                head = merge(l, r);
            }
        }
    }

    public static int rank(int num) {
        return small(head, num) + 1;
    }

    private static int small(int i, int num) {
        if (i == 0) {
            return 0;
        }
        if (key[i] >= num) {
            return small(left[i], num);
        } else {
            return size[left[i]] + count[i] + small(right[i], num);
        }
    }

    public static int index(int x) {
        return index(head, x);
    }

    private static int index(int i, int x) {
        if (size[left[i]] >= x) {
            return index(left[i], x);
        } else if (size[left[i]] + count[i] < x) {
            return index(right[i], x - size[left[i]] - count[i]);
        }
        return key[i];
    }

    public static int pre(int num) {
        return pre(head, num);
    }

    private static int pre(int i, int num) {
        if (i == 0) {
            return Integer.MIN_VALUE;
        }
        if (key[i] >= num) {
            return pre(left[i], num);
        } else {
            return Math.max(key[i], pre(right[i], num));
        }
    }

    public static int post(int num) {
        return post(head, num);
    }

    private static int post(int i, int num) {
        if (i == 0) {
            return Integer.MAX_VALUE;
        }
        if (key[i] <= num) {
            return post(right[i], num);
        } else {
            return Math.min(key[i], post(left[i], num));
        }
    }
    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StreamTokenizer in = new StreamTokenizer(bf);
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        in.nextToken(); int n = (int) in.nval;
        in.nextToken(); int m = (int) in.nval;
        for (int i = 0; i < n; i++) {
            in.nextToken();
            add((int) in.nval);
        }
        int ans = 0;
        int laseAns = 0;
        for (int i = 1, x, opt; i <= m; i++) {
            in.nextToken(); opt = (int) in.nval;
            in.nextToken(); x = (int) in.nval;
            x ^= laseAns;
            if (opt == 1) {
                add(x);
            } else if (opt == 2) {
                remove(x);
            } else if (opt == 3) {
                laseAns = rank(x);
                ans ^= laseAns;
            } else if (opt == 4) {
                laseAns = index(x);
                ans ^= laseAns;
            } else if (opt == 5) {
                laseAns = pre(x);
                ans ^= laseAns;
            } else {
                laseAns = post(x);
                ans ^= laseAns;
            }
        }
        out.println(ans);
        //clear();
        out.flush();
        out.close();
        bf.close();
    }
}
