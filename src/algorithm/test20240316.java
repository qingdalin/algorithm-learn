package algorithm;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 汪大鹏
 * @version 1.0
 * @date 2024-03-16 16:14
 */
public class test20240316 {
    public static void main(String[] args) {
        String s = "abcd";
        stringSplit(s, "");
        List<String> collect = new ArrayList<>();
        collect.add("下降尘凡第一难");

    }

    private static void stringSplit(String s, String result) {
        if (s.length() == 0) {
            System.out.println(result);
        }
        for (int i = 1; i <= s.length(); i++) {
            String split = s.substring(0, i);
            String nextStr = s.substring(i);
            stringSplit(nextStr, result + "," + split);
        }
    }
}
