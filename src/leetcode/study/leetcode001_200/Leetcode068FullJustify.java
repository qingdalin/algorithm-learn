package leetcode.study.leetcode001_200;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/5/21 19:53
 * https://leetcode.cn/problems/text-justification/
 */
public class Leetcode068FullJustify {
    public static List<String> fullJustify(String[] arr, int maxWidth) {
        int n = arr.length;
        List<String> collect = new ArrayList<>();
        List<String> ans = new ArrayList<>();
        StringBuilder cur;
        for (int k = 0; k < n; k++) {
            cur = new StringBuilder();
            collect.clear();
            int len = arr[k].length();
            collect.add(arr[k]);
            while (k + 1 < n && len + arr[k + 1].length() <= maxWidth
                && collect.size() <= (maxWidth - len - arr[k + 1].length())) {
                // 不越界，添加下一个不超过最大宽度，添加下一个每个单词至少分一个空格
                len += arr[k + 1].length();
                collect.add(arr[k + 1]);
                k++;
            }
            int size = collect.size();
            int diff = maxWidth - len;
            if (k == n - 1) {
                for (int i = 0; i < size; i++) {
                    cur.append(collect.get(i));
                    if (i != size - 1) {
                        cur.append(" ");
                    }
                }
                int m = maxWidth - cur.length();
                for (int i = 1; i <= m; i++) {
                    cur.append(" ");
                }
                ans.add(cur.toString());
                break;
            }
            if (size == 1) {
                cur.append(collect.get(0));
                for (int i = 1; i <= maxWidth - len; i++) {
                    cur.append(" ");
                }
            } else {
                int spaceNum = diff / (size - 1); // 每个平均至少分的空格
                int mod = diff % (size - 1); // 多余的可补的空格
                for (int i = 0; i < size; i++) {
                    cur.append(collect.get(i));
                    if (i == size - 1) {
                        continue;
                    }
                    for (int j = 0; j < spaceNum; j++) {
                        cur.append(" ");
                        if (j == spaceNum - 1 && mod > 0) {
                            cur.append(" ");
                            mod--;
                        }
                    }
                }
            }
            ans.add(cur.toString());
        }
        return ans;
    }

    public static void main(String[] args) {
//        String[] arr = new String[] {"This", "is", "an", "example", "of", "text", "justification."};
//        String[] arr = new String[] {"What","must","be","acknowledgment","shall","be"};
        String[] arr = new String[] {"Science","is","what","we","understand","well","enough","to","explain","to","a","computer.","Art","is","everything","else","we","do"};
        List<String> ans = fullJustify(arr, 20);
        for (String item : ans) {
            System.out.println(item);
        }
    }
}
