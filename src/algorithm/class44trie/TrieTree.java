package algorithm.class44trie;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2024/8/10 15:54
 * 前缀树
 * https://leetcode.cn/problems/implement-trie-ii-prefix-tree/description/
 */
public class TrieTree {
    class Trie1 {
        class TrieNode {
            int pass;
            int end;
            TrieNode[] nexts;

            public TrieNode() {
                this.pass = 0;
                this.end = 0;
                this.nexts = new TrieNode[26];
            }
        }

        public TrieNode root;

        public Trie1() {
            this.root = new TrieNode();
        }

        // 删除某个字符串
        public void delete(String word) {
            if (search(word) > 0) {
                TrieNode node = root;
                node.pass--;
                for (int i = 0, path; i < word.length(); i++) {
                    path = word.charAt(i) - 'a';
                    if (--node.nexts[path].pass == 0) {
                        node.nexts[path] = null;
                        return;
                    }
                    node = node.nexts[path];
                }
                node.end--;
            }
        }

        // 插入字符串
        public void insert(String word) {
            TrieNode node = root;
            node.pass++;
            for (int i = 0, path; i < word.length(); i++) {
                path = word.charAt(i) - 'a';
                if (node.nexts[path] == null) {
                    node.nexts[path] = new TrieNode();
                }
                node = node.nexts[path];
                node.pass++;
            }
            node.end++;
        }

        // 查询字符串出现的次数
        public int search(String word) {
            TrieNode node = root;
            for (int i = 0, path; i < word.length(); i++) {
                path = word.charAt(i) - 'a';
                if (node.nexts[path] == null) {
                    return 0;
                }
                node = node.nexts[path];
            }
            return node.end;
        }

        // 查询字符串以pre做前缀的次数
        public int preWordNumber(String pre) {
            TrieNode node = root;
            for (int i = 0, path; i < pre.length(); i++) {
                path = pre.charAt(i) - 'a';
                if (node.nexts[path] == null) {
                    return 0;
                }
                node = node.nexts[path];
            }
            return node.pass;
        }
    }
}
