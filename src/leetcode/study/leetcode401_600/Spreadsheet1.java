package leetcode.study.leetcode401_600;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: 汪大鹏
 * @version: 1.0.0
 * @date: 2025/9/19 20:27
 * https://leetcode.cn/problems/design-spreadsheet/?envType=daily-question&envId=2025-09-19
 */
public class Spreadsheet1 {
    public static Map<String, Integer> map = new HashMap<>();
    public Spreadsheet1(int rows) {
        map.clear();
    }

    public void setCell(String cell, int value) {
        map.put(cell, value);
    }



    public void resetCell(String cell) {
        map.remove(cell);
    }

    public int getValue(String formula) {
        String str = formula.substring(1);
        String[] split = str.split("\\+");
        int ans = 0;
        if (Character.isLetter(split[0].charAt(0))) {
            ans += map.getOrDefault(split[0], 0);
        } else {
            ans += Integer.parseInt(split[0]);
        }
        if (Character.isLetter(split[1].charAt(0))) {
            ans += map.getOrDefault(split[1], 0);
        } else {
            ans += Integer.parseInt(split[1]);
        }
        return ans;
    }


}
