package algorithm.class63doublebfs;


import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author 汪大鹏
 * @version 1.0
 * @date 2024-04-14 14:08
 * 字典 wordList 中从单词 beginWord 和 endWord 的 转换序列
 * 是一个按下述规格形成的序列 beginWord -> s1 -> s2 -> ... -> sk：
 *
 * 每一对相邻的单词只差一个字母。
 *  对于 1 <= i <= k 时，每个 si 都在 wordList 中。注意，
 *  beginWord 不需要在 wordList 中。
 * sk == endWord
 * 给你两个单词 beginWord 和 endWord 和一个字典 wordList ，
 * 返回 从 beginWord 到 endWord 的 最短转换序列 中的 单词数目 。如果不存在这样的转换序列，返回 0 。
 */
public class LadderLength {
    public int ladderLength(String beginWord, String endWord, List<String> wordList) {
        Set<String> dict = new HashSet<>(wordList);
        Set<String> smallLevel = new HashSet<>();
        Set<String> bigLevel = new HashSet<>();
        Set<String> nextLevel = new HashSet<>();
        if (!dict.contains(endWord)) {
            return 0;
        }
        smallLevel.add(beginWord);
        bigLevel.add(endWord);
        for (int len = 2; !smallLevel.isEmpty(); len++) {
            for (String word : smallLevel) {
                char[] w = word.toCharArray();
                for (int i = 0; i < w.length; i++) {
                    char old = w[i];
                    for (char change = 'a'; change <= 'z'; change++) {
                        if (change != old) {
                            w[i] = change;
                            String next = String.valueOf(w);
                            if (bigLevel.contains(next)) {
                                return len;
                            }
                            if (dict.contains(next)) {
                                dict.remove(next);
                                nextLevel.add(next);
                            }
                        }

                    }
                    w[i] = old;
                }
            }
            if (nextLevel.size() <= bigLevel.size()) {
                Set<String> tmp = smallLevel;
                smallLevel = nextLevel;
                nextLevel = tmp;
            } else {
                Set<String> tmp = smallLevel;
                smallLevel = bigLevel;
                bigLevel = nextLevel;
                nextLevel = tmp;
            }
            nextLevel.clear();
        }
        return 0;
    }
}
