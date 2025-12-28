package leetcode.study.leetcdoe201_400;

import java.util.Arrays;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/7/12 14:13
 */
public class Leetcode211WordDictionary {
    public static void main(String[] args) {
        WordDictionary wordDictionary = new WordDictionary();
        wordDictionary.addWord("a");
        wordDictionary.addWord("a");
        System.out.println(wordDictionary.search("."));
        System.out.println(wordDictionary.search("a"));
        System.out.println(wordDictionary.search("aa"));
        System.out.println(wordDictionary.search("a"));
        System.out.println(wordDictionary.search(".a"));
        System.out.println(wordDictionary.search("a."));
    }
}

 class WordDictionary {
    public static int MAXN = 10001;
    public static int[][] tree = new int[MAXN][26];
    public static int[] pass = new int[MAXN];
    public static int[] end = new int[MAXN];
    public static int cnt;
    public WordDictionary() {
        cnt = 1;
        for (int i = 0; i < MAXN; i++) {
            Arrays.fill(tree[i], 0);
            pass[i] = end[i] = 0;
        }
    }

    public void addWord(String word) {
        int cur = 1;
        pass[cur]++;
        int length = word.length();
        for (int i = 0, path; i < length; i++) {
            path = word.charAt(i) - 'a';
            if (tree[cur][path] == 0) {
                tree[cur][path] = ++cnt;
            }
            cur = tree[cur][path];
            pass[cur]++;
        }
        end[cur]++;
    }

    public boolean search(String word) {
        int cur = 1;
        int length = word.length();
        for (int i = 0, path; i < length; i++) {
            if (word.charAt(i) == '.') {
                for (char j = 'a'; j <= 'z'; j++) {
                    path = j - 'a';
                    if (tree[cur][path] == 0) {
                        continue;
                    }
                    cur = tree[cur][path];
                    break;
                }
            } else {
                path = word.charAt(i) - 'a';
                if (tree[cur][path] == 0) {
                    return false;
                }
                cur = tree[cur][path];
            }

        }
        return end[cur] > 0;
    }
}

