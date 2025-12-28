package algorithm.prefixtree44And45;

import java.util.Arrays;

/**
 * https://www.nowcoder.com/practice/c552d3b4dfda49ccb883a6371d9a6932
 * 接头密钥，包含多少前缀
 * @author 汪大鹏
 * @version 1.0
 * @date 2024-01-28 16:32
 */
public class CountContainsKey {
    public static int MAX = 150001;
    public static int[][] tree = new int[MAX][12];
    public static int[] pass = new int[MAX];
    public static int cnt;

    public static void main(String[] args) {
        System.out.println(Integer.numberOfLeadingZeros(100));
    }

    public static int[] countConsistentKeys (int[][] b, int[][] a) {

        build();
        StringBuilder sb = new StringBuilder();
        for (int[] nums : a) {
            sb.setLength(0);
            for (int i = 1; i < nums.length; i++) {
                sb.append((nums[i] - nums[i - 1])).append("#");
            }
            insert(sb.toString());
        }
        int[] ans = new int[b.length];
        for (int i = 0; i < b.length; i++) {
            sb.setLength(0);
            for (int j = 1; j < b[i].length; j++) {
                sb.append((b[i][j] - b[i][j - 1])).append("#");
            }
            ans[i] = prefixNumber(sb.toString());
        }
        clear();
        return ans;
    }

    public static void build() {
        cnt = 1;
    }

    public static void clear() {
        for (int i = 1; i <= cnt; i++) {
            Arrays.fill(tree[i], 0);
            pass[i] = 0;
        }
    }

    public static int path(char c) {
        if (c == '#') {
            return 10;
        } else if (c == '-') {
            return 11;
        } else {
            return c - '0';
        }
    }

    public static void insert(String word) {
        int cur = 1;
        pass[cur]++;
        for (int i = 0, path; i < word.length(); i++) {
            path = path(word.charAt(i));
            if (tree[cur][path] == 0) {
                tree[cur][path] = ++cnt;
            }
            cur = tree[cur][path];
            pass[cur]++;
        }
    }
    public static int prefixNumber(String pre) {
        int cur = 1;
        for (int i = 0, path; i < pre.length(); i++) {
            path = path(pre.charAt(i));
            if (tree[cur][path] == 0) {
                return 0;
            }
            cur = tree[cur][path];
        }
        return pass[cur];
    }
}
