package leetcode.study.leetcode401_600;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/9/23 20:02
 * https://leetcode.cn/problems/encode-and-decode-tinyurl/
 */
public class CodecUrl {
    public Map<Integer, String> map = new HashMap<>();
    public int id = 0;
    // Encodes a URL to a shortened URL.
    public String encode(String longUrl) {
        id++;
        map.put(id, longUrl);
        return "http://tinyurl.com/" + id;
    }

    // Decodes a shortened URL to its original URL.
    public String decode(String shortUrl) {
        int idx = shortUrl.lastIndexOf("/") + 1;
        int key = Integer.parseInt(shortUrl.substring(idx));
        return map.get(key);
    }
}
