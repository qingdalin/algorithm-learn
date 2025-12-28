package algorithm.prefixtree44And45;

import java.io.*;
import java.util.Arrays;

/**
 * 前缀树静态数组的方式实现（也可以类方式实现）
 * @author 汪大鹏
 * @version 1.0
 * @date 2024-01-28 13:38
 */
public class PrefixTreeArray {
    public static int MAX = 150001;
    public static int[][] tree = new int[MAX][26];
    public static int[] pass = new int[MAX];
    public static int[] end = new int[MAX];
    public static int cnt;
    public static int m;
    public static String[]  splits;
    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StreamTokenizer st = new StreamTokenizer(bf);
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        String line = "";
        while ((line = bf.readLine()) != null) {
            build();
            m = Integer.parseInt(line);
            for (int i = 1; i <= m; i++) {
                splits = bf.readLine().split(" ");
                if (splits[0].equals("1")) {
                    insert(splits[1]);
                } else if (splits[0].equals("2")) {
                    delete(splits[1]);
                } else if (splits[0].equals("3")) {
                    System.out.println(search(splits[1]) > 0 ? "YES" : "NO");
                } else if (splits[0].equals("4")) {
                    System.out.println(prefixNumber(splits[1]));
                }
            }
            clear();
        }
        out.flush();
        bf.close();
        out.close();
    }
    public static void build() {
        cnt = 1;
    }

    public static void clear() {
        for (int i = 1; i <= cnt; i++) {
            Arrays.fill(tree[i], 0);
            pass[i] = 0;
            end[i] = 0;
        }
    }

    public static void insert(String word) {
        int cur = 1;
        pass[cur]++;
        for (int i = 0, path; i < word.length(); i++) {
            path = word.charAt(i) - 'a';
            if (tree[cur][path] == 0) {
                tree[cur][path] = ++cnt;
            }
            cur = tree[cur][path];
            pass[cur]++;
        }
        end[cur]++;
    }
    public static void delete(String word) {
        if (search(word) > 0) {
            int cur = 1;
            //pass[cur]--;
            for (int i = 0, path; i < word.length(); i++) {
                path = word.charAt(i) - 'a';
                if (--pass[tree[cur][path]] == 0) {
                    tree[cur][path] = 0;
                    return;
                }
                cur = tree[cur][path];
            }
            end[cur]--;
        }
    }

    public static int search(String word) {
        int cur = 1;
        for (int i = 0, path; i < word.length(); i++) {
            path = word.charAt(i) - 'a';
            if (tree[cur][path] == 0) {
                return 0;
            }
            cur = tree[cur][path];
        }
        return end[cur];
    }
    public static int prefixNumber(String pre) {
        int cur = 1;
        for (int i = 0, path; i < pre.length(); i++) {
            path = pre.charAt(i) - 'a';
            if (tree[cur][path] == 0) {
                return 0;
            }
            cur = tree[cur][path];
        }
        return pass[cur];
    }
}
