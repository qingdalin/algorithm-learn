package algorithm.class150;

import java.util.Arrays;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/1/18 13:14
 */
public class Code01_ShowDetail {
    public static int MAXN = 100001;
    public static double ALPHA = 0.7;
    public static int head = 0, cnt = 0;
    public static int[] key = new int[MAXN];
    public static int[] left = new int[MAXN];
    public static int[] right = new int[MAXN];
    public static int[] count = new int[MAXN];
    public static int[] size = new int[MAXN];
    public static int[] diff = new int[MAXN];
    public static int ci;
    public static int[] collect = new int[MAXN];
    // 最上方的不平衡节点
    public static int top;
    // top的父节点
    public static int father;
    // top是父节点的什么孩子，1代表左孩子，2代表右孩子
    public static int side;

    public static int max, cost;

    public static int init(int num) {
        key[++cnt] = num;
        left[cnt] = right[cnt] = 0;
        count[cnt] = size[cnt] = diff[cnt] = 1;
        return cnt;
    }

    public static void up(int i) {
        size[i] = size[left[i]] + size[right[i]] + count[i];
        diff[i] = diff[left[i]] + diff[right[i]] + (count[i] > 0 ? 1 : 0);
    }

    public static void inorder(int i) {
        if (i != 0) {
            inorder(left[i]);
            if (count[i] > 0) {
                collect[++ci] = i;
            }
            inorder(right[i]);
        }
    }

    public static int build(int l, int r) {
        if (l > r) {
            return 0;
        }
        int m = (l + r) / 2;
        int h = collect[m];
        left[h] = build(l, m - 1);
        right[h] = build(m + 1, r);
        up(h);
        return h;
    }

    public static void rebuild() {
        if (top != 0) {
            ci = 0;
            inorder(top);
            if (ci > 0) {
                cost += ci;
                if (father == 0) {
                    head = build(1, ci);
                } else if (side == 1) {
                    left[father] = build(1, ci);
                } else {
                    right[father] = build(1, ci);
                }
            }
        }
    }

    public static boolean balance(int i) {
        return ALPHA * diff[i] >= Math.max(diff[left[i]], diff[right[i]]);
    }

    public static void add(int num) {
        top = father = side = 0;
        add(head, 0, 0, num);
        rebuild();
    }

    private static void add(int i, int f, int s, int num) {
        if (i == 0) {
            if (f == 0) {
                head = init(num);
            } else if (s == 1){
                left[f] = init(num);
            } else {
                right[f] = init(num);
            }
        } else {
            if (key[i] == num) {
                count[i]++;
            } else if (key[i] > num) {
                add(left[i], i, 1, num);
            } else {
                add(right[i], i, 2, num);
            }
            up(i);
            if (!balance(i)) {
                top = i;
                father = f;
                side = s;
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
        int kth = rank(num);
        if (kth == 1) {
            return Integer.MIN_VALUE;
        } else {
            return index(kth - 1);
        }
    }

    public static int post(int num) {
        int kth = rank(num + 1);
        if (size[head] + 1 == kth) {
            return Integer.MAX_VALUE;
        } else {
            return index(kth);
        }
    }

    public static void remove(int num) {
        if (rank(num) != rank(num + 1)) {
            top = father = side = 0;
            remove(head, 0, 0, num);
            rebuild();
        }
    }

    private static void remove(int i, int f, int s, int num) {
        if (key[i] == num) {
            count[i]--;
        } else if (key[i] > num) {
            remove(left[i], i, 1, num);
        } else {
            remove(right[i], i, 2, num);
        }
        up(i);
        if (!balance(i)) {
            top = i;
            father = f;
            side = s;
        }
    }

    public static void clear() {
        Arrays.fill(key, 1, cnt + 1, 0);
        Arrays.fill(count, 1, cnt + 1, 0);
        Arrays.fill(size, 1, cnt + 1, 0);
        Arrays.fill(left, 1, cnt + 1, 0);
        Arrays.fill(right, 1, cnt + 1, 0);
        Arrays.fill(diff, 1, cnt + 1, 0);
        cnt = 0;
        head = 0;
    }

    public static void main(String[] args) {
        ALPHA = 0.7; // 设置平衡因子
        max = 10000; // 设置插入范围
        System.out.println("测试开始");
        cost = 0; // 清空重构节点计数
        for (int num = 1; num <= max; num++) {
            add(num);
        }
        System.out.println("插入数字 : " + "1~" + max);
        System.out.println("平衡因子 : " + ALPHA);
        System.out.println("树的高度 : " + deep(head));
        System.out.println("重构节点 : " + cost);
        System.out.println("测试结束");
        clear();
    }

    private static int deep(int i) {
        if (i == 0) {
            return 0;
        }
        return Math.max(deep(left[i]), deep(right[i])) + 1;
    }
}
