package algorithm.class44trie;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2024/8/10 15:54
 * 前缀树
 * https://leetcode.cn/problems/implement-trie-ii-prefix-tree/description/
 */
public class TrieTreeMap {
    class Trie1 {
        class TrieNode {
            int pass;
            int end;
            Map<Integer, TrieNode> nexts;

            public TrieNode() {
                this.pass = 0;
                this.end = 0;
                this.nexts = new HashMap<>();
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
                    path = word.charAt(i);
                    TrieNode next = node.nexts.get(path);
                    if (--next.pass == 0) {
                        node.nexts.remove(next);
                        return;
                    }
                    node = next;
                }
                node.end--;
            }
        }

        // 插入字符串
        public void insert(String word) {
            TrieNode node = root;
            node.pass++;
            for (int i = 0, path; i < word.length(); i++) {
                path = word.charAt(i);
                if (!node.nexts.containsKey(path)) {
                    node.nexts.put(path, new TrieNode());
                }
                node = node.nexts.get(path);
                node.pass++;
            }
            node.end++;
        }

        // 查询字符串出现的次数
        public int search(String word) {
            TrieNode node = root;
            for (int i = 0, path; i < word.length(); i++) {
                path = word.charAt(i);
                if (!node.nexts.containsKey(path)) {
                    return 0;
                }
                node = node.nexts.get(path);
            }
            return node.end;
        }

        // 查询字符串以pre做前缀的次数
        public int preWordNumber(String pre) {
            TrieNode node = root;
            for (int i = 0, path; i < pre.length(); i++) {
                path = pre.charAt(i);
                if (!node.nexts.containsKey(path)) {
                    return 0;
                }
                node = node.nexts.get(path);
            }
            return node.pass;
        }
    }
}
